package sis.kitnet.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
@Table(name = "APARTAMENTO")
public class Apartamento extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Length(max = 10, min = 1, message = "Número é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Número")
	@Column(name = "NUMERO", length = 10, nullable = false, unique = true)
	private String numero;

	@Length(max = 10, min = 1, message = "Descrição é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira uma Descrição")
	@Column(name = "DESCRICAO", length = 90, nullable = false, unique = true)
	private String descricao;

	@Length(max = 10, min = 1, message = "Medidor é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Medidor")
	@Column(name = "MEDIDOR", length = 10, unique = true)
	private String medidor;

	@NotEmpty(message = "Insira um Status")
	@Column(name = "STATUS_APARTAMENTO", length = 20, nullable = false)
	private String statusApartamento = Constantes.DISPONIVEL;

	@NotNull(message = "Insira um Predio")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_PREDIO", referencedColumnName = "ID", nullable = false)
	private Predio predio;

	public Apartamento() {
	}

	@Transient
	public boolean isEditando() {
		return getId() != null;
	}
	
	@Transient
	public List<String> statusApartamentos() {
		return Arrays.asList(Constantes.DISPONIVEL, Constantes.OCUPADO);
	}

}
