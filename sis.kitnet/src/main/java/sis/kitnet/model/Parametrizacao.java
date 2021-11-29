package sis.kitnet.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "PARAMETRIZACAO")
public class Parametrizacao extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Length(max = 60, min = 5, message = "E-mail Origem é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Email Origem")
	@Column(name = "EMAIL_ORIGEM", length = 60, unique = true,  nullable = false)
	private String emailOrigem;

	@Length(max = 60, min = 5, message = "Senha E-mail Origem é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira uma Senha E-mail Origem")
	@Column(name = "SENHA_EMAIL_ORIGEN", length = 40, unique = true,  nullable = false)
	private String senhaEmailOrigem;

	public Parametrizacao() {
	}
	
	@Transient
	public boolean isEditando() {
		return getId() != null;
	}

	@Transient
	public String EnviaEmailSim() {
		return Constantes.SIM;
	}
	
	@Transient
	public List<String> StatusEmail() {
		return Arrays.asList(Constantes.SIM, Constantes.NAO);
	}
}
