package compilador;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException  {
		
		Scanner scanner = new Scanner();
		
		Token l = scanner.scannerToken(); 
		
		while(l.getToken() != Dicionario.FIM_DE_ARQUIVO_TOKEN) {
			System.out.println(l);
			l = scanner.scannerToken();
			
			
		}
	}

}
