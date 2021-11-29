package sis.kitnet.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
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
@Table(name = "INQUILINO")
public class Inquilino extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Length(max = 90, min = 5, message = "Nome é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Nome")
	@Column(name = "NOME", length = 90, nullable = false)
	private String nome;

	@Length(max = 90, min = 5, message = "Nome abreviado é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Nome abreviado")
	@Column(name = "NOME_ABREVIADO", length = 20, nullable = false)
	private String nomeAbreviado;

	@Email(message = "Informar um E-mail válido")
	@Column(name = "EMAIL", length = 90)
	private String email;

	@NotEmpty(message = "Insira um Contato")
	@Length(max = 15, min = 5, message = "Contato é permitido, máximo {max} e mínimo {min} caracteres.")
	@Column(name = "CONTATO", length = 15, nullable = false)
	private String contato;

	@Length(max = 20, min = 1, message = "Status é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Status")
	@Column(name = "STATUS", length = 20, nullable = false)
	private String status = Constantes.ATIVO;

	@Length(max = 20, min = 1, message = "Genero é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Genero")
	@Column(name = "GENERO", length = 20, nullable = false)
	private String genero = Constantes.MASCULINO;

	@Length(max = 15, message = "Cpf é permitido, máximo {max}")
	@NotEmpty(message = "Insira um Cpf")
	@Column(name = "cpf", length = 15, unique = true)
	private String cpf;

	public Inquilino() {
	}

	@Transient
	public boolean isEditando() {
		return getId() != null;
	}
	
	@Transient
	public List<String> statusInquilinos() {
		return Arrays.asList(Constantes.ATIVO, Constantes.INATIVO);
	}

	@Transient
	public List<String> generosInquilinos() {
		return Arrays.asList(Constantes.MASCULINO, Constantes.FEMENINO);
	}
}