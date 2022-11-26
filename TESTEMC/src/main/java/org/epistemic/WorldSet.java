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

    public void debug_worlds(String ant) {
        for (int j = 0; j < this.getWorldSet().size();j++){
                System.out.println(ant+this.getWorldSet().get(j).getName());
        }
    }
}
