package compilador;

import java.io.IOException;
import java.util.LinkedList;

public class Parser {

	
	private Scanner scanner;
	private Token nextToken;
	
	private int escopo = 0;
	private static LinkedList<Tipo> tabelaSimbolos; 
	private Tipo operando1 = null, operando2 = null;
	private int temporarioIndice = 0, labelIndice = 0;
	
	public Parser(String nomeArquivo) throws IOException {
		scanner = new Scanner(nomeArquivo);
		nextToken = scanner.scannerToken();
		tabelaSimbolos = new LinkedList<Tipo>();
	}
	  
	// VALIDA O MAIN
	public void programa() throws IOException {
		
		if(nextToken.getToken() != Dicionario.PR_INT_TOKEN) {
			mensagemErroInteiroInicioPrograma(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.PR_MAIN_TOKEN) {
			mensagemErroMainInicioPrograma(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.ABRE_PARENTESE_TOKEN) {
			mensagemAbreParentese(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		nextToken = scanner.scannerToken();
		if(nextToken.getToken() != Dicionario.FECHA_PARENTESE_TOKEN) {
			mensagemErroFechaParentese(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		nextToken = scanner.scannerToken();
		
		bloco();
		
		//System.out.println("PASSOU");
	}
	
	private void bloco() throws IOException {
		
		
		if(nextToken.getToken() != Dicionario.ABRE_CHAVE_TOKEN) {
			mensagemErroAbreChave(Scanner.getLinha(),Scanner.getColuna());
			System.exit(0);
		}
		escopo++;
		nextToken = scanner.scannerToken();
		
		while(nextToken.getToken() == Dicionario.PR_CHAR_TOKEN || nextToken.getToken() == Dicionario.PR_FLOAT_TOKEN || nextToken.getToken() == Dicionario.PR_INT_TOKEN ){
			declaracaoVariavel(nextToken.getToken().getId());
		}
		
		while(nextToken.getToken() == Dicionario.IDENTIFICADOR_TOKEN || nextToken.getToken() == Dicionario.ABRE_CHAVE_TOKEN
				|| nextToken.getToken() == Dicionario.PR_WHILE_TOKEN || nextToken.getToken() == Dicionario.PR_DO_TOKEN
				|| nextToken.getToken() == Dicionario.PR_IF_TOKEN) {
			comando();
		}
		
		if(nextToken.getToken() != Dicionario.FECHA_CHAVE_TOKEN) {
			mensagemErroFechaChave(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		
		//exibeTudo();		
		escopo--;
		removeTodasVariaveiEscopoAtual(escopo);
		nextToken = scanner.scannerToken();
	
	}
	
	private void declaracaoVariavel(int idTipo) throws IOException {
		
			Tipo tipoAux;
			
			nextToken = scanner.scannerToken();
			
			
			if(nextToken.getToken() != Dicionario.IDENTIFICADOR_TOKEN) {
				mensagemErroSemIdentificador(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
			
			
				tipoAux = new Tipo(nextToken.getTipo_Token(), prEmTipo(idTipo), escopo);
				inserirNaTabelaSimbolos(tipoAux);
				
			
			nextToken = scanner.scannerToken();
			while(nextToken.getToken() == Dicionario.VIRGULA_TOKEN) {
				nextToken = scanner.scannerToken();
				if(nextToken.getToken() != Dicionario.IDENTIFICADOR_TOKEN) {
					mensagemErroSemIdentificador(Scanner.getLinha(), Scanner.getColuna());
					System.exit(0);
				}
				
				tipoAux = new Tipo(nextToken.getTipo_Token(), prEmTipo(idTipo), escopo);
				inserirNaTabelaSimbolos(tipoAux);
				
				
				nextToken = scanner.scannerToken();
			}
		
			if(nextToken.getToken() != Dicionario.PONTO_E_VIRGULA_TOKEN) {
				mensagemErroPontoEVirgula(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
			nextToken = scanner.scannerToken();
		
		
	}
	
	private void comando() throws IOException {
		Tipo operando;
		int label = labelIndice;
		
			if(nextToken.getToken() == Dicionario.IDENTIFICADOR_TOKEN || nextToken.getToken() == Dicionario.ABRE_CHAVE_TOKEN) {
				comandoSimples();
			}
			else if(nextToken.getToken() == Dicionario.PR_WHILE_TOKEN || nextToken.getToken() == Dicionario.PR_DO_TOKEN) {
				iteracao();
			}
			else if(nextToken.getToken() == Dicionario.PR_IF_TOKEN) {
				nextToken = scanner.scannerToken();
				if(nextToken.getToken() == Dicionario.ABRE_PARENTESE_TOKEN) {
					nextToken = scanner.scannerToken();
					operando = expressaoRelacional();
					if(nextToken.getToken() == Dicionario.FECHA_PARENTESE_TOKEN) {
						nextToken = scanner.scannerToken();
						
						System.out.print("if "+operando.getLexema());
						if(operando.getTemporarioId() >= 0) {
							System.out.print(operando.getTemporarioId());
						}
						System.out.println(" == 0 goto L"+label);
						labelIndice++;
						
						 comando();
						 
						if(nextToken.getToken() == Dicionario.PR_ELSE_TOKEN) {
							nextToken = scanner.scannerToken();
							System.out.println("goto L"+labelIndice);
							System.out.println("L"+label+":");
							label = labelIndice;
							comando();
							System.out.println("L"+label+":");
						}else {
							System.out.println();
							System.out.println("L"+label+":");
						}
					}else {
						mensagemErroFechaParentese(Scanner.getLinha(), Scanner.getColuna());
						System.exit(0);
					}
				}else {
					mensagemAbreParentese(Scanner.getLinha(), Scanner.getColuna());;
					System.exit(0);
				}
			}
			
		
	}
	
	
	private void iteracao() throws IOException {
		Tipo operando;
		int label =  labelIndice;
		
		System.out.println("L"+label+":");
		
		if(nextToken.getToken() == Dicionario.PR_WHILE_TOKEN ) {
			labelIndice +=2;
			nextToken = scanner.scannerToken();
			if(nextToken.getToken() == Dicionario.ABRE_PARENTESE_TOKEN) {
				nextToken = scanner.scannerToken();
				operando = expressaoRelacional();
				if(nextToken.getToken() == Dicionario.FECHA_PARENTESE_TOKEN) {
					nextToken = scanner.scannerToken();
					
					System.out.print("if "+operando.getLexema());
					if(operando.getTemporarioId() >= 0) {
						System.out.print(operando.getTemporarioId());
					}
					System.out.println("== 0 goto L"+(label+1));
			
					comando();
					System.out.println();
					System.out.println("goto L"+label);
					System.out.println("L"+(label + 1)+":");
					
				} else {
					mensagemErroFechaParentese(Scanner.getLinha(), Scanner.getColuna());
					System.exit(0);
				}
			} else {
				mensagemAbreParentese(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
		} else if(nextToken.getToken() == Dicionario.PR_DO_TOKEN) {
			
			label = labelIndice++;
			nextToken=scanner.scannerToken();
			comando();
			if(nextToken.getToken() == Dicionario.PR_WHILE_TOKEN) {
				nextToken = scanner.scannerToken();
				if(nextToken.getToken() == Dicionario.ABRE_PARENTESE_TOKEN) {
					nextToken = scanner.scannerToken();
					 operando = expressaoRelacional();
					if(nextToken.getToken() == Dicionario.FECHA_PARENTESE_TOKEN) {
						nextToken = scanner.scannerToken();
						if(nextToken.getToken() == Dicionario.PONTO_E_VIRGULA_TOKEN) {
							nextToken = scanner.scannerToken();
							
							System.out.print("if "+operando.getLexema());
							if(operando.getTemporarioId() >= 0) {
								System.out.print(operando.getTemporarioId());
							}
							System.out.println(" != 0 goto L"+label);
							
						} else {
							mensagemErroPontoEVirgula(Scanner.getLinha(), Scanner.getLinha());
							System.exit(0);
						}
					}else {
						mensagemErroFechaParentese(Scanner.getLinha(), Scanner.getColuna());
						System.exit(0);
					}
				}else {
					mensagemAbreParentese(Scanner.getLinha(), Scanner.getColuna());
					System.exit(0);
				}
			}else {
				mensagemErroSemWhile(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
			
		}
	}
	
	private void comandoSimples() throws IOException {
		Tipo operando1a, operando2a;
		
		if(nextToken.getToken() == Dicionario.IDENTIFICADOR_TOKEN) {
			
			operando1a = buscaEmTodosOsEscopos(nextToken.getTipo_Token());
			if(operando1a == null) {
				mensagemErroVariavelNaoDeclarada(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
			else {
				//System.out.println("lado direito: "+operando1a.getLexema());
				
			}
			
			operando2a = atribuicao();
			//System.out.println("lado esquerdo: "+operando2.getLexema());
			verificaEmAtribuicao(operando1a, operando2a);
		}
		else if (nextToken.getToken() == Dicionario.ABRE_CHAVE_TOKEN) {
			bloco();
		}
	}
	
	private Tipo atribuicao() throws IOException {
		
		if(nextToken.getToken() == Dicionario.IDENTIFICADOR_TOKEN) {
			nextToken = scanner.scannerToken();
			if(nextToken.getToken() == Dicionario.OP_ARITMETICO_IGUAL_TOKEN) {
				nextToken = scanner.scannerToken();
				operando1 = expressaoAritmetica();
				if(nextToken.getToken() == Dicionario.PONTO_E_VIRGULA_TOKEN) {
					nextToken = scanner.scannerToken();
				} else {
					mensagemErroPontoEVirgula(Scanner.getLinha(), Scanner.getColuna());
					System.exit(0);
				}
			} else {
				mensagemErroOperadorAtirmeticoIgual(Scanner.getLinha(), Scanner.getColuna());
				
			}
		}
		return operando1;
	}
	
	private Tipo expressaoAritmetica() throws IOException {
		Tipo operando1a;
		
		
		operando1a = termo();
	
		
		operando2 = expressaoLinha(operando1a);
		
		return operando1a;
	}
	
	private Tipo termo() throws IOException {
		
		operando1 = fator();
		operando2 = termoLinha(operando1);
		
		
		
		return operando1;
		
		
	}
	
	private Tipo expressaoLinha(Tipo operando1) throws IOException {
		Tipo operando1a = operando1;
		String sinal = null;
		
		if(nextToken.getToken() == Dicionario.OP_ARITMETICO_ADICAO_TOKEN || nextToken.getToken() == Dicionario.OP_ARITMETICO_SUBTRACAO_TOKEN) {
			
			sinal = nextToken.getTipo_Token();
			
			nextToken = scanner.scannerToken();
			
			operando2 = expressaoAritmetica();
			
			verificaOperadores(operando1a, operando2, false);
			operando1a = codigoIntermediario(operando1a, operando2, sinal);
			
		}
		
		
		return operando1a; 
		
	}
	
	private Tipo termoLinha(Tipo operador1) throws IOException {
		Tipo operando1 = operador1, operando2;
		String operacao;
		
		do {
			if(nextToken.getToken() == Dicionario.OP_ARITMETICO_MULTIPLICACAO_TOKEN) {
				operacao = nextToken.getTipo_Token();
				
				
				nextToken = scanner.scannerToken();
				
				operando1 = fator();
				operando2 = termoLinha(operando1);
				
				verificaOperadores(operador1, operando2, false);
				operando1 = codigoIntermediario(operador1, operando2, operacao);
			}
			else if(nextToken.getToken() == Dicionario.OP_ARITMETICO_DIVISAO_TOKEN) {
				operacao = nextToken.getTipo_Token();
				
				nextToken = scanner.scannerToken();
				
				operando1 = fator();
				operando2 = termoLinha(operando1);
				
				verificaOperadores(operador1, operando2, true);
				operando1 = codigoIntermediario(operando1, operando2, operacao);
				
			}
		}while(nextToken.getToken() == Dicionario.OP_ARITMETICO_MULTIPLICACAO_TOKEN || nextToken.getToken() == Dicionario.OP_ARITMETICO_DIVISAO_TOKEN);
		
		return operando1;
		  
	}
	
	private Tipo fator() throws IOException {
		
		Tipo operando1 = null ;
		if(nextToken.getToken() == Dicionario.IDENTIFICADOR_TOKEN) {
			operando1 = buscaEmTodosOsEscopos(nextToken.getTipo_Token());
			if(operando1 == null) {
				mensagemErroVariavelNaoDeclarada(Scanner.getLinha(), Scanner.getColuna());
				System.exit(0);
			}
			else {
				//System.out.println("variavel buscada "+operando1.getLexema());
				 
			}
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.TIPO_INT_TOKEN) {
			
			operando1 = new Tipo (nextToken.getTipo_Token(), nextToken.getToken().getId(),escopo);
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.TIPO_CHAR_TOKEN) {
			
			operando1 = new Tipo (nextToken.getTipo_Token(), nextToken.getToken().getId(),escopo);
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.TIPO_FLOAT_TOKEN) {
			
			operando1 = new Tipo (nextToken.getTipo_Token(), nextToken.getToken().getId(),escopo);
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.ABRE_PARENTESE_TOKEN) {
			nextToken = scanner.scannerToken();
			
			/*operador 1 ou 2???*/ //termoLinha();
			operando1 = expressaoAritmetica();
			
			if(nextToken.getToken() == Dicionario.FECHA_PARENTESE_TOKEN) {
				nextToken = scanner.scannerToken();
			} else {
				mensagemErroFechaParentese(Scanner.getLinha(), Scanner.getLinha());
				System.exit(0);
			}
			
		} else {
			mensagemErroFator(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		
		return operando1;
		
	}
	
	private Tipo expressaoRelacional() throws IOException {
		Tipo operando1a,operando2a;
		String operacao;
		operando1a = expressaoAritmetica();
		operacao = nextToken.getTipo_Token();
		
		operadorRelacional();
		
		operando2a = expressaoAritmetica();
		
		verificaOperadores(operando1a, operando2a, false);
		operando1a = codigoIntermediario(operando1a, operando2a, operacao);
		
		return operando1a;
	}
	
	private void inserirNaTabelaSimbolos(Tipo novoTipo) {
		if(buscaVariavelEscopoAtual(novoTipo.getLexema()) == null) {
			
			tabelaSimbolos.addFirst(novoTipo);
		}else {
			mensagemErroVariavelDeclaradaEscopoArual(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
	}
	
	private void removeTodasVariaveiEscopoAtual(int escopoAtual) {
		
		for(Tipo variavel : new LinkedList<Tipo>(tabelaSimbolos)) {
			if(escopoAtual < variavel.getEscopo()) {
				//System.out.println("IF");
				//System.out.println("REmoveu=> "+variavel);
				tabelaSimbolos.remove(variavel);
			}
		}
	}
	
	private void exibeTudo() {
		for(Tipo t :tabelaSimbolos) {
			System.out.println(t.toString());
		}
	}
	
	private Tipo buscaEmTodosOsEscopos(String lexema) {
		Tipo tipo = null;
		
		for(int i = 0; i < tabelaSimbolos.size();i++) {
			if(lexema.equals(tabelaSimbolos.get(i).getLexema())) {
				tipo = new Tipo(tabelaSimbolos.get(i).getLexema(), tabelaSimbolos.get(i).getId_Tipo(), tabelaSimbolos.get(i).getEscopo());
				break;
			}
		}
		return tipo;
	}
	
	private Tipo buscaVariavelEscopoAtual(String lexema) {
		Tipo tipo = null;
		for (Tipo t : tabelaSimbolos) {
			if(lexema.equals(t.getLexema()) && escopo == t.getEscopo()) {
				tipo = new Tipo(t.getLexema(), t.getId_Tipo(), t.getEscopo());
				break;
			}
		}
		return tipo;
	}
	
	// VERIFICA O LADO DIREITO DA OPERA��O
	private void verificaOperadores(Tipo operando1, Tipo operando2, boolean divisao) {
		
		//System.out.println("operando1: "+operando1.getId_Tipo()+" operando1: "+operando1.getLexema()+" operando2: "+operando2.getId_Tipo()+" operando2: "+operando2.getLexema());
		if(operando1.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_FLOAT_TOKEN.getId()) {
			operando1.setId_Tipo(Dicionario.TIPO_FLOAT_TOKEN.getId());
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_FLOAT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId()) {
			operando2.setId_Tipo(Dicionario.TIPO_FLOAT_TOKEN.getId());
			
			System.out.print("T"+temporarioIndice+" = (float) "+operando2.getLexema());
			if(operando2.getTemporarioId() >= 0) {
				System.out.print(operando2.getTemporarioId());
			}
			System.out.println();
			operando2.setLexema("T");
			operando2.setTemporarioId(temporarioIndice);
			temporarioIndice++;
			
		}
		if(divisao == true && operando1.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId() && operando2.getId_Tipo()== Dicionario.TIPO_INT_TOKEN.getId()) {
			 operando1.setId_Tipo(Dicionario.TIPO_FLOAT_TOKEN.getId());
			 
			 System.out.print("T"+temporarioIndice+" = (float) "+operando1.getLexema());
			 if(operando1.getTemporarioId() >= 0) {
				 System.out.println(operando1.getTemporarioId());
			 }
			 operando1.setLexema("T");
			 operando1.setTemporarioId(temporarioIndice);
			 temporarioIndice++;
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_CHAR_TOKEN.getId() && operando2.getId_Tipo() != Dicionario.TIPO_CHAR_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_CHAR_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_FLOAT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_CHAR_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		
	}
	
	// VERIFICA O LADO DIREITO COM O ESQUERDO
	private void verificaEmAtribuicao(Tipo operando1, Tipo operando2) {
		if(operando1.getId_Tipo() == Dicionario.TIPO_FLOAT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId()) {
			operando2.setId_Tipo(Dicionario.TIPO_FLOAT_TOKEN.getId());
			
			System.out.print("T"+temporarioIndice+" = (float) "+operando2.getLexema());
			if(operando2.getTemporarioId() >= 0) {
				System.out.println(operando2.getTemporarioId());
			}
			System.out.println();
			operando2.setLexema("T");
			operando2.setTemporarioId(temporarioIndice);
			temporarioIndice++;
			
		}
		System.out.print(operando1.getLexema()+" = "+operando2.getLexema());
		if(operando2.getTemporarioId() >= 0) {
			System.out.println(operando2.getTemporarioId());
		}
		
		if(operando1.getId_Tipo() == Dicionario.TIPO_INT_TOKEN.getId() && operando2.getId_Tipo() != Dicionario.TIPO_INT_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_CHAR_TOKEN.getId() && operando2.getId_Tipo() != Dicionario.TIPO_CHAR_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
		if(operando1.getId_Tipo() == Dicionario.TIPO_FLOAT_TOKEN.getId() && operando2.getId_Tipo() == Dicionario.TIPO_CHAR_TOKEN.getId()) {
			mensagemErroVariavelIncompativel(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
	}
	
	
	private void operadorRelacional() throws IOException {
		if(nextToken.getToken() == Dicionario.OP_RELACIONAL_DIFERENTE_TOKEN) {
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.OP_RELACIONAL_IGUAL_IGUAL) {
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.OP_RELACIONAL_MAIOR_IGUAL_TOKEN) {
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.OP_RELACIONAL_MAIOR_TOKEN) {
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.OP_RELACIONAL_MENOR_IGUAL_TOKEN) {
			nextToken = scanner.scannerToken();
		}
		else if(nextToken.getToken() == Dicionario.OP_RELACIONAL_MENOR_TOKEN) {
			nextToken = scanner.scannerToken();
		}
		else {
			mensagemErroOperadoresRelacionario(Scanner.getLinha(), Scanner.getColuna());
			System.exit(0);
		}
	}
	
	public int  prEmTipo(int idTipo) {
		int tipo = 0;
		// INT
		if(idTipo == 11) {
			 tipo = 1;
		}
		if(idTipo == 12) {
			 tipo = 2;
		}
		if(idTipo == 13) {
			tipo = 3;
		}
		return tipo;
	}
	
	public Tipo codigoIntermediario(Tipo operando1, Tipo operando2, String operacao) {
		
		System.out.print("T"+temporarioIndice+" = "+operando1.getLexema());
		if(operando1.getTemporarioId() >= 0) {
			System.out.print(operando1.getTemporarioId());
		}
		System.out.print(operacao);
		System.out.print(operando2.getLexema());
		if(operando2.getTemporarioId() >= 0) {
			System.out.print(operando2.getTemporarioId());
		}
		System.out.println();
		operando1.setLexema("T");
		operando1.setTemporarioId(temporarioIndice);
		temporarioIndice++;
		
		return operando1;
	}
	
	
	
	
	/// ---------------------------ERROS----------------------------////
	private void mensagemErroInteiroInicioPrograma(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+" Iniciado programa sem INT");
	}
	private void mensagemErroMainInicioPrograma(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Iniciado programa sem MAIN");
	}
	private void mensagemAbreParentese(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+" coluna "+coluna+". Sem o (");
	}
	private void mensagemErroFechaParentese(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+" coluna "+coluna+". Sem o )");
	}
	private void mensagemErroAbreChave(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+" coluna "+coluna+". Sem o {");
	}
	private void mensagemErroFechaChave(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+" coluna "+coluna+". Sem o }");
	}
	private void mensagemErroSemIdentificador(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem o identidicador");
	}
	private void mensagemErroPontoEVirgula(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem o ;");
	}
	private void mensagemErroSemWhile(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem WHILE");
	}
	private void mensagemErroFator(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem ID,TIP_INT,TIPO_FLOAT ou TIPO_CHAR");
	}
	private void mensagemErroOperadorAtirmeticoIgual(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem = na atribuição");
	}
	private void mensagemErroOperadoresRelacionario(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Sem operadores relacionais !=,==, >=, <=, >, <");
	}
	
	
	// ---------------------------------ERRO SEMANTICO--------------------------------
	private void mensagemErroVariavelNaoDeclarada(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Vari�vel n�o declarada");
	}
	private void mensagemErroVariavelDeclaradaEscopoArual(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Vari�vel j� declarada no escopo");
	}
	private void mensagemErroVariavelIncompativel(int linha, int coluna) {
		System.out.println("Erro na linha "+linha+", coluna "+coluna+". Tipo de vari�veis incompat�vel.");
	}
}
