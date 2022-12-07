package org.epistemic;

import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 *  Clase encargada de controlar todo lo relativo a la fórmula
 * @version 3.01
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 14/07/2020
 */
public class FormulaManager {
    /**
     * Constructor
     */
    public FormulaManager() {
    }

    private int lg;
    private Predicate q = new Predicate("q");
    char a;
    private  RelationalFormula atom = new FolAtom(q);
    private Negation neg = new Negation(atom);
    private Conjunction con = new Conjunction();
    private Disjunction dis = new Disjunction();
    private Implication imp = new Implication(atom,atom);
    private Equivalence eq = new Equivalence(atom,atom);
    private Knowledge know = new Knowledge(atom, a);
    private PosKnowledge posKnow = new PosKnowledge(atom,a);
    private ArrayList<RelationalFormula> list = new ArrayList<RelationalFormula>();

    /**
     * Elimina los elementos duplicados de una lista
     * @param list lista donde eliminar los elementos duplicados
     * @param <T>
     * @return newList lista con los elementos duplicados eliminados
     */
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list){
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    public RelationalFormula createFormulaFromString(String formula) throws ParserException, IOException{
        FolSignature sig = new FolSignature();
        EpistemicParser epistemicParser = new EpistemicParser();
        sig.add(new Predicate("p", 0));
        sig.add(new Predicate("q", 0));
        sig.add(new Predicate("r", 0));
        sig.add(new Predicate("s", 0));
        sig.add(new Predicate("t", 0));
        sig.add(new Predicate("u", 0));
        sig.add(new Predicate("v", 0));
        sig.add(new Predicate("w", 0));
        sig.add(new Predicate("x", 0));
        sig.add(new Predicate("y", 0));
        sig.add(new Predicate("z", 0));
        epistemicParser.setSignature(sig);

        Traductor t = new Traductor();
        formula = t.convertEMC(formula);
        RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);
        return formulaFinal;
    }
    /**
     *  Se encarga de obtener las subfórmulas de una fórmula dada
     * @param formula fórmula a partir de la cual extraer las subfórmulas
     * @return list lista de las subfórmulas
     */
    public ArrayList<RelationalFormula> getSubFormula(RelationalFormula formula) {
        if(formula.getClass()==atom.getClass()) {
            list.add(formula);
        }else if(formula.getClass()==neg.getClass()) {
            neg=(Negation) formula;
            list.add(neg);
            list.addAll(getSubFormula(neg.getFormula()));
        }else if(formula.getClass()==con.getClass()) {
            con=(Conjunction) formula;
            list.add(con);
            list.addAll(getSubFormula(con.getFormulas().get(0)));
            list.addAll(getSubFormula(con.getFormulas().get(1)));
        }else if(formula.getClass()==dis.getClass()) {
            dis=(Disjunction) formula;
            list.add(dis);
            list.addAll(getSubFormula(dis.getFormulas().get(0)));
            list.addAll(getSubFormula(dis.getFormulas().get(1)));
        }else if(formula.getClass()==imp.getClass()) {
            imp=(Implication) formula;
            list.add(imp);
            list.addAll(getSubFormula(imp.getFormulas().getFirst()));
            list.addAll(getSubFormula(imp.getFormulas().getSecond()));
        }else if(formula.getClass()==eq.getClass()) {
            eq=(Equivalence)formula;
            list.add(eq);
            list.addAll(getSubFormula(eq.getFormulas().getFirst()));
            list.addAll(getSubFormula(eq.getFormulas().getSecond()));
        }else if(formula.getClass()==know.getClass()) {
            know=(Knowledge) formula;
            list.add(know);
            list.addAll(getSubFormula(know.getFormula()));
        }else if(formula.getClass()==posKnow.getClass()) {
            posKnow=(PosKnowledge) formula;
            list.add(posKnow);
            list.addAll(getSubFormula(posKnow.getFormula()));
        }

        list= FormulaManager.removeDuplicates(list);
        return list;
    }

    /**
     *  Devuelve la longitud de una fórmula dada
     * @param formula formula a analizar
     * @return lg. Longitud de la fórmula
     */
    public int getLg(RelationalFormula formula) {
        char newPos;
        lg=0;
        for(int i=0;i<formula.toString().length();i++) {
            char pos=formula.toString().charAt(i);
            if(pos>='p'&&pos<='z') {
                lg++;
            }else if(pos=='!') {
                lg++;
            }else if(pos=='&') {
                newPos=formula.toString().charAt(i-1);
                if(newPos=='&') {
                    lg++;
                }
            }else if(pos=='|') {
                newPos=formula.toString().charAt(i-1);
                if(newPos=='|') {
                    lg++;
                }
            }else if(pos=='>') {
                newPos=formula.toString().charAt(i-2);
                if(newPos=='<') {
                    lg++;
                }else {
                    lg++;
                }
            }else if(pos=='a'||pos=='b'||pos=='c'||pos=='d') {
                newPos=formula.toString().charAt(i-1);
                if(newPos=='K') {
                    lg++;
                }else if(newPos=='M') {
                    lg++;
                }
            }
        }
        return lg;
    }

    /**
     * Devuelve las formulas de longitud i
     * @param lg  Longitud máxima
     * @param list Lista de subfórmulas
     * @return newList. Lista ordenada
     */
    public ArrayList<RelationalFormula> getFormulaLg(int lg,ArrayList<RelationalFormula> list){
        FormulaManager sub = new FormulaManager();
        ArrayList<RelationalFormula> newList= new ArrayList<RelationalFormula>();

        for(int i=0;i<list.size();i++) {
            if(sub.getLg(list.get(i))==lg) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    /**
     *  Obtiene la longitud máxima de un conjunto de fórmulas
     * @param list lista a analizar
     * @return max. Número máximo
     */
    public int getMaxLg(ArrayList<RelationalFormula> list){
        FormulaManager sub = new FormulaManager();
        int max=0;
        for(int i=0;i<list.size();i++) {
            if(sub.getLg(list.get(i))>max) {
                max=sub.getLg(list.get(i));
            }
        }
        return max;
    }

   // public static void main(String[]args) throws ParserException, IOException {
   //     FormulaManager sub = new FormulaManager();
   //     EpistemicParser epistemicParser= new EpistemicParser();
   //     ArrayList<RelationalFormula> list = new ArrayList<RelationalFormula>();

   //     //INTRODUCIR ALFABETO EN XML
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
   //     RelationalFormula formulaFinal = epistemicParser.parseFormula("p => q");

   //     Negation neg = new Negation(formulaFinal);
   //     list=sub.getSubFormula(neg);

   //     list=FormulaManager.removeDuplicates(list);
   //     System.out.println(list);

   //     System.out.println(sub.getLg(formulaFinal));
   // }

}
