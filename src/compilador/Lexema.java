package compilador;

public class Lexema {
	String string;
	Token token;
	
	public Lexema(String tipo_token,Token token) {
		this.string = tipo_token;
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	public String getTipo_Token() {
		return string;
	}
	
	public String toString() {
		return ""+string+" token: "+token;
	}
}
