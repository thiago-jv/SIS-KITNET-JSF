package sis.kitnet.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sis.kitnet.constante.Constantes;
import sis.kitnet.util.UtilDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "CONTROLE")
public class Controle extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_LANCAMENTO", nullable = false)
	private Date dataLancamento;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Insira uma Data Entrada")
	@Column(name = "DATA_ENTRADA", nullable = false)
	private Date dataEntrada = new Date(System.currentTimeMillis());

	@Temporal(TemporalType.DATE)
	@NotNull(message = "Insira uma Data próximo pagamento")
	@Column(name = "DATA_PROXIMO_PAGAMENTO", nullable = false)
	private Date dataPagamento = UtilDate.calculaDataMaisDoisMeses(new Date(System.currentTimeMillis()));
	
	@Column(name = "STATUS_PROXIMO_PAGAMENTO", nullable = false)
	private String statusProximoPagamento = Constantes.DEBITO;

	@Column(name = "STATUS_CONTROLE", nullable = false)
	private String statusControle = Constantes.ABERTO;
	
	@Length(max = 200, message = "Observação é permitido, máximo {max} caracteres.")
	@Column(name = "OBSERVACAO", length = 200)
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "FK_VALOR", referencedColumnName = "ID", nullable = false)
	private Valor valor;

	@ManyToOne
	@JoinColumn(name = "FK_INQUILINO", referencedColumnName = "ID", nullable = false)
	private Inquilino inquilino;

	@ManyToOne
	@JoinColumn(name = "FK_APARTAMENTO", referencedColumnName = "ID", nullable = false)
	private Apartamento apartamento;

	public Controle() {
	}
	
	@Transient
	public boolean isEditando() {
		return getId() != null;
	}
	
	@Transient
	public List<String> statusApartamentoApartamentoLuz() {
		return Arrays.asList(Constantes.PAGO, Constantes.DEBITO);
	}
	
	@Transient
	public List<String> statusProximoPagamentos() {
		return Arrays.asList(Constantes.PAGO, Constantes.DEBITO);
	}
	
	@Transient
	public List<String> statusFechamentoControle() {
		return Arrays.asList(Constantes.FECHADO, Constantes.ABERTO);
	}
	
	@Transient
	public boolean isPago() {
		return !statusProximoPagamento.equals(Constantes.PAGO);
	}
	
	@Transient
	public boolean isFechado() {
		return !statusControle.equals(Constantes.FECHADO);
	}

}
