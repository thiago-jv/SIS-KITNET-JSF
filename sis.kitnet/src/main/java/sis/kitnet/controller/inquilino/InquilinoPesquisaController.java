package sis.kitnet.controller.inquilino;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.filtrosPesquisa.FiltroInquilino;
import sis.kitnet.geradorRelatorio.ExecutorRelatorio;
import sis.kitnet.model.Inquilino;
import sis.kitnet.repository.Inquilinos;
import sis.kitnet.service.InquilinoService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class InquilinoPesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Inquilino inquilino;

	@Inject
	private Inquilinos inquilinos;

	@Inject
	private InquilinoService inquilinoService;

	@Getter
	@Setter
	private FiltroInquilino filtro;

	@Getter
	private LazyDataModel<Inquilino> model;
	
	@Getter
	@Setter
	private Long id;

	public InquilinoPesquisaController() {
		filtro = new FiltroInquilino();
		model = new LazyDataModel<Inquilino>() {
			private static final long serialVersionUID = 1L;

			public List<Inquilino> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(inquilinos.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return inquilinos.filtrados(filtro);
			}

		};
	}

	public int qtdRegistros() {
		return inquilinos.quantidadeFiltrados(getFiltro());
	}

	public void excluir() {
		inquilinos.remover(inquilino);
		UtilMensagens.mensagemInformacao("Cliente " + inquilino.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			inquilinoService.salvar(inquilino);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_INQUILINO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return Navegacao.redirecionar(Constantes.EDICAO_INQUILINO_URL);

	}
	
	public void emitirRelatorioDeInquilinos() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", this.id);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inquilino/listadeinquilinos.jasper", response,
				parametros, "Lista de Inquilos.pdf");
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			UtilMensagens.mensagemErro("A execução do relatório não retornou dados.");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void selecionar(Inquilino inquilino) {
		RequestContext.getCurrentInstance().closeDialog(inquilino);
	}
	
	@SuppressWarnings("deprecation")
	public void abrirDialogInquilino() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", true);
		opcoes.put("contentHeight", 570);
		RequestContext.getCurrentInstance().openDialog("/dialogs/inquilino/PesquisaInquilinoDialog", opcoes, null);
	}

}
