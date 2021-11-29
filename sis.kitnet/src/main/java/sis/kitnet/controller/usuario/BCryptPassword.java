package sis.kitnet.controller.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPassword {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String digitado = "ADMIN";
		String senha = passwordEncoder.encode(digitado);
	    System.out.println("senha gerada : " +senha);
		

	}

}
