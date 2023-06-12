package br.gov.cesarschool.poo.fidelidade.testes;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.gov.cesarschool.poo.fidelidade.cartao.dao.CartaoFidelidadeDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.dao.LancamentoExtratoDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoPontuacao;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoResgate;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.TipoResgate;
import br.gov.cesarschool.poo.fidelidade.cliente.dao.ClienteDAO;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Endereco;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Sexo;

public class TesteDAOGenerico {
	private static final long NUMERO_CARTAO = 12345678920230101L;
	private static final long NUMERO_CARTAO_2 = 98765432120230101L;
	private static final String CPF_CLI = "12345678900";
	@BeforeEach
	public void limparDados() {
		ExclusaoArquivos.limparDados(); 		
	}
	@Test
	public void testarDAOGenericoCliente() {
		System.out.println("################# Testando DAOGenerico e ClienteDAO ################# ");
		ClienteDAO daoCli = new ClienteDAO();
		Cliente c1 = new Cliente(
				CPF_CLI, "JOSA SILVA", Sexo.MASCULINO, new Date(), 
				1500.00, new Endereco("RUA B",666,"AP 801","55055001","RECIFE","PE","BR"));
		boolean res = daoCli.incluir(c1);
		Assertions.assertTrue(res);		
		Cliente cli2 = daoCli.buscar(CPF_CLI);
		Assertions.assertNotNull(cli2);
		Assertions.assertEquals(cli2.toString(), "Cliente(cpf=12345678900, nomeCompleto=JOSA SILVA, sexo=MASCULINO, renda=1500.0, endereco=Endereco" +
				"(logradouro=RUA B, numero=666, complemento=AP 801, cep=55055001, cidade=RECIFE, estado=PE, pais=BR))");
		Cliente cli3 = new Cliente(
				CPF_CLI, "MAURA SHAMPOO", Sexo.FEMININO, new Date(), 
				2000.00, new Endereco("RUA XXX",777,"AP 999","59999002","JAMPA","PB","US"));	
		res = daoCli.alterar(cli3);
		Assertions.assertTrue(res);
		Cliente cli4 = daoCli.buscar(CPF_CLI);
		Assertions.assertNotNull(cli4);
		Assertions.assertEquals(cli4.toString(), "Cliente(cpf=12345678900, nomeCompleto=MAURA SHAMPOO, " +
				"sexo=FEMININO, renda=2000.0, endereco=Endereco(logradouro=RUA XXX, numero=777, complemento=AP 999, cep=59999002, cidade=JAMPA, estado=PB, pais=US))");		
		Cliente cli5 = new Cliente(
				CPF_CLI, "LUIZ CARLOS", Sexo.MASCULINO, new Date(), 
				4000.00, new Endereco("RUA PPP",999,"AP 111","50000000","NATAL","RN","UK"));
		res = daoCli.incluir(cli5);
		Assertions.assertFalse(res);
		Cliente cli6 = new Cliente(
				"98765432100", "MARCOS", Sexo.MASCULINO, new Date(), 
				6000.00, new Endereco("RUA ABC",15,"AP 202","60999000","SALVADOR","BA","VZ"));
		res = daoCli.alterar(cli6);
		Assertions.assertFalse(res);
		Cliente cli7 = daoCli.buscar("11111111123");
		Assertions.assertNull(cli7);
	}
	@Test
	public void testarDAOGenericoCartao() {
		System.out.println("################# Testando DAOGenerico e CartaoFidelidadeDAO ################# ");
		CartaoFidelidade c1 = new CartaoFidelidade(NUMERO_CARTAO);
		c1.creditar(100);
		c1.debitar(60);
		CartaoFidelidadeDAO daoCart = new CartaoFidelidadeDAO();
		boolean res = daoCart.incluir(c1);		
		Assertions.assertTrue(res);		
		CartaoFidelidade c2 = daoCart.buscar(NUMERO_CARTAO+"");
		Assertions.assertNotNull(c2);
		Assertions.assertEquals(c2.toString(),"CartaoFidelidade(numero=12345678920230101, saldo=40.0)");			
		c2.creditar(200);
		res = daoCart.alterar(c2);
		Assertions.assertTrue(res);		
		CartaoFidelidade c3 = daoCart.buscar(NUMERO_CARTAO+"");
		Assertions.assertNotNull(c3);
		Assertions.assertEquals(c3.toString(), "CartaoFidelidade(numero=12345678920230101, saldo=240.0)");
		CartaoFidelidade c4 = new CartaoFidelidade(NUMERO_CARTAO);
		c4.creditar(500);		
		res = daoCart.incluir(c4);
		Assertions.assertFalse(res);
		CartaoFidelidade c5 = new CartaoFidelidade(NUMERO_CARTAO_2);
		c5.creditar(600);
		res = daoCart.alterar(c5);
		Assertions.assertFalse(res);
		CartaoFidelidade c6 = daoCart.buscar("88885678920239999");
		Assertions.assertNull(c6);
	}
	@Test
	public void testDAOGenericoLancamento() {
		System.out.println("################# Testando DAOGenerico e LancamentoExtratoDAO ################# ");
		LancamentoExtrato l1 = new LancamentoExtratoPontuacao(NUMERO_CARTAO, 100, LocalDateTime.now());
		LancamentoExtrato l2 = new LancamentoExtratoResgate(NUMERO_CARTAO, 200, LocalDateTime.now(), TipoResgate.PRODUTO);
		LancamentoExtratoDAO led = new LancamentoExtratoDAO();
		boolean res1 = led.incluir(l1);
		Assertions.assertTrue(res1);	
		res1 = led.incluir(l2);
		Assertions.assertTrue(res1);
		File dirBaseLancamento = new File(ExclusaoArquivos.DIR_BASE_LANCAMENTO);
		Assertions.assertEquals(dirBaseLancamento.list().length, 2);
	}
}
