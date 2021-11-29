package sis.kitnet.controller.predio;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.filtrosPesquisa.FiltroPredio;
import sis.kitnet.geradorRelatorio.ExecutorRelatorio;
import sis.kitnet.model.Predio;
import sis.kitnet.repository.Predios;
import sis.kitnet.service.PredioService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class PredioPesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Predio predio;

	@Inject
	private Predios predios;

	@Inject
	private PredioService predioService;

	@Getter
	@Setter
	private FiltroPredio filtro;

	@Getter
	private LazyDataModel<Predio> model;
	
	@Getter
	@Setter
	private Long id;

	public PredioPesquisaController() {
		filtro = new FiltroPredio();
		model = new LazyDataModel<Predio>() {
			private static final long serialVersionUID = 1L;

			public List<Predio> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(predios.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return predios.filtrados(filtro);
			}

		};
	}

	public int qtdRegistros() {
		return predios.quantidadeFiltrados(getFiltro());
	}

	public void excluir() {
		predios.remover(predio);
		UtilMensagens.mensagemInformacao("Predio " + predio.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			predioService.salvar(predio);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_PREDIO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return Navegacao.redirecionar(Constantes.EDICAO_PREDIO_URL);

	}
	
	public void emitirRelatorioDePredios() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", this.id);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/predio/listadepredios.jasper", response,
				parametros, "Lista de predios.pdf");
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			UtilMensagens.mensagemErro("A execução do relatório não retornou dados.");
		}
	}

}
