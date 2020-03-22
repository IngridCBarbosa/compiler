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
					if(caracter == ';') {
						lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
						mensagemErroFloat(linha ,coluna,lexema);
						//return lexema;
						break;
					}
					if(Character.isDigit(caracter)) {
						forma_token = forma_token + caracter;
						caracter = read.leituraCaracterArquivo();
						if(caracter == ';') {
							// float correto
							lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
							//return lexema;
							break;
						}
						if(caracter == ' ') {
							coluna++;
							// float correto 
							lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
							//return lexema;
							break;
						}
					}
					if(Character.isLetter(caracter)) {
						lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
						mensagemErroFloat(linha, coluna, lexema);
						break;
					}
					
					if(caracter == ' ') {
						lexema = new Lexema(forma_token,Token.TIPO_FLOAT_TOKEN);
						coluna++;
						mensagemErroFloat(linha, coluna, lexema);
						//return lexema;
						break;
					}
					
				
					
					
				}
				if(caracter == ';') {
					// int correto
					lexema = new Lexema(forma_token,Token.TIPO_INT_TOKEN);
					//return lexema;
					break;
				}else if(caracter == ' ') {
					coluna++;
					// int correto
					lexema = new Lexema(forma_token,Token.TIPO_INT_TOKEN);
					//return lexema;
					break;
				}
				if(Character.isLetter(caracter)) {
					forma_token = forma_token + caracter;
					lexema = new Lexema(forma_token, Token.TIPO_INT_TOKEN);
					mensagemErroInt(linha, coluna, lexema);
					//return lexema;
					break;
				}
				
			}
			
			// TIPO FLOAT
			if(caracter == '.') {
				caracter = read.leituraCaracterArquivo();
				
				if(Character.isDigit(caracter)) {
					forma_token = forma_token + caracter;
					caracter = read.leituraCaracterArquivo();
					if(caracter == ';') {
						// float correto
						lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
						break;
					}
					if(caracter == ' ') {
						lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
						break;
					}
					
					
				}
				
				if(caracter == ';') {
					lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
					mensagemErroFloat(linha,coluna,lexema);
					break;
				}
				if(caracter == ' ') {
					lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
					mensagemErroFloat(linha,coluna,lexema);
					break;
				}
				if(Character.isWhitespace(caracter)) {
					lexema = new Lexema(forma_token, Token.TIPO_FLOAT_TOKEN);
					mensagemErroFloat(linha,coluna,lexema);
					break;
				}
				
					
			}
			
			
			
			if(Character.isWhitespace(caracter)) {
				
				
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
				 //return lexema;
				 break;
			}
			
			// TIPO CHAR
			
			 int aspasSimplesCodigo = 39;
			 char aspasSimples = (char)aspasSimplesCodigo;
			 
			 if(caracter == aspasSimples) {
				 caracter = read.leituraCaracterArquivo();
				 if(Character.isLetterOrDigit(caracter)) {
					 forma_token = forma_token + caracter;
					 caracter = read.leituraCaracterArquivo();
					 if(caracter == aspasSimples) {
						 lexema = new Lexema(forma_token, Token.TIPO_CHAR_TOKEN);
						 // return lexema;
						 break;
					 }
					 else {
						 lexema = new Lexema(forma_token,Token.TIPO_CHAR_TOKEN);
						 mensagemErroChar(linha, coluna, lexema);
						 break;
					 }
				 }
				 if(Character.isWhitespace(caracter)) {
					 forma_token = forma_token + caracter;
					 caracter = read.leituraCaracterArquivo();
					 if(caracter == aspasSimples) {
						 lexema = new Lexema (forma_token, Token.TIPO_CHAR_TOKEN);
						 //return lexema;
						 break;
					 }else {
						 lexema = new Lexema(forma_token,Token.TIPO_CHAR_TOKEN);
						 mensagemErroChar(linha, coluna, lexema);
						 break;
					 }
				 }
				 
			 }
			 
			 // PALAVRAS RESERVADA OU IDENTIDICADOR
			 if(Character.isLetter(caracter)) {
				 caracter = read.leituraCaracterArquivo();
				 if(Character.isLetter(caracter)) {
					 forma_token = forma_token + caracter;
					 caracter = read.leituraCaracterArquivo();
					 if(Character.isWhitespace(caracter)) {
						 
					 }
				 }
			 }
			 
			 // CERACTER ESPECIAS
			 if(caracter == '(') {
				 lexema = new Lexema(forma_token, Token.ABRE_PARENTESE_TOKEN);
				 break;
			 }
			 if(caracter == ')') {
				 lexema = new Lexema(forma_token, Token.FECHA_PARENTESE_TOKEN);
				 break;
			 }
			 if(caracter == ',') {
				 lexema = new Lexema(forma_token, Token.VIRGULA_TOKEN);
				 break;
			 }
			 if(caracter == ';') {
				 lexema = new Lexema(forma_token, Token.PONTO_E_VIRGULA_TOKEN);
				 break;
			 }
			
			
		}// FIM DO LOOP
		
		
		return lexema;
	
	}
	
	
	private void mensagemErroInt( int linha, int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" int mal formado");
	}
	
	
	
	private void mensagemErroFloat(int linha, int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" float mal formado");
	}
	
	private void mensagemErroChar(int linha,int coluna,Lexema lexema) {
		System.err.println("ERRO linha "+linha+", coluna "+coluna+" token: "+lexema.getToken()+" char mal formado");
	}
	

	
	
}
