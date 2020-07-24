package org.epistemic;

import java.util.ArrayList;

public class RelationSet {
    private final ArrayList<Relation> relationSet;

    /**
     * Constructor
     * @param relationSet
     */
    public RelationSet (ArrayList<Relation> relationSet){
        this.relationSet=relationSet;
    }

    /**
     * Devuelve el conjunto de relaciones
     * @return
     */
    public ArrayList<Relation> getRelationSet() {
        return relationSet;
    }

    /**
     * Devuelve las relaciones de un agente en concreto
     * @param agent
     * @return
     */
    public ArrayList<Relation> getRelationSetOfAgent(char agent){
        ArrayList<Relation> relationAgentSet=new ArrayList<>();
        for(int i=0;i<relationSet.size();i++){
            if(relationSet.get(i).getAgent()==agent){
                relationAgentSet.add(relationSet.get(i));
            }
        }
        return relationAgentSet;
    }
}
