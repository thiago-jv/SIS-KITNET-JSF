package sis.kitnet.filtrosPesquisa;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class FiltroUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String apelido;
	
	@Getter
	@Setter
	private int primeiroRegistro;

	@Getter
	@Setter
	private int quantidadeRegistros;

	@Getter
	@Setter
	private boolean ascendente;

	@Getter
	@Setter
	private String propriedadeOrdenacao;

}