/*Recursive functions that returns true if the formula is true in the model
 */

/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020 
 */
package EpistemicChecker;

import java.util.ArrayList;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.Conjunction;
import net.sf.tweety.logics.fol.syntax.Disjunction;
import net.sf.tweety.logics.fol.syntax.Equivalence;
import net.sf.tweety.logics.fol.syntax.FolAtom;
import net.sf.tweety.logics.fol.syntax.Implication;
import net.sf.tweety.logics.fol.syntax.Negation;
import net.sf.tweety.logics.ml.syntax.MlBeliefSet;

public class EpistemicReasoner {
private Predicate q = new Predicate("q");
	
	private char a;
	private RelationalFormula atom = new FolAtom(q);
	private Negation neg = new Negation(atom);
	private Conjunction con = new Conjunction();
	private Disjunction dis = new Disjunction();
	private Implication imp = new Implication(atom,atom);
	private Equivalence eq = new Equivalence(atom,atom);
	private Knowledge know = new Knowledge(atom, a);
	private PosKnowledge posKnow = new PosKnowledge(atom,a);
	private ArrayList<MlBeliefSet> accRelSetAgent;
	private MlBeliefSet currentWorld;
	private boolean bol;
	

	public boolean WorldReasoner(RelationalFormula formula,EpistemicKripkeModel str, MlBeliefSet world ) {	
		
		if(formula.getClass()==atom.getClass()) {
			if(world.contains(formula)){
				bol=true;
			}else {
				bol=false;
			}
		} else if(formula.getClass()==neg.getClass()) {
			neg = (Negation) formula;
			if(WorldReasoner(neg.getFormula(),str,world)==true){
				bol=false;
			}else {
				bol=true;
			}
		}else if(formula.getClass()==con.getClass()) {
			con = (Conjunction) formula;
			if(WorldReasoner(con.get(0),str,world)==true&&WorldReasoner(con.get(1),str,world)) {
				bol=true;
			}else {
				bol=false;
			}
		}else if(formula.getClass()==dis.getClass()) {
			dis=(Disjunction) formula;
			if(WorldReasoner(dis.get(0),str,world)==true || WorldReasoner(dis.get(1),str,world)==true ) {
				bol=true;
			}else {
				bol=false;
			}
		}else if(formula.getClass()==imp.getClass()) {
			imp=(Implication) formula;
			if(WorldReasoner(imp.getFormulas().getFirst(),str,world)==true 
					&& WorldReasoner(imp.getFormulas().getSecond(),str,world)==false) {
				bol=false;
			}else {
				bol=true;
			}
		}else if(formula.getClass()==eq.getClass()) {
			eq=(Equivalence) formula;
			Implication imp1 = new Implication(eq.getFormulas().getFirst(),eq.getFormulas().getSecond());
			Implication imp2 = new Implication(eq.getFormulas().getSecond(),eq.getFormulas().getFirst());
			if(WorldReasoner(imp1,str,world)==true 
					&& WorldReasoner(imp2,str,world)==true) {
				bol=true;
			}else {
				bol=false;
			}
		}else 
			
			if(formula.getClass()==know.getClass()) {
			know= (Knowledge) formula;
			char c = know.getAgent();
			accRelSetAgent = str.getRelations(world, c); 
			int j=0;
			int length =accRelSetAgent.size();
			bol=true;
			
			if(accRelSetAgent.isEmpty()){//Si no tiene relaciones
				
				bol=true; 
				
			}else {				
					while(j<length) {
						accRelSetAgent = str.getRelations(world, c); 
						currentWorld = accRelSetAgent.get(j);		
						bol=WorldReasoner(know.getFormula(),str,currentWorld);
						if(bol==false){
							break;
						}else {
						j++;
						}
					
				}
			}
			
		}else if(formula.getClass()==posKnow.getClass()) {
			posKnow= (PosKnowledge) formula;
			char c = posKnow.getAgent();
			accRelSetAgent = str.getRelations(world, c); 
			int i=0;
			int length = accRelSetAgent.size();
			bol=false;
			if(accRelSetAgent.isEmpty()){//Si no tiene relaciones
				bol=false; 
			}else {				
					while(i<length) {
						accRelSetAgent = str.getRelations(world,c);
						currentWorld = accRelSetAgent.get(i);		
						bol=WorldReasoner(posKnow.getFormula(),str,currentWorld);
						if(bol==true){
							break;
						}else {
						i++;
						}
					
				}
			}
			
		}
		return bol;
	}
	
	public boolean ModelReasoner(RelationalFormula formula, EpistemicKripkeModel str) {
		ArrayList<MlBeliefSet> worldSet = str.getWorlds();
		bol=true;
		for(int i = 0;i<worldSet.size();i++) {
			currentWorld=worldSet.get(i);
			if(this.WorldReasoner(formula, str, currentWorld)==true) {
				
			}else {
				bol=false;
				break;
			}
		}
		
		return bol;
	}
}
