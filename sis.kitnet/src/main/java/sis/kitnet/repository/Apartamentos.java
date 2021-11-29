package sis.kitnet.repository;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.filtrosPesquisa.FiltroApartamento;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Apartamento;
import sis.kitnet.util.AbstractManager;

public class Apartamentos extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Apartamento guardar(Apartamento apartamento) {	
		return manager.merge(apartamento);
	}
	
	@Transactional
	public void remover(Apartamento apartamento) {
		try {
			apartamento = porId(apartamento.getId());
			manager.remove(apartamento);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Apartamento> listar() {
		String sql = "select distinct a from Apartamento a order by a.id";
		return manager.createQuery(sql, Apartamento.class).getResultList();
	}
	
	public List<Apartamento> listarApartamentosDisponiveis(String numero) {
		return this.manager
				.createQuery("select distinct a from Apartamento a where a.numero = :pNumero", Apartamento.class)
				.setParameter("pNumero", numero).getResultList();
	}
	
	public Apartamento porId(Long id) {
		return manager.find(Apartamento.class, id);
	}
	
	public int quantidadeFiltrados(FiltroApartamento filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto.
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		// como ira retorna um numero, devemos fazer um cats conforme codificado
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Apartamento> filtrados(FiltroApartamento  filtro) {
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
	private Criteria criarCriteriaParaFiltro(FiltroApartamento filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Apartamento.class);

		if (filtro.getNumero() != null) {
			criteria.add(Restrictions.like("numero", filtro.getNumero(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
	
}
