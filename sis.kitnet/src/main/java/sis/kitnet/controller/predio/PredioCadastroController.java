package sis.kitnet.controller.predio;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Predio;
import sis.kitnet.service.PredioService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class PredioCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Predio predio;
	
	@Inject
	private PredioService predioService;

	public void inicializar() {
		if (predio == null) {
			limpar();
		}
	}

	public void limpar() {
		predio = new Predio();
	}

	public String salvar() {
      try {
    	  predioService.salvar(predio);
		UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
		inicializar();
		return Navegacao.redirecionar(Constantes.CADASTRO_PREDIO_URL);
      } catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
  	   return Navegacao.redirecionar(Constantes.CADASTRO_PREDIO_URL);
    
	}
	
}
