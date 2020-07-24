package EpistemicChecker;

import java.io.IOException;
import java.util.Stack;

import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.FolAtom;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.fol.syntax.Negation;

public class Traductor {
	
	public Traductor() {
		
	}
	
	
	public String convertMono(String cad) {
		String formulaTraducida = cad;
		formulaTraducida = formulaTraducida.replace("\\ka","*");
		formulaTraducida = formulaTraducida.replace("\\kb","@");
		formulaTraducida = formulaTraducida.replace("\\kc","·");
		formulaTraducida = formulaTraducida.replace("\\kd","$");
		//formulaTraducida = formulaTraducida.replace("\\lnot","!");
		return formulaTraducida;
	}
	
	public String convertNormal(String cad) {
		String formulaTraducida = cad;
		formulaTraducida = formulaTraducida.replace("&&","∧");
		formulaTraducida = formulaTraducida.replace("=>","→");
		formulaTraducida = formulaTraducida.replace("<=>","<->");
		formulaTraducida = formulaTraducida.replace("||","∨");
		formulaTraducida = formulaTraducida.replace("!","¬");
		formulaTraducida = formulaTraducida.replace(" ", "");
		return formulaTraducida;
	}
	
	public String convertEMC(String cad) {
		String formulaTraducida = cad;
		formulaTraducida = formulaTraducida.replace("\\land","&&");
		formulaTraducida = formulaTraducida.replace("\\to","=>");
		formulaTraducida = formulaTraducida.replace("\\leftrightarrow","<=>");
		formulaTraducida = formulaTraducida.replace("\\lor","||");
		formulaTraducida = formulaTraducida.replace("\\lnot","!");
		formulaTraducida = formulaTraducida.replace("*","Ka");
		formulaTraducida = formulaTraducida.replace("@","Kb");
		formulaTraducida = formulaTraducida.replace("·","Kc");
		formulaTraducida = formulaTraducida.replace("$","Kd");
		formulaTraducida = formulaTraducida.replace(" ", "");
		return formulaTraducida;
	}
	
	public String emptyStack(Stack<String> pila){
		int size = pila.size();
		String cad = "";
		for (int w = 0; w < size; w++) {
		   cad=cad+pila.pop();
		}	
		return cad;
		}
	
	public String translatePar(String cad){
		char currentChar;
		char nextChar=' ';
		int i=0;		
		String salida="";
		Stack<String> s= new Stack<String>();
		Traductor t = new Traductor();
		cad=cad.replace(" ", "");
		cad=t.convertMono(cad);
		
		while(i<cad.length()) {
			currentChar=cad.charAt(i);
			salida=salida+String.valueOf(currentChar);
			if(i<cad.length()-1) {
				nextChar=cad.charAt(i+1);
			}
			if((currentChar=='*'||currentChar=='@'||currentChar=='·'||currentChar=='$') && nextChar!='(') {
				salida=salida+("(");
				s.push(")");
			}
			if(currentChar>='p'&&currentChar<='z') {
				salida=salida+(t.emptyStack(s));
			}
			
			i++;			
		}	
		System.out.println();
		salida=t.convertEMC(salida);
		return salida;
	}


	public static void main(String[]args) throws ParserException, IOException {
	  	Traductor t= new Traductor();
    	String form=t.translatePar("\\lnot  (p \\land q)");
    	String cad="";
        Run run = new Run();         
        EpistemicParser epistemicParser= new EpistemicParser();    	
        FolSignature sig = new FolSignature();
		sig.add(new Predicate("p",0));
		sig.add(new Predicate("q",0));
		sig.add(new Predicate("r",0));
		sig.add(new Predicate("s",0));
		sig.add(new Predicate("t",0));
		sig.add(new Predicate("u",0));
		sig.add(new Predicate("v",0));
		sig.add(new Predicate("w",0));
		sig.add(new Predicate("x",0));
		sig.add(new Predicate("y",0));
		sig.add(new Predicate("z",0));
		epistemicParser.setSignature(sig);
	    RelationalFormula formula =  epistemicParser.parseFormula(form);
	    Negation neg = new Negation(formula);
	    if(formula.getClass()==neg.getClass()) {
	    	cad="!("+formula.getFormula()+")";
	    }
		System.out.println(cad);
		System.out.println("Par " + t.translatePar(form));
		
		
		
	}




}





