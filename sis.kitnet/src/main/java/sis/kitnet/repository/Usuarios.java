package sis.kitnet.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.constante.Constantes;
import sis.kitnet.filtrosPesquisa.FiltroUsuario;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Usuario;
import sis.kitnet.util.AbstractManager;


public class Usuarios extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Usuario guardar(Usuario usuario) {
		return this.manager.merge(usuario);
	}

	public Usuario porId(Long idUsu) {
		return this.manager.find(Usuario.class, idUsu);
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException(Constantes.RESTRICAO_USUARIO);
		}
	}
	
	public Usuario porApelilo(String apelido) {
		try {
			return manager.createQuery("select distinct u from Usuario u where upper(u.apelido) =:pApelido",
					Usuario.class).setParameter("pApelido", apelido).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public int quantidadeFiltrados(FiltroUsuario filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(FiltroUsuario filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	private Criteria criarCriteriaParaFiltro(FiltroUsuario filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if (filtro.getApelido() != null) {
			criteria.add(Restrictions.like("apelido", filtro.getApelido(), MatchMode.ANYWHERE));
		}

		return criteria;
	}
}
