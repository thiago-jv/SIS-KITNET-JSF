package sis.kitnet.constante;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Named;

@Named
public class Constantes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final BigDecimal VALOR_ZERO = new BigDecimal(0);
	public static final String BANDEIRA_AMARELA = "0.960143";
	
	public static final String DISPONIVEL = "DISPONIVEL";
	public static final String OCUPADO = "OCUPADO";
	public static final String MANUTENCAO = "MANUTENCAO";
	
	public static final String ATIVO = "ATIVO";
	public static final String INATIVO = "INATIVO";
	
	public static final String PAGO = "PAGO";
	public static final String DEBITO = "DEBITO";
	
	public static final String ABERTO = "ABERTO";
	public static final String FECHADO = "FECHADO";
	
	public static final String SIM = "SIM";
	public static final String NAO = "NAO";
	
	public static final String MASCULINO = "MASCULINO";
	public static final String FEMENINO = "FEMENINO";
	public static final String OUTROS = "OUTROS";
	
	public static final String ADMINISTRADOR = "ADMINISTRADOR";
	
	public static final String VALIDA_DATAS_CONTROLE = "Dt. Prox. Pagamento não pode ser maior que Dt. Entrada";
	public static final String VALIDA_APARTAMENTO_LOCADO = "O apatamento não pode ser locado, pois está em uso!";
	public static final String VALIDA_STATUS_DEBITO = " encontra-se em debito, favor realizar a quitação do proximo pagamento!";
	
	public static final String VALIDA_UNIQUE_VALOR = "A nível de regra de negócio, o sistema permite apenas que seja inserido um registro de Parametrização!";
	
	public static final String RESTRICAO_USUARIO = "Usuario não pode ser excluído, pois possui referências em outras tabelas!";
	public static final String RESTRICAO_USUARIO_SENHA = "As senhas devem ser iguais, favor realizar verificação!";
	public static final String RESTRICAO_USUARIO_GRUPO = "Favor informar um grupo";
	
	public static final String SALVO_COM_SUCESSO = "Salvo com sucesso.";
	public static final String ATUALIZADO_COM_SUCESSO = "Atualizado com sucesso.";
	
	public static final String RELATORIO_SEM_DADOS = "A execução do relatório não retornou dados.";
	public static final String ERRO_EXECUTAR_RELATORIO = "Erro ao executar relatório";
	
	// URLS //
	public static final String CADASTRO_INQUILINO_URL = "novoInquilino";
	public static final String EDICAO_INQUILINO_URL = "pesquisaInquilino";
	
	public static final String CADASTRO_VALOR_URL = "novoValor";
	public static final String EDICAO_VALOR_URL = "pesquisaValor";
	
	public static final String CADASTRO_PREDIO_URL = "novoPredio";
	public static final String EDICAO_PREDIO_URL = "pesquisaPredio";
	
	public static final String CADASTRO_PARAMETRIZACAO_URL = "novoParametrizacao";
	public static final String EDICAO_PARAMETRIZACAO_URL = "pesquisaParametrizacao";
	
	public static final String CADASTRO_APARTAMENTO_URL = "novoApartamento";
	public static final String EDICAO_APARTAMENTO_URL = "pesquisaApartamento";
	
	public static final String CADASTRO_CONTROLE_URL = "novoControle";
	public static final String EDICAO_CONTROLE_URL = "pesquisaControle";
	
	public static final String EDICAO_USUARIO_URL = "pesquisaUsuario";
	public static final String CADASTRO_USUARIO_URL = "novoUsuario";

}
