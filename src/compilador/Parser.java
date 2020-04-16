package compilador;

import java.io.IOException;

public class Parser {

	
	private Scanner scanner;
	private Token nextToken;
	
	public Parser(String nomeArquivo) {
		scanner = new Scanner(nomeArquivo);
	}
	
	// VALIDA O MAIN
	public void programa() throws IOException {
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.TIPO_INT_TOKEN) {
			// ERRO
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.PR_MAIN_TOKEN) {
			// ERRO
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.ABRE_PARENTESE_TOKEN) {
			// ERRO
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.FECHA_PARENTESE_TOKEN) {
			//ERRO
		}
		// CHAMA O BLOCO
		nextToken = scanner.scannerToken();
		bloco();
		
	}
	
	private void bloco() throws IOException {
		
		
		if(nextToken.getToken() != Dicionario.ABRE_CHAVE_TOKEN) {
			
		}
		
		nextToken = scanner.scannerToken();
		declaracaoVariavel();
		
		
		if(nextToken.getToken() != Dicionario.FECHA_CHAVE_TOKEN) {
			System.out.println("ERRO }");
			System.exit(0);
		}
		nextToken = scanner.scannerToken();
	
	}
	
	private void declaracaoVariavel() throws IOException {
		
		while(nextToken.getToken() == Dicionario.PR_CHAR_TOKEN || nextToken.getToken() == Dicionario.PR_FLOAT_TOKEN || nextToken.getToken() == Dicionario.PR_INT_TOKEN ){
			
			tipo();
		
			if(nextToken.getToken() != Dicionario.IDENTIFICADOR_TOKEN) {
				// ERRO
				System.out.println("ERRO sem identificador");
				System.exit(0);
			}
		
			nextToken = scanner.scannerToken();
			while(nextToken.getToken() == Dicionario.VIRGULA_TOKEN) {
				nextToken = scanner.scannerToken();
				if(nextToken.getToken() != Dicionario.IDENTIFICADOR_TOKEN) {
					// ERRO
					System.out.println("ERRO sem identificador");
					System.exit(0);
				}
				nextToken = scanner.scannerToken();
			}
		
			if(nextToken.getToken() != Dicionario.PONTO_E_VIRGULA_TOKEN) {
				// ERRO
				System.out.println("ERRO sem ;");
				System.exit(0);
			}
			nextToken = scanner.scannerToken();
			
		}
	}
	
	
	private void tipo() throws IOException {
		//nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.PR_INT_TOKEN) {
			// ERRO
		}
		if(nextToken.getToken() != Dicionario.PR_FLOAT_TOKEN) {
			// ERRO
		}
		if(nextToken.getToken() != Dicionario.PR_CHAR_TOKEN) {
			// ERRO
		}
		nextToken = scanner.scannerToken();
	}
	
	private void comando() {
		
	}
	
}
