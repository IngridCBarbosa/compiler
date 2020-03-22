package compilador;

import java.io.IOException;

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
						forma_token = forma_token + caracter;
						caracter = read.leituraCaracterArquivo();
						if(caracter == ';') {
							// float correto
							lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
							return lexema;
						}
						if(caracter == ' ') {
							coluna++;
							// float correto 
							lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
							return lexema;
						}
					}
					
					if(caracter == ' ') {
						lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
						coluna++;
						mensagemErroFloat(linha, coluna, lexema);
						return lexema;
					}
					if(caracter == '\n') {
						lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
						mensagemErroFloat(linha, coluna, lexema);
						return lexema;
					}
					// FIM DE ARQUIVO
					if(caracter == '|') {
						lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
						mensagemErroFloat(linha, coluna, lexema);
						return lexema;
					}
				}
				if(caracter == ';') {
					// int correto
					lexema = new Lexema(forma_token,Token.TIPO_INT_TOKEN);
					return lexema;
				}else if(caracter == ' ') {
					coluna++;
					// int correto
					lexema = new Lexema(forma_token,Token.TIPO_INT_TOKEN);
					return lexema;
				}
				if(Character.isLetter(caracter)) {
					forma_token = forma_token + caracter;
					lexema = new Lexema(forma_token, Token.TIPO_INT_TOKEN);
					mensagemErroInt(linha, coluna, lexema);
					return lexema;
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
			// FIM DE ARQUIVO
			if(caracter == '|') {
				 lexema = new Lexema ("-1",Token.FIM_DE_ARQUIVO_TOKEN);
				 return lexema;
			}
			
			
		}
		return null;
	
	}
	
	
	private void mensagemErroInt( int linha, int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" int mal formado");
	}
	
	
	
	private void mensagemErroFloat(int linha, int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" float mal formado");
	}
	
}
