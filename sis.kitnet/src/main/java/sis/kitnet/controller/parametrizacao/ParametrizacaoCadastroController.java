package sis.kitnet.controller.parametrizacao;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.service.ParametrizacaoService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ParametrizacaoCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Parametrizacao parametrizacao;
	
	@Inject
	private ParametrizacaoService parametrizacaoService;

	public void inicializar() {
		if (parametrizacao == null) {
			limpar();
		}
	}

	public void limpar() {
		parametrizacao = new Parametrizacao();
	}

	public String salvar() {
      try {
    	  parametrizacaoService.salvar(parametrizacao);
		UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
		inicializar();
		return Navegacao.redirecionar(Constantes.CADASTRO_PREDIO_URL);
      } catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
  	   return Navegacao.redirecionar(Constantes.CADASTRO_PREDIO_URL);
    
	}
	
}
