package sis.kitnet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "PREDIO")
public class Predio extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Length(max = 90, min = 1, message = "Descrição é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira uma Descrição")
	@Column(name = "DESCRICAO", length = 90, nullable = false, unique = true)
	private String descricao;

	@Length(max = 8, min = 1, message = "Cep é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Cep")
	@Column(name = "CEP", length = 8, nullable = false)
	private String cep;

	@Column(name = "LOGRADOURO", length = 200)
	private String logradouro;

	@Column(name = "COMPLEMENTO", length = 90)
	private String complemento;

	@Length(max = 90, min = 1, message = "Bairro é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Bairro")
	@Column(name = "BAIRRO", length = 90)
	private String bairro;

	@Length(max = 10, min = 1, message = "UF é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um UF")
	@Column(name = "UF", length = 10)
	private String uf;

	@Column(name = "LOCALIDADE", length = 90)
	private String localidade;
	
	@Length(max = 10, min = 1, message = "Número é permitido, máximo {max} e mínimo {min} caracteres.")
	@NotEmpty(message = "Insira um Número")
	@Column(name = "NUMERO", length = 10)
	private String numero;

	public Predio() {
	}
	
	@Transient
	public boolean isEditando() {
		return getId() != null;
	}
	

}
