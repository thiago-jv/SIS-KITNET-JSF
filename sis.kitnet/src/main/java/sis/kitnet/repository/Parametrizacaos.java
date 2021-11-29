package sis.kitnet.repository;


import java.io.Serializable;
import java.util.List;

import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.util.AbstractManager;

public class Parametrizacaos extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Parametrizacao guardar(Parametrizacao parametrizacao) {	
		return manager.merge(parametrizacao);
	}
	
	@Transactional
	public void remover(Parametrizacao parametrizacao) {
		try {
			parametrizacao = porId(parametrizacao.getId());
			manager.remove(parametrizacao);
			manager.flush();
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public List<Parametrizacao> listar() {
		String sql = "select distinct p from Parametrizacao p order by p.id";
		return manager.createQuery(sql, Parametrizacao.class).getResultList();
	}
	
	public Parametrizacao porId(Long id) {
		return manager.find(Parametrizacao.class, id);
	}
	
}
