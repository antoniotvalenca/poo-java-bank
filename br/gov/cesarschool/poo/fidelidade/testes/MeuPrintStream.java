package br.gov.cesarschool.poo.fidelidade.testes;

import java.io.FileNotFoundException;
import java.io.PrintStream;

class MeuPrintStream extends PrintStream {
	private PrintStream psWrapped; 
	private StringBuffer text = new StringBuffer();
	MeuPrintStream(PrintStream psWrapped) throws FileNotFoundException {
		super("teste");
		this.psWrapped = psWrapped;
	}
	@Override
	public void println(String str) {
		psWrapped.println(str);
		text.append(str);
	}
	@Override
	public void println(Object obj) {
		psWrapped.println(obj);
		text.append(obj);
	}		
	String getText() {
		return text.toString();
	}
}