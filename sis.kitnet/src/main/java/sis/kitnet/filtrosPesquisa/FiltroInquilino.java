package sis.kitnet.filtrosPesquisa;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class FiltroInquilino implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String nome;

	// A partir de qual registro quero iniciar meu arquivo ou informacao
	@Getter
	@Setter
	private int primeiroRegistro;

	// Caso eu queira buscas os 5(cinco) primeiros registro, basta eu inputar o
	// quantitativo
	@Getter
	@Setter
	private int quantidadeRegistros;

	// Como sera a ordenacao, ascendente = ASC em sql ou descendente DESC em sql.
	@Getter
	@Setter
	private boolean ascendente;

	// qual propriedade que quero ordenar, sera descendente ou ascendente
	@Getter
	@Setter
	private String propriedadeOrdenacao;
}
