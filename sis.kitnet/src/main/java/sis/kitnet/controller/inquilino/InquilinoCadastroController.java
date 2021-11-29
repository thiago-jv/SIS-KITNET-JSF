package sis.kitnet.controller.inquilino;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Inquilino;
import sis.kitnet.service.InquilinoService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class InquilinoCadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Inquilino inquilino;
	
	@Inject
	private InquilinoService inquilinoService;

	public void inicializar() {
		if (inquilino == null) {
			limpar();
		}
	}

	public void limpar() {
		inquilino = new Inquilino();
	}

	public String salvar() {
      try {
		inquilinoService.salvar(inquilino);
		UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
		inicializar();
		return Navegacao.redirecionar(Constantes.CADASTRO_INQUILINO_URL);
      } catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
  	   return Navegacao.redirecionar(Constantes.CADASTRO_INQUILINO_URL);
    
	}
	
}
