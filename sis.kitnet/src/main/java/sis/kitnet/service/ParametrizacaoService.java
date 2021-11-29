package sis.kitnet.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import sis.kitnet.constante.Constantes;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.repository.Parametrizacaos;

public class ParametrizacaoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Parametrizacaos parametrizacaoRepository;
	
	@Transactional
	public Parametrizacao salvar(Parametrizacao parametrizacao) {
		try {
			List<Parametrizacao> tamanho = parametrizacaoRepository.listar();
			if (tamanho.isEmpty()) {
				return	parametrizacaoRepository.guardar(parametrizacao);
			} else {
				throw new NegocioException(Constantes.VALIDA_UNIQUE_VALOR);
			 }	
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	
}
