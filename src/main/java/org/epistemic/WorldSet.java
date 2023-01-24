package org.epistemic;

import java.util.ArrayList;

public class WorldSet {
    private final ArrayList<World> worldSet;
/**
     * Constructor
     * @param worldSet
     */
    public WorldSet(ArrayList<World>worldSet){
        this.worldSet=worldSet;
    }

    /**
     * Devuelve el conjunto de mundo
     * @return
     */
    public ArrayList<World> getWorldSet() {
        return worldSet;
    }

	public String getWorldSetString(){
		String worldListString = "[";
		for (int k=0; k < this.worldSet.size();k++){
			World world = this.worldSet.get(k);
			if(k==this.worldSet.size()-1){
				worldListString = worldListString + world.getName() + "]";
			}else{
				worldListString = worldListString + world.getName() + ", ";
			}
		}
		return worldListString;
	}
}
