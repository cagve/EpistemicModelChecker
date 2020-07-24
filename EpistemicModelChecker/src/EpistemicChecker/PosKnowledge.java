/*Dual epistemic operator
 */

/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020 
 */
package EpistemicChecker;

import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.ml.syntax.MlFormula;

public class PosKnowledge extends MlFormula{
	MlFormula formula;
	char c;
	
	public PosKnowledge(RelationalFormula formula, char c) {
		super(formula);
		this.c=c;
		// TODO Auto-generated constructor stub
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
		return "M"+c+"(" + this.getFormula() + ")";
	}
	
	public char getAgent() {
		return c;
	}
	
}
