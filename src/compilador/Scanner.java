package compilador;

import java.io.IOException;

public class Scanner {
	
	
	private ReadeFile read;
	private static char caracter = ' ';
	
	
	private static int linha = 0;
	private static int coluna = 0;
	
	public Scanner(String nomeArquivo) {
		read = new ReadeFile(nomeArquivo);
	}
	
	public Token scannerToken() throws IOException {
		
		String forma_lexema = "";
			
		
		while(Character.isWhitespace(caracter)) {
			
			if(caracter == '\t') {
				coluna = coluna + 4;
			}
			if (caracter == '\n') {
				linha++;
				coluna = 0;
				
			}
			else {
				coluna++;
				
			}
			
			
			caracter = read.leituraCaracterArquivo();
		}
		
		// NUMEROS INTEIROS
		if(Character.isDigit(caracter)) {
			while(Character.isDigit(caracter)) {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				// NUMERO FLOAT
				if(caracter == '.') {
					forma_lexema = forma_lexema + caracter;
					caracter = read.leituraCaracterArquivo();
					if(!Character.isDigit(caracter)) {
						mensagemDeErroFloat(linha, coluna);
						System.exit(0);
					}
					while(Character.isDigit(caracter)) {
						forma_lexema = forma_lexema + caracter;
						caracter = read.leituraCaracterArquivo();
					}
					return new Token(forma_lexema, Dicionario.TIPO_FLOAT_TOKEN);
					
				}
			}
			return new Token(forma_lexema, Dicionario.TIPO_INT_TOKEN);
		}
		
		 
		// NUMEROS FLOAT INICIADO POR PONTO
		if(caracter == '.') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			if(!Character.isDigit(caracter) ) {
				 mensagemDeErroFloat(linha, coluna);
				System.exit(0);
				
			}
			while(Character.isDigit(caracter)) {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.TIPO_FLOAT_TOKEN);
		}
		
		// TIPO CHAR
		int aspasSimplesCodigo = 39;
		char aspasSimples= (char) aspasSimplesCodigo;
		
		if(caracter == aspasSimples) {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			while(Character.isLetterOrDigit(caracter)) {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				if(caracter == aspasSimples) {
					forma_lexema = forma_lexema + caracter;
					caracter = read.leituraCaracterArquivo();
					return new Token(forma_lexema, Dicionario.TIPO_CHAR_TOKEN);
				}
				while(Character.isLetterOrDigit(caracter)) {
					forma_lexema = forma_lexema + caracter;
					caracter = read.leituraCaracterArquivo();
					if(caracter == aspasSimples) {
						forma_lexema = forma_lexema + caracter;
						mensagemDeErroChar(linha, coluna);
						System.exit(0);
					
					}
				}
				if(caracter != aspasSimples) {
					mensagemDeErroChar(linha, coluna);
					System.exit(0);
				}
				
			}
		}
		
		// CARACTERES ESPECIAIS
		// ABRE PARENTESE
		if(caracter == '(') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.ABRE_PARENTESE_TOKEN);
		}
		// FECHA PARENTESE
		if(caracter == ')') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.FECHA_PARENTESE_TOKEN);
		}
		// ABRE CHAVE
		if(caracter == '{') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.ABRE_CHAVE_TOKEN);
		}
		// FECHA CHAVES
		if(caracter == '}') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.FECHA_CHAVE_TOKEN);
		}
		
		// PONTO E VÍRGULA
		if(caracter == ';') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.PONTO_E_VIRGULA_TOKEN);
		}
		
		// VÍRGULA
		if(caracter == ',') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.VIRGULA_TOKEN);
		}
		
		// OPERADOR ARITMÉTICO IGUAL
		
		if(caracter == '=') {
			forma_lexema = forma_lexema+caracter;
			caracter = read.leituraCaracterArquivo();
			// OPERADOR RELACIONAL IGUAL IGUAL
			if(caracter == '=') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				return new Token(forma_lexema,Dicionario.OP_RELACIONAL_IGUAL_IGUAL);
			}
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_IGUAL_TOKEN);
		}
		// OPERADOR ARITMETICO SOMA
		if(caracter == '+') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_ADICAO_TOKEN);
		}
		// OPERADOR ARITMÉTICO SUBTRAÇÃO
		if(caracter == '-') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_SUBTRACAO_TOKEN);
		}
		// OPERADOR ARITMÉTICO MULTIPLICAÇÃO
		if(caracter == '*') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_MULTIPLICACAO_TOKEN);
		}
		
		// OPERADOR RELACIONAL MAIOR / MAIOR IGUAL
		if(caracter == '>'){
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			if(caracter == '=') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				return new Token(forma_lexema, Dicionario.OP_RELACIONAL_MAIOR_IGUAL_TOKEN);
			}
			return new Token(forma_lexema, Dicionario.OP_RELACIONAL_MAIOR_TOKEN);
		}
		
		// OPERADOR RELACIONAL MENOR / MENOR IGUAL
		if(caracter == '<') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			if(caracter == '=') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				return new Token(forma_lexema,Dicionario.OP_RELACIONAL_MENOR_IGUAL_TOKEN);
			}
			return new Token(forma_lexema,Dicionario.OP_RELACIONAL_MENOR_TOKEN);
		}
		
		// OPERADOR RELACIONAL DEFERENTE
		if(caracter == '!') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			if(caracter == '=') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
				return new Token(forma_lexema, Dicionario.OP_RELACIONAL_DIFERENTE_TOKEN);
			}
			mensagemOperadorDiferenteIncompleto(linha, coluna);;
			System.exit(0);
		}
		
		// COMENTARIO LINHA
		if(caracter == '/') {
			forma_lexema = forma_lexema + caracter;
			caracter = read.leituraCaracterArquivo();
			if(caracter == '/') {
				while(caracter != '\n') {
					if(caracter == ' ') {
						coluna++;
					}
					if( caracter == '\t') {
						coluna = coluna + 4;
					}
					caracter = read.leituraCaracterArquivo();
				}
				if(caracter == '\n') {
					linha++;
					coluna = 0;
					// caracter = read.leituraCaracterArquivo();
					return scannerToken();
				}
			}
			// COMENTARIO BLOCO
			if(caracter == '*') {
				caracter = read.leituraCaracterArquivo();
				
				while(true) {
					caracter = read.leituraCaracterArquivo();
					if(caracter == '*') {
						caracter = read.leituraCaracterArquivo();
						if(caracter == '/') {
							break;
						}
					}
					// FIM DE ARQUIVO
					if(caracter == '|') {
						break;
					}
				}
				
				if(caracter == '/') {	
					caracter = read.leituraCaracterArquivo();
					return scannerToken();
				}
				else {
					mensagemComentarioBlocoErro(linha, coluna);						
					System.exit(0);
				}
			}
			return new Token (forma_lexema,Dicionario.OP_ARITMETICO_DIVISAO_TOKEN);
		}
		
	
		
		// PALAVRA RESERVADA OU IDENTIFICADORES
		if( caracter == '_' || Character.isLetter(caracter)) {
			while(Character.isLetter(caracter) || caracter == '_' || Character.isDigit(caracter)) {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			Token token = buscaPalavraReservada(forma_lexema);
			return token;
		}
		if(caracter == '|') {
			return new Token("-1", Dicionario.FIM_DE_ARQUIVO_TOKEN);
		}
		
		mensagemCaracterInexistente(linha, coluna);
		System.exit(0);
		return null;
		
	}
	
	private Token buscaPalavraReservada(String p) {
		
		switch (p) {
		case "int":
			return new Token(p, Dicionario.PR_INT_TOKEN);
		case "if":
			return new Token(p, Dicionario.PR_IF_TOKEN); 
		case "else":
			return new Token(p, Dicionario.PR_ELSE_TOKEN);
		case "while":
			return new Token(p, Dicionario.PR_WHILE_TOKEN);
		case "do":
			return new Token(p, Dicionario.PR_DO_TOKEN);
		case "for":
			return new Token(p,Dicionario.PR_FOR_TOKEN);
		case "float":
			return new Token(p,Dicionario.PR_FLOAT_TOKEN);
		case "char":
			return new Token(p, Dicionario.PR_CHAR_TOKEN);
		case "main":
			return new Token(p, Dicionario.PR_MAIN_TOKEN);
		default: return new Token(p, Dicionario.IDENTIFICADOR_TOKEN);
		}
		
	}
	
	
	
	public static int getLinha() {
		return linha;
	}

	public static int getColuna() {
		return coluna;
	}

	
	private void mensagemDeErroFloat(int linha, int coluna) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Float mal formado");
	}
	
	private void mensagemDeErroChar(int linha, int coluna) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Char mal formado");
	}
	
	private void mensagemCaracterInexistente(int linha, int colune) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Caracter Invalido");
	}
	private void mensagemComentarioBlocoErro(int linha, int colune) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". EOF sem fechar o comentário");
	}
	private void mensagemOperadorDiferenteIncompleto(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". ! não sucedida de =");
	}
	
}
