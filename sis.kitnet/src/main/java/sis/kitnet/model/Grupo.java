package sis.kitnet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "GRUPO")
public class Grupo extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Nome : campo Obrigatório")
	@Column(name = "NOME", nullable = false, length = 40, unique = true)
	private String nome;

	@NotNull(message = "Descrição : campo Obrigatório")
	@Column(name = "DESCRICAO", nullable = false, length = 80, unique = true)
	private String descricao;

	public Grupo() {
	}

	@Override
	public String toString() {
		return "descricao=" + descricao + "";
	}

}