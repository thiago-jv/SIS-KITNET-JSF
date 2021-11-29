package sis.kitnet.repository;

import java.io.Serializable;
import java.util.List;

import sis.kitnet.model.Grupo;
import sis.kitnet.util.AbstractManager;

public class Grupos extends AbstractManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}

	public Grupo porId(Long idGrupo) {
		return manager.find(Grupo.class, idGrupo);
	}
	
	public List<Grupo> listaGrupos() {
		return manager.createQuery("select distinct g from Grupo g order by g.id desc", Grupo.class).getResultList();
	}
}
