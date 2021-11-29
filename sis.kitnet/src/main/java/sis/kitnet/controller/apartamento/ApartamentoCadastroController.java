package sis.kitnet.controller.apartamento;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Apartamento;
import sis.kitnet.model.Predio;
import sis.kitnet.repository.Predios;
import sis.kitnet.service.ApartamentoService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ApartamentoCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Apartamento apartamento;
	
	@Inject
	private ApartamentoService apartamentoService;
	
	@Getter
	@Setter
	private Predio predio;
	
	@Inject
	private Predios predios;
	
	@Getter
	private List<Predio> listarPredios;
	
	public void inicializar() {
		if (apartamento == null) {
			limpar();
		}
		listarPredios = predios.listar();
	}

	public void limpar() {
		apartamento = new Apartamento();
		predio = new Predio();
	}

	public String salvar() {
      try {
		apartamentoService.salvar(apartamento);
		UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
		inicializar();
		return Navegacao.redirecionar(Constantes.CADASTRO_APARTAMENTO_URL);
      } catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
  	   return Navegacao.redirecionar(Constantes.CADASTRO_APARTAMENTO_URL);
    
	}
	
}
