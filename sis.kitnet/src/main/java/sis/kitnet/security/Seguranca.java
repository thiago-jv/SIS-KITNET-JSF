package sis.kitnet.security;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.model.Grupo;
import sis.kitnet.model.Usuario;

@Named
@SessionScoped
public class Seguranca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private Grupo grupo;

	public String getNomeUsuario() {
		String nome = null;
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getApelido();
		}
		return nome;
	}

	public Usuario getIdUsuario() {
		Usuario usuario = null;
		UsuarioSistema usuarioLogado = getUsuarioLogado();

		if (usuarioLogado != null) {
			usuario = usuarioLogado.getUsuario();
		}
		return usuario;
	}

	public boolean isAdministradores() {
		List<Grupo> usuarioGrupos = getUsuarioLogado().getUsuario().getGrupos();
		for (Grupo grupo : usuarioGrupos) {
			if (grupo.getDescricao().equals(Constantes.ADMINISTRADOR)) {
				return true;
			}
		}
		return false;
	}

	private UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario;
	}

}
