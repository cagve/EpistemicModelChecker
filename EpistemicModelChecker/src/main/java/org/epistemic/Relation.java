package org.epistemic;

public class Relation {
    private final World first;
    private final World second;
    private final char agent;
    private final String name;

    /**
     * Contrusctor
     * @param name
     * @param first
     * @param second
     * @param agent
     */
    public Relation (String name, World first, World second, char agent){
        this.first=first;
        this.second=second;
        this.agent=agent;
        this.name = name;
    }

    /**
     * Devuelve el primer término de la relación
     * @return
     */
    public World getFirst(){
        return first;
    }

    /**
     * Devuelve el nombre de la relación
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Devuelve el segundo término de la relación
     * @return
     */
    public World getSecond(){
        return second;
    }

    /**
     * Devuelve el agente de la relación
     * @return
     */
    public char getAgent(){
        return agent;
    }
}


