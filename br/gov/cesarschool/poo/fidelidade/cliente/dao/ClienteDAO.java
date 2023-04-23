package br.gov.cesarschool.poo.fidelidade.cliente.dao;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClienteDAO { 
	private static final String FILE_SEP = System.getProperty("file.separator");
	private static final String DIR_BASE = "." + FILE_SEP + "banco" + FILE_SEP 
			+ "cliente" + FILE_SEP; 
	private static final String EXT = ".dat";
	
	public ClienteDAO() {
		File diretorio = new File(DIR_BASE);
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
	}
	
	private File getArquivo(String cpf) {
		String nomeArq = DIR_BASE + cpf + EXT;
		return new File(nomeArq);		
	}
	
	private void incluirAux(Cliente cliente, File arq) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(arq);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(cliente);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao incluir cliente");
		} finally {
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}			
		} 		
	}
	
	public boolean incluir(Cliente cliente) {
		File arq = getArquivo(cliente.getCpf());
		if (arq.exists()) {
			return false; 
		}
		incluirAux(cliente, arq);
		return true; 
	}
	
	public boolean alterar(Cliente cliente) {
		File arq = getArquivo(cliente.getCpf());
		if (!arq.exists()) {
			return false; 
		}		
		if (!arq.delete()) {
			return false;
		}
		incluirAux(cliente, arq);
		return true;
	}
	
	public Cliente buscar(String cpf) {
		File arq = getArquivo(cpf);
		if (!arq.exists()) {
			return null; 
		}				
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(arq);
			ois = new ObjectInputStream(fis);
			return (Cliente)ois.readObject(); 
		} catch (Exception e) {
			throw new RuntimeException("Erro ao ler cliente");
		} finally {
			try {
				ois.close(); 
			} catch (Exception e) {}
			try {
				fis.close(); 
			} catch (Exception e) {}			
		}
	}
}
