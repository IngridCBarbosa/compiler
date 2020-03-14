package compilador;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Scanner {
	
	private boolean loop = true;
	
	private ReadeFile read = new ReadeFile();
	private static char caracter = ' ';
	
	
	private int linha = 1;
	private int coluna = 1;
	
	public Lexema scannerToken() throws IOException {
		String forma_token;
		caracter = read.leituraCaracterArquivo();
		forma_token = "" + caracter;
		Lexema lexema = null;
		while(loop) {
			
			// TIPO INTEIRO
			if(Character.isDigit(caracter)) {
				
				caracter = read.leituraCaracterArquivo();
				forma_token = forma_token + caracter;
				
				// TIPO FLOAT
				if(caracter == '.') {
					caracter = read.leituraCaracterArquivo();
					forma_token = forma_token + caracter;
					if(caracter == ';') {
						lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
						mensagemErroFloat(linha ,coluna,lexema);
						return lexema;
					}
					if(Character.isDigit(caracter)) {
						caracter = read.leituraCaracterArquivo();
						if(caracter == ';') {
							// float correto
						}
						if(caracter == ' ') {
							coluna++;
							// float correto 
						}
					}
				}
				if(caracter == ';') {
					// int correto
				}else if(caracter == ' ') {
					coluna++;
					// int correto
				}
				
			}
			// TIPO FLOAT
			if(caracter == '.') {
				caracter = read.leituraCaracterArquivo();
				if(Character.isDigit(caracter)) {
					caracter = read.leituraCaracterArquivo();
					if(caracter == ';') {
						// float correto
						
					}
				}
				
				if(caracter == ';') {
					//mensagemErroFloat(linha,coluna);
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
			if(caracter == '|') {
				 lexema = new Lexema ("-1",Token.FIM_DE_ARQUIVO_TOKEN);
				 break;
			}
			
			
		}
		return lexema;
	
	}
	
	
	private void mensagemErroInt( int linha, int coluna) {
		System.err.println("ERRO linha "+linha+", coluna"+coluna);
	}
	
	private void mensagemErroFloat(int linha, int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" float mal formado");
	}
	
}
