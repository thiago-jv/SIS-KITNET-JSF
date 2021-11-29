 package sis.kitnet.controller.apartamento;

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
import sis.kitnet.filtrosPesquisa.FiltroApartamento;
import sis.kitnet.geradorRelatorio.ExecutorRelatorio;
import sis.kitnet.model.Apartamento;
import sis.kitnet.model.Predio;
import sis.kitnet.repository.Apartamentos;
import sis.kitnet.repository.Predios;
import sis.kitnet.service.ApartamentoService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ApartamentoPesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Apartamento apartamento;

	@Inject
	private Apartamentos apartamentos;

	@Inject
	private ApartamentoService apartamentoService;

	@Getter
	@Setter
	private FiltroApartamento filtro;

	@Getter
	private LazyDataModel<Apartamento> model;
	
	@Getter
	@Setter
	private Long id;
	
	@Inject
	private Predios predios;

	public ApartamentoPesquisaController() {
		filtro = new FiltroApartamento();
		model = new LazyDataModel<Apartamento>() {
			private static final long serialVersionUID = 1L;

			public List<Apartamento> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(apartamentos.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return apartamentos.filtrados(filtro);
			}

		};
	}

	public int qtdRegistros() {
		return apartamentos.quantidadeFiltrados(getFiltro());
	}

	public void excluir() {
		apartamentos.remover(apartamento);
		UtilMensagens.mensagemInformacao("Apartamento " + apartamento.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			apartamentoService.salvar(apartamento);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_APARTAMENTO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return"";

	}
	
	public void emitirRelatorioDeApartamentos() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", this.id);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/apartamento/listadeapartamentos.jasper", response,
				parametros, "Lista de Apartamento.pdf");
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			UtilMensagens.mensagemErro("A execução do relatório não retornou dados.");
		}
	}
	
	public List<Predio> listarPredios(){
		return predios.listar();
	}

}
