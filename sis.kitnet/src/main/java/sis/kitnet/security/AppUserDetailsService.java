package sis.kitnet.security;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Grupo;
import sis.kitnet.model.Usuario;
import sis.kitnet.repository.Usuarios;


public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String apelidoUsu) throws UsernameNotFoundException {
		Usuarios usuarios = CDIServiceLocator.getBean(Usuarios.class);
		Usuario usuario = usuarios.porApelilo(apelidoUsu);
		
		UsuarioSistema user = null;
		
		if (usuario != null && usuario.isAtivo()) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Grupo grupo : usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}

}
