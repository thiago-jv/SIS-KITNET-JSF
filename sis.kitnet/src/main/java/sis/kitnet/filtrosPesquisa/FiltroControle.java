package sis.kitnet.filtrosPesquisa;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.util.UtilDate;

public class FiltroControle {
	
	
	@Getter
	@Setter
	private Date dataPagamentoDe = new Date(System.currentTimeMillis());

	@Getter
	@Setter
	private Date dataPagamentoAte = UtilDate.calculaDataMaisDoisMeses(new Date(System.currentTimeMillis()));

	@Getter
	@Setter
	private String inquilino;
	
	@Getter
	@Setter
	private String apartamento;
	
	@Getter
	@Setter
	private BigDecimal valor;
	
	
	@Getter
	@Setter
	private String statusProximoPagamento;

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
