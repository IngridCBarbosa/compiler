package compilador;

public class Tipo {
	
	String lexema;
	int escopo;
	int idTipo;
	int temporarioId;
	
	public Tipo(String lexema, int idTipo, int escopo) {
		this.lexema = lexema;
		this.idTipo = idTipo;
		this.escopo = escopo;
		temporarioId = -1;
	}
	
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public int getEscopo() {
		return escopo;
	}
	public void setEscopo(int escopo) {
		this.escopo = escopo;
	}
	public int getId_Tipo() {
		return idTipo;
	}
	public void setId_Tipo(int id_Tipo) {
		this.idTipo = id_Tipo;
	}
	
	public void setTemporarioId(int temporarioId) {
		this.temporarioId = temporarioId;
	}
	public int getTemporarioId() {
		return temporarioId;
	}
	
	@Override
	public String toString() {
		return"Lexema:"+lexema+" ID_Tipo:"+idTipo+" Escopo:"+escopo;
	}

}
