package sis.kitnet.controller.parametrizacao;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.repository.Parametrizacaos;
import sis.kitnet.service.ParametrizacaoService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ParametrizacaoPesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Parametrizacao parametrizacao;

	@Inject
	private Parametrizacaos parametrizacaos;

	@Inject
	private ParametrizacaoService parametrizacaoService;

	public void excluir() {
		parametrizacaos.remover(parametrizacao);
		UtilMensagens.mensagemInformacao("Parametrização " + parametrizacao.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			parametrizacaoService.salvar(parametrizacao);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_PARAMETRIZACAO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return Navegacao.redirecionar(Constantes.EDICAO_PARAMETRIZACAO_URL);

	}

	public List<Parametrizacao> listarParametrizacao() {
		return parametrizacaos.listar();
	}
}
