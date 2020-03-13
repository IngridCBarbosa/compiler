package compilador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
	
	private boolean loop = true;
	
	private ReadeFile read = new ReadeFile();
	private static char caracter = ' ';
	
	private List<String> buffer = new ArrayList<String>();

	private int linha = 0;
	private int coluna = 0;
	
	public void scannerToken() throws IOException {
		
		caracter = read.leituraCaracterArquivo();

		while(loop) {
			
			if(Character.isDigit(caracter)) {
				//buffer.add(String.valueOf(caracter)); // buffer ???
				caracter = read.leituraCaracterArquivo();
				
				if(caracter == '.') {
					caracter = read.leituraCaracterArquivo();
					if(caracter == ';') {
						mensagemErroFloat();
					}
					if(Character.isDigit(caracter)) {
						caracter = read.leituraCaracterArquivo();
						if(caracter == ';') {
							
						}
					}
				}
				
				
			}
			
			if(caracter == '.') {
				caracter = read.leituraCaracterArquivo();
				if(Character.isDigit(caracter)) {
					caracter = read.leituraCaracterArquivo();
				}
				
					
			}
			
			if(Character.isWhitespace(caracter)) {
				//caracter = new Character(read.leituraCaracterArquivo());
				
				if(caracter == '\t') { // TAB
					coluna = coluna + 4; 
				}
				
				if(caracter == '\n') { // NEW LINE
					linha++;
				}
				
				else {
					coluna++;
				}
				
			}
			
			
		}
	}
	
	
	private void mensagemErroInt() {
		System.err.println("int mal formado");
	}
	
	private void mensagemErroFloat() {
		System.err.println("fload mal formado");
	}
	
}
