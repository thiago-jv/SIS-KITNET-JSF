package sis.kitnet.repository;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import sis.kitnet.filtrosPesquisa.FiltroControle;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Controle;
import sis.kitnet.util.AbstractManager;

public class Controles extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Controle guardar(Controle controle) {	
		return manager.merge(controle);
	}
	
	@Transactional
	public void remover(Controle controle) {
		try {
			controle = porId(controle.getId());
			manager.remove(controle);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Controle> listar() {
		String sql = "select distinct c from Controle c order by c.id";
		return manager.createQuery(sql, Controle.class).getResultList();
	}
	
	public Controle porId(Long id) {
		return manager.find(Controle.class, id);
	}
	
	public int quantidadeFiltrados(FiltroControle filtro) {
		// Rececebe o metodo privado com o select de pesquisa pronto.
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		// como ira retorna um numero, devemos fazer um cats conforme codificado
		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Controle> filtrados(FiltroControle  filtro) {
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
	private Criteria criarCriteriaParaFiltro(FiltroControle filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Controle.class);

		criteria.createAlias("inquilino", "i");
		criteria.createAlias("apartamento", "a");
		criteria.createAlias("valor", "v");
		
		if (filtro.getDataPagamentoDe() != null) {
			criteria.add(Restrictions.between("dataPagamento", filtro.getDataPagamentoDe(), filtro.getDataPagamentoAte()));
			criteria.add(Restrictions.like("i.nome", filtro.getInquilino(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("a.numero", filtro.getApartamento(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("statusProximoPagamento", filtro.getStatusProximoPagamento(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("v.valor", filtro.getValor()));
		}
		
		return criteria;
	}
	
}
