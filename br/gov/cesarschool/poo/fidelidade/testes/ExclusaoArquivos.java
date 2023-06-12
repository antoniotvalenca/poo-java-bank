package br.gov.cesarschool.poo.fidelidade.testes;

import java.io.File;

public class ExclusaoArquivos {
	private static final String FILE_SEP = System.getProperty("file.separator");
	static final String DIR_BASE_CLI = "." + FILE_SEP + "fidelidade" + FILE_SEP + "cliente" + FILE_SEP;
	static final String DIR_BASE_CARTAO = "." + FILE_SEP + "fidelidade" + FILE_SEP + "cartao" + FILE_SEP;
	static final String DIR_BASE_LANCAMENTO = "." + FILE_SEP + "fidelidade" + FILE_SEP + "lancamento" + FILE_SEP;
	static void limparDados() {
		deleteClientes();
		deleteCartoes();
		deleteLancamentos();
	}
	private static void deleteClientes() {
		deleteDir(DIR_BASE_CLI, "clientes");
	}
	private static void deleteCartoes() {
		deleteDir(DIR_BASE_CARTAO, "cart�es");
	}
	private static void deleteLancamentos() {
		deleteDir(DIR_BASE_LANCAMENTO, "lan�amentos");
	}
	
	private static void deleteDir(String diretorio, String entidade) {
		File dirCli = new File(diretorio);
		File[] arqs = dirCli.listFiles();
		boolean res = false;
		if (arqs != null) {
			for (File file : arqs) {
				res = file.delete();
				if (!res) {
					System.out.println("Nem todos os arquivos de "+ entidade + " foram apagados para execu��o dos testes de relat�rio.");
					System.out.println("Apague os arquivos pelo sistema de arquivos!");
					System.exit(1);
				}
			}
		} 	
	}	
}
