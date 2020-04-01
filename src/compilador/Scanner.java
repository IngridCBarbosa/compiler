package compilador;

import java.io.IOException;

public class Scanner {
	
	private ReadeFile read = new ReadeFile();
	private static char caracter = ' ';
	
	
	private int linha = 0;
	private int coluna = 0;
	
	public Token scannerToken() throws IOException {
		
		String forma_lexema = "";
			
		
		while(Character.isWhitespace(caracter)) {
			if(caracter == '\n' ) {
				coluna = 0;
				linha++;
			}
			if(caracter == '\t') {
				coluna = coluna + 4;
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
					if(caracter == ' ' || caracter == ';' || caracter == '\n' || caracter == '|') {
						coluna++;
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
			if(caracter == ' ' ) {
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
				
			}
		}
		
		// CARACTERES ESPECIAIS
		// ABRE PARENTESE
		if(caracter == '(') {
			while(caracter == '(') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.ABRE_PARENTESE_TOKEN);
		}
		// FECHA PARENTESE
		if(caracter == ')') {
			while(caracter == ')') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.FECHA_PARENTESE_TOKEN);
		}
		// ABRE CHAVE
		if(caracter == '{') {
			while(caracter == '{') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.ABRE_CHAVE_TOKEN);
		}
		// FECHA CHAVES
		if(caracter == '}') {
			while(caracter == '}') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.FECHA_CHAVE_TOKEN);
		}
		
		// PONTO E VÍRGULA
		if(caracter == ';') {
			while(caracter == ';') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.PONTO_E_VIRGULA_TOKEN);
		}
		
		// VÍRGULA
		if(caracter == ',') {
			while(caracter == ',') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			
			return new Token(forma_lexema, Dicionario.VIRGULA_TOKEN);
		}
		
		// OPERADOR ARITMÉTICO IGUAL
		
		if(caracter == '=') {
			while(caracter == '=') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_IGUAL_TOKEN);
		}
		// OPERADOR ARITMETICO SOMA
		if(caracter == '+') {
			while(caracter == '+') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_ADICAO_TOKEN);
		}
		// OPERADOR ARITMÉTICO SUBTRAÇÃO
		if(caracter == '-') {
			while(caracter == '-') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_SUBTRACAO_TOKEN);
		}
		// OPERADOR ARITMÉTICO MULTIPLICAÇÃO
		if(caracter == '*') {
			while(caracter == '*') {
				forma_lexema = forma_lexema + caracter;
				caracter = read.leituraCaracterArquivo();
			}
			return new Token(forma_lexema, Dicionario.OP_ARITMETICO_MULTIPLICACAO_TOKEN);
		}
		
		// OPERADOR RELACIONAL
		
		
		// COMENTÁRIO DE LINHA ÚNICA
		
		
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
	
	private void mensagemDeErroFloat(int linha, int coluna) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Float mal formado");
	}
	
	private void mensagemDeErroChar(int linha, int coluna) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Char mal formado");
	}
	
	private void mensagemCaracterInexistente(int linha, int colune) {
		System.out.println("ERRO na linha "+linha+", coluna "+coluna+". Caracter Inexistente");
	}

}
