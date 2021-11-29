package sis.kitnet.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import sis.kitnet.constante.Constantes;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Apartamento;
import sis.kitnet.model.Controle;
import sis.kitnet.repository.Apartamentos;
import sis.kitnet.repository.Controles;

public class ControleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Controles controleRepository;
	
	@Inject
	private Apartamentos apartamentoRepository;
	
	@Inject
	private ApartamentoService apartamentoService;

	@Transactional
	public Controle salvar(Controle controle) {
		try {
			controle.setDataLancamento(new Date(System.currentTimeMillis()));
			validarData(controle);
			validaApartamentoDisponivel(controle);
			return controleRepository.guardar(controle);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public Controle atualizarStatusPagamento(Controle controle) {
		try {
			return controleRepository.guardar(controle);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public Controle atualizarStatusFechamento(Controle controle) {
		try {
			if(controle.getStatusProximoPagamento().equals(Constantes.DEBITO)) {
				throw new NegocioException("Controle id "+controle.getId()+  Constantes.VALIDA_STATUS_DEBITO);	
			}
			validarApartamento(controle);
			return controleRepository.guardar(controle);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private void validarApartamento(Controle controle) {
		Long idApartamento = controle.getApartamento().getId();
		Apartamento ap = apartamentoRepository.porId(idApartamento);
		
		if(controle.getId() == null) {	
			ap.setStatusApartamento(Constantes.OCUPADO);
		} else {
			ap.setStatusApartamento(Constantes.DISPONIVEL);
		}
		apartamentoService.salvar(ap);	
	}

	public void validarData(Controle controle) {
		if (controle.getDataPagamento().before(controle.getDataEntrada())) {
			throw new NegocioException(Constantes.VALIDA_DATAS_CONTROLE);
		}
	}
	
	public List<Apartamento> validaApartamentoDisponivel(Controle controle) {
		List<Apartamento> apartamentos = apartamentoRepository.listarApartamentosDisponiveis(controle.getApartamento().getNumero());
		for (Apartamento apartamento : apartamentos) {
			if(apartamento.getStatusApartamento().equals(Constantes.OCUPADO)) {
				throw new NegocioException(Constantes.VALIDA_APARTAMENTO_LOCADO);		
			}
			else {
				validarApartamento(controle);
			}
		}
		return apartamentos;
	}

}
