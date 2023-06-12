package br.gov.cesarschool.poo.fidelidade.testes;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.icu.impl.Assert;

import br.gov.cesarschool.poo.fidelidade.cartao.dao.LancamentoExtratoDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoPontuacao;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoResgate;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.RetornoConsultaExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.TipoResgate;
import br.gov.cesarschool.poo.fidelidade.cartao.negocio.CartaoFidelidadeMediator;
import br.gov.cesarschool.poo.fidelidade.cartao.relatorios.RelatorioExtrato;
import br.gov.cesarschool.poo.fidelidade.cliente.dao.ClienteDAO;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.cliente.relatorios.RelatorioCliente;
import br.gov.cesarschool.poo.fidelidade.excecoes.ExcecaoDadoInvalido;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Endereco;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Sexo;

public class TesteRelatoriosExcecoes {
	private static final long NUM_CARTAO_OK = 12345678920230101L; 
	private static final long NUM_CARTAO_2 = 98765432120230101L;
	private static final int TAM_CLIENTES = 10;
	private static final int TAM_LANCAMENTOS = 11;
	private static final long CPF_CLI = 12345678900L;
	private static final LocalDateTime DH_INIC = LocalDateTime.of(2023, Month.JULY, 29, 0, 0, 0);
	private static final LocalDateTime DH_FIM = LocalDateTime.of(2023, Month.AUGUST, 11, 23, 59, 59);
	private PrintStream outDoSystem; 
	private MeuPrintStream meuPs;
	
	private static final String OUT_REL_CLI = "ADRIANA ROBERTA - 03/12/2000 - 1.200,00AMARILIS COSTA - 09/12/2000 - " + 
			"1.900,00BRIANE SOUZA - 03/12/2000 - 1.300,00CARLOS ANDREOLI - 04/12/2000 - 1.400,00CARLOS MAZZO - 01/12/2000 - " + 
			"1.000,00EVERALDO TEIXEIRA - 08/12/2000 - 1.800,00FLAVIA PEREIRA - 07/12/2000 - 1.700,00FLAVIO MEIRA - 06/12/2000 - " + 
			"1.600,00SILENE MARIA - 05/12/2000 - 1.500,00ZOROBABEL SILVA - 02/12/2000 - 1.100,00";
	private static final String OUT_REL_LAN = "11/08/2023 - 5.000,00 - P11/08/2023 - 1.000,00 - R10/08/2023 - 6.000,00 - R06/08/2023" + 
			" - 3.000,00 - P30/07/2023 - 4.000,00 - R29/07/2023 - 2.000,00 - P12/11/2023 - 24.000,00 - R31/07/2023 - 11.000,00 - P"; 
	
	@BeforeEach
	public void prepararTeste() {
		outDoSystem = System.out;
		ExclusaoArquivos.limparDados();
		try {
			meuPs = new MeuPrintStream(outDoSystem);
			System.setOut(meuPs);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Test
	public void testarRelatorioClientes() {		
		outDoSystem.println("################# Testando Relatório de clientes ################# ");
		Cliente[] clientes = obterClientes();
		ClienteDAO daoCli = new ClienteDAO();
		boolean res = false;
		for (Cliente cliente : clientes) {
			res = daoCli.incluir(cliente);
			Assertions.assertTrue(res);
		}
		RelatorioCliente.gerarRelatorioClientes();				
		Assertions.assertEquals(meuPs.getText(), OUT_REL_CLI);		
	}
	@Test
	public void testarRelatorioLancamentos() {	
		outDoSystem.println("################# Testando Relatório de lançamentos ################# ");
		LancamentoExtrato[] lancamentos = obterLancamentos();
		LancamentoExtratoDAO lancDao = new LancamentoExtratoDAO();
		CartaoFidelidadeMediator cartaoMed = CartaoFidelidadeMediator.getInstance();
		boolean res = false;
		for (LancamentoExtrato lancamentoExtrato : lancamentos) {
			res = lancDao.incluir(lancamentoExtrato);
			Assertions.assertTrue(res);
		}
		RelatorioExtrato.gerarRelatorioExtratos(NUM_CARTAO_OK, DH_INIC, DH_FIM);
		RelatorioExtrato.gerarRelatorioExtratos(NUM_CARTAO_2, DH_INIC, null);		
		Assertions.assertEquals(meuPs.getText(), OUT_REL_LAN);
		assertFluxosExcepcionais(cartaoMed, null, DH_INIC, DH_FIM);
		assertFluxosExcepcionais(cartaoMed, "  ", DH_INIC, DH_FIM);
		assertFluxosExcepcionais(cartaoMed, NUM_CARTAO_OK+"", null, DH_FIM);
		assertFluxosExcepcionais(cartaoMed, NUM_CARTAO_OK+"", null, null);
		assertFluxosExcepcionais(cartaoMed, NUM_CARTAO_OK+"", DH_FIM, DH_INIC);
	}
	private void assertFluxosExcepcionais(CartaoFidelidadeMediator cartaoMed, String numCartao, 
			LocalDateTime inicio, LocalDateTime fim) {
		try {
			cartaoMed.consultaEntreDatas(numCartao, inicio, fim);
			Assertions.fail();
		} catch (ExcecaoDadoInvalido e) {
			Assertions.assertNotNull(e.getMessage());
		}		
	}
	private LancamentoExtrato[] obterLancamentos() {
		LancamentoExtrato[] lancamentos = new LancamentoExtrato[TAM_LANCAMENTOS];
		int i = 0;
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_OK, 1000, 
				LocalDateTime.of(2023, Month.AUGUST, 11, 4, 17, 38), TipoResgate.SERVICO);		
		lancamentos[i++] = new LancamentoExtratoPontuacao(NUM_CARTAO_OK, 2000, 
				LocalDateTime.of(2023, Month.JULY, 29, 0, 0, 0));
		lancamentos[i++] = new LancamentoExtratoPontuacao(NUM_CARTAO_OK, 10000, 
				LocalDateTime.of(2023, Month.JUNE, 27, 10, 24, 8));		
		lancamentos[i++] = new LancamentoExtratoPontuacao(NUM_CARTAO_OK, 3000, 
				LocalDateTime.of(2023, Month.AUGUST, 6, 9, 11, 31));
		lancamentos[i++] = new LancamentoExtratoPontuacao(NUM_CARTAO_2, 11000, 
				LocalDateTime.of(2023, Month.JULY, 31, 22, 21, 44));		
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_OK, 4000, 
				LocalDateTime.of(2023, Month.JULY, 30, 20, 20, 20), TipoResgate.VIAGEM);
		lancamentos[i++] = new LancamentoExtratoPontuacao(NUM_CARTAO_OK, 5000, 
				LocalDateTime.of(2023, Month.AUGUST, 11, 23, 59, 59));		
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_OK, 6000, 
				LocalDateTime.of(2023, Month.AUGUST, 10, 12, 3, 44), TipoResgate.PRODUTO);
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_OK, 22000, 
				LocalDateTime.of(2023, Month.OCTOBER, 2, 14, 17, 0), TipoResgate.VIAGEM);								
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_2, 19000, 
				LocalDateTime.of(2023, Month.JULY, 4, 1, 2, 42), TipoResgate.SERVICO);
		lancamentos[i++] = new LancamentoExtratoResgate(NUM_CARTAO_2, 24000, 
				LocalDateTime.of(2023, Month.NOVEMBER, 12, 18, 30, 32), TipoResgate.PRODUTO);				
		return lancamentos; 
	}
	private Cliente[] obterClientes() {
		Cliente[] clientes = new Cliente[TAM_CLIENTES];			
		clientes[0] = new Cliente(
				(CPF_CLI + 1)+"", "CARLOS MAZZO", Sexo.MASCULINO, new Date(100, 11, 1), 
				1000.00, new Endereco("RUA B",666,"AP 801","55055001","RECIFE","PE","BR"));
		clientes[1] = new Cliente(
				(CPF_CLI + 2)+"", "ZOROBABEL SILVA", Sexo.MASCULINO, new Date(100, 11, 2), 
				1000.00 + 100, new Endereco("RUA A",111,"AP 802","55055002","CARUARU","PE","BR"));			
		clientes[2] = new Cliente(
				(CPF_CLI + 3)+"", "ADRIANA ROBERTA", Sexo.FEMININO, new Date(100, 11, 3), 
				1000.00 + 200, new Endereco("RUA C",222,"AP 803","55055003","OLINDA","PE","BR"));			
		clientes[3] = new Cliente(
				(CPF_CLI + 4)+"", "BRIANE SOUZA", Sexo.FEMININO, new Date(100, 11, 3), 
				1000.00 + 300, new Endereco("RUA D",333,"AP 804","55055004","IPOJUCA","PE","BR"));
		clientes[4] = new Cliente(
				(CPF_CLI + 5)+"", "CARLOS ANDREOLI", Sexo.MASCULINO, new Date(100, 11, 4), 
				1000.00 + 400, new Endereco("RUA E",444,"AP 901","55055005","NATAL","RN","BR"));
		clientes[5] = new Cliente(
				(CPF_CLI + 6)+"", "SILENE MARIA", Sexo.FEMININO, new Date(100, 11, 5), 
				1000.00 + 500, new Endereco("RUA F",555,"AP 902","55055006","DALLAS","TX","US"));
		clientes[6] = new Cliente(
				(CPF_CLI + 7)+"", "FLAVIO MEIRA", Sexo.MASCULINO, new Date(100, 11, 6), 
				1000.00 + 600, new Endereco("RUA G",777,"AP 903","55055007","RECIFE","PE","BR"));
		clientes[7] = new Cliente(
				(CPF_CLI + 8)+"", "FLAVIA PEREIRA", Sexo.FEMININO, new Date(100, 11, 7), 
				1000.00 + 700, new Endereco("RUA H",888,"AP 904","55055008","RECIFE","PE","BR"));
		clientes[8] = new Cliente(
				(CPF_CLI + 9)+"", "EVERALDO TEIXEIRA", Sexo.MASCULINO, new Date(100, 11, 8), 
				1000.00 + 800, new Endereco("RUA I",2,"AP 1001","55055009","SOUZA","PB","BR"));			
		clientes[9] = new Cliente(
				(CPF_CLI + 10)+"", "AMARILIS COSTA", Sexo.FEMININO, new Date(100, 11, 9), 
				1000.00 + 900, new Endereco("RUA J",3,"AP 1002","55055009","ARACAJU","SE","BR"));
		return clientes;
	}
}
