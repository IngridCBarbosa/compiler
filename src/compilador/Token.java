package compilador;

public class Token {
	String lexema;
	Dicionario token;
	
	public Token(String lexema,Dicionario token) {
		this.lexema = lexema;
		this.token = token;
	}
	
	public Dicionario getToken() {
		return token;
	}
	
	public String getTipo_Token() {
		return lexema;
	}
	
	public String toString() {
		return ""+lexema+" token: "+token;
	}
}
