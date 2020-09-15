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
}
