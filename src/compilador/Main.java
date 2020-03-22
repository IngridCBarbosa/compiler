package compilador;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException  {
		
		Scanner scanner = new Scanner();
		
		Lexema l = scanner.scannerToken(); 
		
		while(l.getToken() != Token.FIM_DE_ARQUIVO_TOKEN) {
			
			l = scanner.scannerToken();
		}
	}

}
