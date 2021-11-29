package sis.kitnet.repository;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.filtrosPesquisa.FiltroPredio;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Predio;
import sis.kitnet.util.AbstractManager;

public class Predios extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Predio guardar(Predio predio) {	
		return manager.merge(predio);
	}
	
	@Transactional
	public void remover(Predio predio) {
		try {
			predio = porId(predio.getId());
			manager.remove(predio);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Predio> listar() {
		String sql = "select distinct p from Predio p order by p.id";
		return manager.createQuery(sql, Predio.class).getResultList();
	}
	
	public Predio porId(Long id) {
		return manager.find(Predio.class, id);
	}
	
	public int quantidadeFiltrados(FiltroPredio filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto.
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		// como ira retorna um numero, devemos fazer um cats conforme codificado
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Predio> filtrados(FiltroPredio  filtro) {
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
	private Criteria criarCriteriaParaFiltro(FiltroPredio filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Predio.class);

		if (filtro.getDescricao() != null) {
			criteria.add(Restrictions.like("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
	
}
