package compilador;

public enum Token {
	
	TIPO_INT_TOKEN(1),
	TIPO_FLOAT_TOKEN(2),
	TIPO_CHAR_TOKEN(3),
	
	IDENTIFICADOR_TOKEN(4),
	
	
	PR_WHILE_TOKEN(5),
	PR_IF_TOKEN(6),
	PR_FOR_TOKEN(7),
	PR_ELSE_TOKEN(8),
	PR_MAIN_TOKEN(9),
	PR_DO_TOKEN(10),
	PR_INT_TOKEN(11),
	PR_FLOAT_TOKEN(12),
	PR_CHAR_TOKEN(13),
	
	FIM_DE_ARQUIVO_TOKEN(10);
	
	private int id;
	
	Token(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	
}
