package compilador;

import java.io.IOException;

import FileInput.ReadeFile;

public class Main {

	public static void main(String[] args) throws IOException  {
		
		ReadeFile arquivo = new ReadeFile();
		arquivo.leituraCaracterArquivo();
	}

}
