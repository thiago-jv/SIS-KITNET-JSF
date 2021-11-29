package sis.kitnet.controller.valor;

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
import sis.kitnet.filtrosPesquisa.FiltroValor;
import sis.kitnet.geradorRelatorio.ExecutorRelatorio;
import sis.kitnet.model.Valor;
import sis.kitnet.repository.Valores;
import sis.kitnet.service.ValorService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ValorPesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Valor valor;

	@Inject
	private Valores valores;

	@Inject
	private ValorService valorService;

	@Getter
	@Setter
	private FiltroValor filtro;

	@Getter
	private LazyDataModel<Valor> model;
	
	@Getter
	@Setter
	private Long id;

	public ValorPesquisaController() {
		filtro = new FiltroValor();
		model = new LazyDataModel<Valor>() {
			private static final long serialVersionUID = 1L;

			public List<Valor> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(valores.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return valores.filtrados(filtro);
			}

		};
	}

	public int qtdRegistros() {
		return valores.quantidadeFiltrados(getFiltro());
	}

	public void excluir() {
		valores.remover(valor);
		UtilMensagens.mensagemInformacao("Valor " + valor.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			valorService.salvar(valor);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_VALOR_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return Navegacao.redirecionar(Constantes.EDICAO_VALOR_URL);

	}
	
	public void emitirRelatorioDeValores() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", this.id);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/valor/listadevalores.jasper", response,
				parametros, "Lista de Valor.pdf");
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			UtilMensagens.mensagemErro("A execução do relatório não retornou dados.");
		}
	}

}
