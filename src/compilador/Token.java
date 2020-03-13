package compilador;

public enum Token {
	
	TIPO_INT(1),
	TIPO_FLOAT(2),
	TIPO_CHAR(3),
	IDENTIFICADOR(4),
	PONTO_E_VIRGULA(5),
	PR_WHILE(5),
	PR_IF(6),
	PR_FOR(7),
	PR_ELSE(8);
	
	private int id;
	
	Token(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
