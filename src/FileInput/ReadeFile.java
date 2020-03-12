package FileInput;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadeFile {
	private FileInputStream entrada;
	private InputStreamReader entradaFormatada;
	
	public void leituraCaracterArquivo() throws IOException {
		entrada = new FileInputStream("file.txt");
		entradaFormatada= new InputStreamReader(entrada);
		
		int c = entradaFormatada.read();
		while(c != -1) {		
			System.out.println((char)c);
			c =  entradaFormatada.read();
		}
	}
}
