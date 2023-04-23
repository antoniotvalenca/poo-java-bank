package br.gov.cesarschool.poo.fidelidade.cartao.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;


public class CartaoFidelidadeDAO {
	private static final String FILE_SEP = System.getProperty("file.separator");
	private static final String DIR_BASE = "." + FILE_SEP + "cartaofidelidade" + FILE_SEP;
	private static final String EXT = ".dat";

	public CartaoFidelidadeDAO() {
		File diretorio = new File(DIR_BASE);
		if (!diretorio.exists()) {
			diretorio.mkdir();			
		}
	}

	private File getArquivo(long numero) {
		String nomeArq = DIR_BASE + numero + EXT;
		return new File(nomeArq);		
	}

	private void incluirAux(CartaoFidelidade cartao, File arq) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(arq);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(cartao);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao incluir cartão fidelidade");
		} finally {
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}			
		} 		
	}

	public boolean incluir(CartaoFidelidade cartao) {
		File arq = getArquivo(cartao.getNumero());
		if (arq.exists()) {
			return false; 
		}
		incluirAux(cartao, arq);
		return true; 
	}

	public boolean alterar(CartaoFidelidade cartao) {
		File arq = getArquivo(cartao.getNumero());
		if (!arq.exists()) {
			return false; 
		}		
		if (!arq.delete()) {
			return false;
		}
		incluirAux(cartao, arq);
		return true;
	}

	public boolean excluir(long numero) {
		File arq = getArquivo(numero);
		if (!arq.exists()) {
			return false; 
		}				
		return arq.delete();
	}

	public CartaoFidelidade buscar(long numero) {
		File arq = getArquivo(numero);
		if (!arq.exists()) {
			return null; 
		}				
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(arq);
			ois = new ObjectInputStream(fis);
			return (CartaoFidelidade) ois.readObject(); 
		} catch (Exception e) {
			throw new RuntimeException("Erro ao ler cartão fidelidade");
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
