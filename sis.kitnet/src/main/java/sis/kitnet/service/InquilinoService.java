package sis.kitnet.service;

import java.io.Serializable;

import javax.inject.Inject;

import sis.kitnet.jpa.Transactional;
import sis.kitnet.model.Inquilino;
import sis.kitnet.repository.Inquilinos;

public class InquilinoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Inquilinos inquilinoRepository;
	
	@Transactional
	public Inquilino salvar(Inquilino inquilino) {
	 return	inquilinoRepository.guardar(inquilino);
	}
	
}
