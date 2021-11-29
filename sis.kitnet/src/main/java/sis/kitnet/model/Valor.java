package sis.kitnet.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "VALOR")
public class Valor extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Insira um Valor")
	@Column(name = "VALOR", nullable = false, unique = true)
	private BigDecimal valor = BigDecimal.ZERO;
	
	@Length(max = 50, min = 5, message = "Valor Extensso é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Valor Extenso")
	@Column(name = "VALOR_EXTENSO", length = 50, nullable = false)
	private String valorExtenso;

	public Valor() {
	}

	@Transient
	public boolean isEditando() {
		return getId() != null;
	}

}