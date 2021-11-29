package sis.kitnet.controller.usuario;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.filtrosPesquisa.FiltroUsuario;
import sis.kitnet.model.Usuario;
import sis.kitnet.repository.Usuarios;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class UsuarioPesquisaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Usuario usuario;
	
	@Inject
	private Usuarios usuarios;

	@Getter
	@Setter
	private Long codigo;

	@Getter
	@Setter
	private FiltroUsuario filtro;
	
	public void init() {
		this.usuario = new Usuario();
	}

	@Getter
	@Setter
	private LazyDataModel<Usuario> model;

	public UsuarioPesquisaBean() {
		filtro = new FiltroUsuario();
		model = new LazyDataModel<Usuario>() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unused")
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(usuarios.quantidadeFiltrados(filtro));
				// retorna a lista filtrada
				return usuarios.filtrados(filtro);
			}
		};
	}

	public int qtdRegistros() {
		return usuarios.quantidadeFiltrados(getFiltro());
	}
	
	public void excluir() {
		usuarios.remover(usuario);
		UtilMensagens.mensagemInformacao("Usuario " + usuario.getApelido() + " excluído com sucesso.");
	}

}