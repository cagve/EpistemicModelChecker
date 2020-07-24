/*Class that builds world and relation:
 * BuildWorld(int q) -> construye el mundo
 * BuildRel(int q) -> construye las relaciones
 */

/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020 
 */
package EpistemicChecker;

import java.util.ArrayList;
import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.ml.syntax.MlBeliefSet;

public class Builder {
	int n_mundos;
	String atoms;
	String relacion;
	String agent;
	String formula;
	int first;
	int second;
	char c;
	int n_relaciones;
	int n_agents;
	
	ArrayList<Character> agents = new ArrayList<Character>();
	ArrayList<MlBeliefSet> worlds = new ArrayList<MlBeliefSet>();
	ArrayList<Character> agentList = new ArrayList<Character>();
	ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> relsList =	new ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>>();
	
	public Builder() {
		
	}
	
	public ArrayList<MlBeliefSet> BuildWorld(int q){	
		for (int i=0;i<q;i++) {
			worlds.add(new MlBeliefSet());	
		}
		return worlds;
	}
	
	public ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> BuildRel(int q){
		for (int i=0;i<q;i++) {
			relsList.addAll(new ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>>());	
		}
		return relsList;
	}

	
}
