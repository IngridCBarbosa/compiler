package compilador;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
	
	private List<String> bufferToke = new ArrayList<String>();
	 
	public List<String> proxToken(String c) {
		
		if(c.matches("[0-9]*")) {
			bufferToke.add(c);
			
		}
		
		
		return bufferToke;
	}
	
	
	public boolean verificaInteiro(List<String>token) {
		for(String c: token) {
			if(!c.matches("[0-9]*")) {
				return false;
			}
		}
		return false;
	}
	
	
	private boolean verificaFloat() {
		return false;
	}
	
	
	public void methodScanner(String caracter) {
		List<String> token = proxToken(caracter);
		verificaInteiro(token);
	}
}
