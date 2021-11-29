package sis.kitnet.service;

import java.io.Serializable;

import javax.inject.Inject;

import sis.kitnet.jpa.Transactional;
import sis.kitnet.model.Apartamento;
import sis.kitnet.repository.Apartamentos;

public class ApartamentoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Apartamentos apartamentoRepository;
	
	@Transactional
	public Apartamento salvar(Apartamento apartamento) {
	 return	apartamentoRepository.guardar(apartamento);
	}
	
}
