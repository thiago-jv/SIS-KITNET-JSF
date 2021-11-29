package sis.kitnet.repository;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.filtrosPesquisa.FiltroInquilino;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Inquilino;
import sis.kitnet.util.AbstractManager;

public class Inquilinos extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Inquilino guardar(Inquilino inquilino) {	
		return manager.merge(inquilino);
	}
	
	@Transactional
	public void remover(Inquilino inquilino) {
		try {
			inquilino = porId(inquilino.getId());
			manager.remove(inquilino);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Inquilino> listar() {
		String sql = "select distinct i from Inquilino i order by i.id";
		return manager.createQuery(sql, Inquilino.class).getResultList();
	}
	
	public Inquilino porId(Long id) {
		return manager.find(Inquilino.class, id);
	}
	
	public int quantidadeFiltrados(FiltroInquilino filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto.
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		// como ira retorna um numero, devemos fazer um cats conforme codificado
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Inquilino> filtrados(FiltroInquilino  filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto
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

	// metodo que realiza a consulta e retorna para os metodos acima, dessa forma nao repetindo codigo
	private Criteria criarCriteriaParaFiltro(FiltroInquilino filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Inquilino.class);

		if (filtro.getNome() != null) {
			criteria.add(Restrictions.like("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
	
}
