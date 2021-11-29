package sis.kitnet.service;

import java.io.Serializable;

import javax.inject.Inject;

import sis.kitnet.jpa.Transactional;
import sis.kitnet.model.Valor;
import sis.kitnet.repository.Valores;

public class ValorService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Valores valorRepository;
	
	@Transactional
	public Valor salvar(Valor valor) {
	 return	valorRepository.guardar(valor);
	}
	
}
