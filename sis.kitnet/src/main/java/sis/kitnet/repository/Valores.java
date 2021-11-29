package sis.kitnet.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.filtrosPesquisa.FiltroValor;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Valor;
import sis.kitnet.util.AbstractManager;

public class Valores extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Valor guardar(Valor valor) {	
		return manager.merge(valor);
	}
	
	@Transactional
	public void remover(Valor valor) {
		try {
			valor = porId(valor.getId());
			manager.remove(valor);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Valor> listar() {
		String sql = "select distinct v from Valor v order by v.id";
		return manager.createQuery(sql, Valor.class).getResultList();
	}
	
	public Valor porId(Long id) {
		return manager.find(Valor.class, id);
	}
	
	public int quantidadeFiltrados(FiltroValor filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto.
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		// como ira retorna um numero, devemos fazer um cats conforme codificado
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Valor> filtrados(FiltroValor  filtro) {
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
	private Criteria criarCriteriaParaFiltro(FiltroValor filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Valor.class);

		if (filtro.getValor() != null) {
			criteria.add(Restrictions.eq("valor", filtro.getValor()));
		}
		return criteria;
	}
	
}
