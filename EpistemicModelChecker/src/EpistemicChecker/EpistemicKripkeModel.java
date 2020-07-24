/* Build the epistemic kripke model. It has 3 method:
 * getAgents -> return the agent's list
 * getAccRel -> return the relations's list of an agent
 * getRelations -> return the relations of all agent
 * getWorld -> return the worlds's list
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

public class EpistemicKripkeModel {

	private ArrayList<MlBeliefSet> worldSet= new ArrayList<MlBeliefSet>();
	private ArrayList<MlBeliefSet> worldsVar = new ArrayList<MlBeliefSet>();
	private MlBeliefSet worldVar;
	private ArrayList<Pair<MlBeliefSet, MlBeliefSet>> accRelSetAgent = new ArrayList<Pair<MlBeliefSet, MlBeliefSet>>();
	private ArrayList<ArrayList<Pair<MlBeliefSet, MlBeliefSet>>> accRelSet = new ArrayList<ArrayList<Pair<MlBeliefSet, MlBeliefSet>>>();
	private ArrayList<Character> agentList;
	
	int pos;

	
	public EpistemicKripkeModel (ArrayList<ArrayList<Pair<MlBeliefSet, MlBeliefSet>>> accRelSet, ArrayList<MlBeliefSet> worldSet, ArrayList<Character> agentList) {
		this.accRelSet=accRelSet;
		this.worldSet=worldSet;
		this.agentList = agentList;
	}
	
	public ArrayList<Character> getAgents(){
		return agentList;
	}
	
	public ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> getAccRel(){
		return accRelSet;
	}
	
	public ArrayList<MlBeliefSet> getRelations(MlBeliefSet world, Character agent) {
		pos = agentList.indexOf(agent); //Devuelve posici�n del agente
		accRelSetAgent=accRelSet.get(pos);		
		int arrayLength = accRelSetAgent.size();
		worldsVar.clear();
	
		for(int i=0;i<arrayLength;i++) {
			if(world.equals(accRelSetAgent.get(i).getFirst())) {
				worldVar=accRelSetAgent.get(i).getSecond();
				worldsVar.add(worldVar);
				}
			}
		
		return worldsVar;
	}
	
	public ArrayList<MlBeliefSet> getWorlds() {
		return worldSet;
	}
}
