package sis.kitnet.controller.valor;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Valor;
import sis.kitnet.service.ValorService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ValorCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Valor valor;
	
	@Inject
	private ValorService valorService;

	public void inicializar() {
		if (valor == null) {
			limpar();
		}
	}

	public void limpar() {
		valor = new Valor();
	}

	public String salvar() {
      try {
    	  valorService.salvar(valor);
		UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
		inicializar();
		return Navegacao.redirecionar(Constantes.CADASTRO_VALOR_URL);
      } catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
  	   return Navegacao.redirecionar(Constantes.CADASTRO_VALOR_URL);
    
	}
	
}
