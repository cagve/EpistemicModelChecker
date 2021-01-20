package org.epistemic;

import java.util.Stack;

/**
 * Se encarga de traducir las fórmulas una vez introducidas por el usuario. Permite que la entrada sea tanto en formato informático como en formáto latex.
 * @version 3.01
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 */
public class Traductor {

    /**
     * Constructor
     */
    public Traductor() {
    }

    /**
     * Convierte la entrada en formato latex por una sintaxis intempretable por el programa
     * @param cad formula a traducir
     * @return formula traducida
     */
    public String convertMono(String cad) {
        String formulaTraducida = cad;
        formulaTraducida = formulaTraducida.replace("\\ma","*");
        formulaTraducida = formulaTraducida.replace("\\mb","@");
        formulaTraducida = formulaTraducida.replace("\\mc","·");
        formulaTraducida = formulaTraducida.replace("\\md","$");
        //formulaTraducida = formulaTraducida.replace("\\lnot","!");
        return formulaTraducida;
    }

    /**
     * Convierte la fórmula generada por el programa en la notación común
     * @param cad fórmula a traducir
     * @return fórmula traducida
     */
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

    /**
     * Convierte la fórmula introducida en latex por una fórmula interpretable por la libreria tweety
     * @param cad fórmula a traducir
     * @return fórmula traducida
     */
    public String convertEMC(String cad) {
        String formulaTraducida = cad;
        formulaTraducida = formulaTraducida.replace("\\land","&&");
        formulaTraducida = formulaTraducida.replace("\\to","=>");
        formulaTraducida = formulaTraducida.replace("\\eq","<=>");
        formulaTraducida = formulaTraducida.replace("\\lor","||");
        formulaTraducida = formulaTraducida.replace("\\lnot","!");
        formulaTraducida = formulaTraducida.replace("*","Ka");
        formulaTraducida = formulaTraducida.replace("@","Kb");
        formulaTraducida = formulaTraducida.replace("·","Kc");
        formulaTraducida = formulaTraducida.replace("$","Kd");
        formulaTraducida = formulaTraducida.replace(" ", "");
        return formulaTraducida;
    }

    /**
     * Vaciar pilas
     * @param pila Pila a vaciar
     * @return Devuelve la fórmula con los paréntesis introducidos
     * @deprecated
     */
    public String emptyStack(Stack<String> pila){
        int size = pila.size();
        String cad = "";
        for (int w = 0; w < size; w++) {
            cad=cad+pila.pop();
        }
        return cad;
    }

    /**
     * Traduce los pares ordenados
     * @param cad par a traducir
     * @return salida, par traducido
     */
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

// public static void main(String[]args) throws ParserException, IOException {
   //     Traductor t= new Traductor();
   //     String form=t.translatePar("\\lnot  (p \\land q)");
   //     String cad="";
   //     Run run = new Run();
   //     EpistemicParser epistemicParser= new EpistemicParser();
   //     FolSignature sig = new FolSignature();
   //     sig.add(new Predicate("p",0));
   //     sig.add(new Predicate("q",0));
   //     sig.add(new Predicate("r",0));
   //     sig.add(new Predicate("s",0));
   //     sig.add(new Predicate("t",0));
   //     sig.add(new Predicate("u",0));
   //     sig.add(new Predicate("v",0));
   //     sig.add(new Predicate("w",0));
   //     sig.add(new Predicate("x",0));
   //     sig.add(new Predicate("y",0));
   //     sig.add(new Predicate("z",0));
   //     epistemicParser.setSignature(sig);
   //     RelationalFormula formula =  epistemicParser.parseFormula(form);
   //     Negation neg = new Negation(formula);
   //     if(formula.getClass()==neg.getClass()) {
   //         cad="!("+formula.getFormula()+")";
   //     }
   //     System.out.println(cad);
   //     System.out.println("Par " + t.translatePar(form));



   // }

}





