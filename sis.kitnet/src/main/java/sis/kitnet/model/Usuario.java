package sis.kitnet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Apelido: é obrigatório")
	@Column(name = "APELIDO", length = 31, nullable = false, unique = true)
	private String apelido;

	@NotNull(message = "Senha: é obrigatório")
	@Column(name = "SENHA", length = 90, nullable = false)
	private String senha;

	@Column(name = "ATIVO", length = 20, nullable = false)
	private boolean ativo;

	@NotNull(message = "Grupo: campo Obrigatório")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos = new ArrayList<Grupo>();

	public Usuario() {
	}

	@Override
	public String toString() {
		return "Usuario [grupos=" + grupos + "]";
	}

	@Transient
	public boolean isEditando() {
		return getId() != null;
	}

	@Transient
	public String senhaTransient;

	@Transient
	public String verificaSenhaTransient;

	@Transient
	public String novaSenhaTransient;

}
