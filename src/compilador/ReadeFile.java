package compilador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadeFile {
	private FileInputStream entrada;
	private InputStreamReader entradaFormatada ; 
	 
	
	public ReadeFile()  {
		try {
			entrada = new FileInputStream("file.txt");
			entradaFormatada = new InputStreamReader(entrada);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
	}


	public char leituraCaracterArquivo() throws IOException {	
		int c;
	
		c = entradaFormatada.read();
		
		if(c == -1) {
			return '|';
		}
		//System.out.println((char) c);
		return (char) c;
	}
	
}