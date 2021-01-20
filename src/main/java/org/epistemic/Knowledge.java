package org.epistemic;

import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.ml.syntax.MlFormula;

/**
 *  Clase que construye una fórmula de tipo epistémico. Extiende MLFormula, una clase de la librería tweety. (https://tweetyproject.org/)
 * @see "https://tweetyproject.org/api/1.16/net/sf/tweety/logics/ml/syntax/MlFormula.html"
 * @version 3.01
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 */
public class Knowledge extends MlFormula {
    MlFormula formula;
    char c;
    String cad;

    /**
     * Constructor
     * @param formula fórmula
     * @param c agente
     */
    public Knowledge(RelationalFormula formula, char c) {
        super(formula);
        this.c=c;
        // TODO Auto-generated constructor stub
    }

    /**
     * Devuelve el agente de la fórmula
     * @return c
     */
    public char getAgent() {
        return c;
    }

    @Override
    public FolFormula toNnf() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RelationalFormula collapseAssociativeFormulas() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDnf() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public FolFormula substitute(Term<?> v, Term<?> t) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FolFormula clone() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return "K"+c+"(" + this.getFormula() + ")";
    }

}
