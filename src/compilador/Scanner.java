package compilador;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Scanner {
	
	private boolean loop = true;
	
	private ReadeFile read = new ReadeFile();
	private static char caracter = ' ';
	
	private Queue<Token> filaToken = new LinkedList<Token>();

	private int linha = 1;
	private int coluna = 1;
	
	public void scannerToken() throws IOException {
		
		caracter = read.leituraCaracterArquivo();

		while(loop) {
			
			if(Character.isDigit(caracter)) {
				
				caracter = read.leituraCaracterArquivo();
				
				if(caracter == '.') {
					caracter = read.leituraCaracterArquivo();
					if(caracter == ';') {
						mensagemErroFloat(linha ,coluna);
						break;
					}
					if(Character.isDigit(caracter)) {
						caracter = read.leituraCaracterArquivo();
						if(caracter == ';') {
							// float correto 
						}
					}
				}
				if(caracter == ';') {
					// int correto
				}
				
			}
			
			if(caracter == '.') {
				caracter = read.leituraCaracterArquivo();
				if(Character.isDigit(caracter)) {
					caracter = read.leituraCaracterArquivo();
					if(caracter == ';') {
						// float correto
						filaToken.add(Token.TIPO_FLOAT);
					}
				}
				
				if(caracter == ';') {
					mensagemErroFloat(linha,coluna);
					break;
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
				caracter = read.leituraCaracterArquivo();
			}
			
			
		}
	}
	
	
	private void mensagemErroInt( int linha, int coluna) {
		System.err.println("ERRO linha "+linha+", coluna"+coluna);
	}
	
	private void mensagemErroFloat(int linha, int coluna) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+"");
	}
	
}
