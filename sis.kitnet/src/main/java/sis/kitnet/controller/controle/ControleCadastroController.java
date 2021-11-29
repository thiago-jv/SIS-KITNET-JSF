package sis.kitnet.controller.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Apartamento;
import sis.kitnet.model.Controle;
import sis.kitnet.model.Inquilino;
import sis.kitnet.model.Valor;
import sis.kitnet.repository.Apartamentos;
import sis.kitnet.repository.Inquilinos;
import sis.kitnet.repository.Valores;
import sis.kitnet.service.ControleService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ControleCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Controle controle;

	@Inject
	private ControleService controleService;

	@Inject
	private Inquilinos inquilinos;

	@Getter
	private List<Inquilino> listarInquilinos;

	@Inject
	private Apartamentos apartamentos;

	@Getter
	private List<Apartamento> listarApartamentos;

	@Inject
	private Valores valores;

	@Getter
	private List<Valor> listarValores;

	public void inicializar() {
		if (controle == null) {
			limpar();
		}
		listarInquilinos = inquilinos.listar();
		listarApartamentos = apartamentos.listar();
		listarValores = valores.listar();
	}

	public void limpar() {
		controle = new Controle();
	}

	public String salvar() {
		try {
			controleService.salvar(controle);
			UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
			inicializar();
			return Navegacao.redirecionar(Constantes.CADASTRO_CONTROLE_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return "";
	}

	/*
	 * Seleciona um inquilino na tela
	 */
	public void inquilinoSelecionado(SelectEvent event) {
		controle.setInquilino((Inquilino) event.getObject());
	}

	/*
	 * Parametro relacionado a tela suspensa do inquilino
	 */
	@NotBlank
	public String getNomeInquilino() {
		return controle.getInquilino() == null ? null : controle.getInquilino().getNome();
	}

	/*
	 * Parametro relacionado a tela suspensa do inquilino
	 */
	public void setNomeInquilino(String nome) {
	}

}
