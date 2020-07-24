/* Main class that executes the epistemic model reasoner
 */

/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020 
 */

package EpistemicChecker;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import net.sf.tweety.commons.ParserException;
import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.ml.syntax.MlBeliefSet;

public class Run {
	
	public String runModelChecker (String formula,String dir) throws ParserException, IOException, ParserConfigurationException, TransformerException {
		XMLManager manager = new XMLManager(dir);
		manager.writeXML();
		
		ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> accRelSet = new ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>>();
		ArrayList<MlBeliefSet> worldSet = new ArrayList<MlBeliefSet>();
		ArrayList<Character> agentSet = new ArrayList<Character>();
		
		worldSet=manager.getWorldSet();
		agentSet=manager.getAgentSet();
		accRelSet=manager.getAccRelSet();
		
		EpistemicReasoner reasoner = new EpistemicReasoner();	
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
		EpistemicKripkeModel str = new EpistemicKripkeModel(accRelSet,worldSet, agentSet);
		String ret="";
		
		Traductor t = new Traductor();
		formula=t.convertEMC(formula);
		
		RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);
		
		
		for (int y=0;y<worldSet.size();y++) {
		ret=ret+"The formula "+t.convertNormal(formula)+ " is "+ reasoner.WorldReasoner(formulaFinal, str, worldSet.get(y)) + " in w"+y+"\n";
		}
		ret=ret+"The formula "+t.convertNormal(formula)+" is "+ reasoner.ModelReasoner(formulaFinal,str)+" in the model";
		return ret;
	}


}