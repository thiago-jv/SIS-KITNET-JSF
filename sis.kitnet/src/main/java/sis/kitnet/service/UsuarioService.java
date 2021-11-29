package sis.kitnet.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import sis.kitnet.constante.Constantes;
import sis.kitnet.jpa.Transactional;
import sis.kitnet.jsf.NegocioException;
import sis.kitnet.model.Usuario;
import sis.kitnet.repository.Usuarios;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			validarGrupo(usuario);
			validarSenha(usuario);
			usuario.setAtivo(true);
			return usuario = this.usuarios.guardar(usuario);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	public Usuario atualizar(Usuario usuario) {
		try {
			validarGrupo(usuario);
			return usuario = this.usuarios.guardar(usuario);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public Usuario atualizarSenha(Usuario usuario) {
		try {
			validarNovaSenha(usuario);
			return usuario = this.usuarios.guardar(usuario);
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	private void validarGrupo(Usuario usuario) {
		try {
			if (usuario.getGrupos().isEmpty()) {
				throw new NegocioException(Constantes.RESTRICAO_USUARIO_GRUPO);
			}
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	private void validarSenha(Usuario usuario) {
		try {
			usuario.senhaTransient = usuario.getSenha();
			if (!usuario.senhaTransient.equals(usuario.verificaSenhaTransient)) {
				throw new NegocioException(Constantes.RESTRICAO_USUARIO_SENHA);
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	private void validarNovaSenha(Usuario usuario) {
		try {
			if (!usuario.novaSenhaTransient.equals(usuario.verificaSenhaTransient)) {
				throw new NegocioException(Constantes.RESTRICAO_USUARIO_SENHA);
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			usuario.setSenha(passwordEncoder.encode(usuario.novaSenhaTransient));
		} catch (RuntimeException e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
