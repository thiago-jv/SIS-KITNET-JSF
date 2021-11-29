package sis.kitnet.controller.usuario;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Grupo;
import sis.kitnet.model.Usuario;
import sis.kitnet.repository.Grupos;
import sis.kitnet.service.UsuarioService;
import sis.kitnet.util.Navegacao;
import sis.kitnet.util.UtilMensagens;

@Named
@ViewScoped
public class UsuarioCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Usuario usuario;

	@Getter
	@Setter
	private Grupo grupo;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private Grupos grupos;

	@Getter
	private List<Usuario> listaUsuarios;

	@Getter
	@Setter
	private List<Grupo> listaGrupos;

	@Getter
	@Setter
	private Long idGrupo;

	public void inicializar() {
		if (usuario == null) {
			limpar();
		}
		listaGrupos = grupos.listaGrupos();
	}

	public void limpar() {
		usuario = new Usuario();
	}

	public String salvar() {
		try {
			this.usuario = usuarioService.salvar(usuario);
			UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.CADASTRO_USUARIO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return "";
	}

	public String atualizar() {
		try {
			this.usuario = usuarioService.atualizar(usuario);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_USUARIO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return "";
	}
	
	public String atualizarSenha() {
		try {
			this.usuario = usuarioService.atualizarSenha(usuario);
			UtilMensagens.mensagemInformacao(Constantes.ATUALIZADO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_USUARIO_URL);
		} catch (RuntimeException e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		return "";
	}
	
	public String resetarSenha() {
		try {
			usuarioService.atualizarSenha(usuario);
			UtilMensagens.mensagemInformacao(Constantes.SALVO_COM_SUCESSO);
			return Navegacao.redirecionar(Constantes.EDICAO_USUARIO_URL);
		} catch (NegocioException ne) {
			UtilMensagens.mensagemErro(ne.getMessage());
		}
		return "";
	}

	public void adicionarGrupo() {
		grupo = grupos.porId(idGrupo);
		usuario.getGrupos().add(grupo);
	}

	public int qtdGrupos() {
		return usuario.getGrupos().size();
	}

	public void removerGrupoUsuario(Grupo grupo) {
		usuario.getGrupos().remove(grupo);
	}

	public List<Grupo> usuarioGrupos() {
		return this.usuario.getGrupos();
	}
}
