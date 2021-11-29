package sis.kitnet.controller.controle;

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
import sis.kitnet.filtrosPesquisa.FiltroControle;
import sis.kitnet.geradorRelatorio.ExecutorRelatorio;
import sis.kitnet.model.Apartamento;
import sis.kitnet.model.Controle;
import sis.kitnet.model.Valor;
import sis.kitnet.repository.Apartamentos;
import sis.kitnet.repository.Controles;
import sis.kitnet.repository.Valores;
import sis.kitnet.service.ControleService;
import sis.kitnet.util.AbstractManager;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class ControlePesquisaController extends AbstractManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Controle controle;

	@Inject
	private Controles controles;

	@Inject
	private ControleService controleService;

	@Getter
	@Setter
	private FiltroControle filtro;

	@Getter
	private LazyDataModel<Controle> model;

	@Getter
	@Setter
	private Long id;

	@Inject
	private Valores valores;
	
	@Inject
	private Apartamentos apartamentos;
	
	public ControlePesquisaController() {
		filtro = new FiltroControle();
		model = new LazyDataModel<Controle>() {
			private static final long serialVersionUID = 1L;

			public List<Controle> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(controles.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return controles.filtrados(filtro);
			}

		};
	}

	public int qtdRegistros() {
		return controles.quantidadeFiltrados(getFiltro());
	}

	public void excluir() {
		controles.remover(controle);
		UtilMensagens.mensagemInformacao("Controle " + controle.getId() + " excluído com sucesso.");
	}

	public String atualizar() {
		try {
			controleService.salvar(controle);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_CONTROLE_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return"";
	}
	
	public String atualizarStatusPagamento() {
		try {
			controleService.atualizarStatusPagamento(controle);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_CONTROLE_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return"";
	}
	
	public String atualizarStatusFechamento() {
		try {
			controleService.atualizarStatusFechamento(controle);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_CONTROLE_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return"";
	}

	public void emitirRelatorioDeControle() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", this.id);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/controle/relatoriodecontrole.jasper", response,
				parametros, "Lista de Controle.pdf");
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			UtilMensagens.mensagemErro("A execução do relatório não retornou dados.");
		}
	}

	public List<Valor> listarValores() {
		return valores.listar();
	}
	
	public List<Apartamento> listarApartamentos(){
		return apartamentos.listar();
	}

}
