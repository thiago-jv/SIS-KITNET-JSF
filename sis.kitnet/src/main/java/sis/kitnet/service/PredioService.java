package sis.kitnet.service;

import java.io.Serializable;

import javax.inject.Inject;

import sis.kitnet.jpa.Transactional;
import sis.kitnet.model.Predio;
import sis.kitnet.repository.Predios;

public class PredioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Predios predioRepository;
	
	@Transactional
	public Predio salvar(Predio predio) {
	 return	predioRepository.guardar(predio);
	}
	
}
