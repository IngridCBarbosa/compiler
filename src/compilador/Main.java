package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		FileReader codigo = new FileReader("codigo.txt");
		BufferedReader file = new BufferedReader(codigo);
		
		while(file.ready()) {
			String linha = file.readLine();
			System.out.println(linha);
		}
		
	}

}
