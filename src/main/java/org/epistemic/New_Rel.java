package org.epistemic;

public class New_Rel{
    private New_World first;
    private New_World second;
    private char agent;

    public New_Rel (New_World first, New_World second, char agent){
        this.first=first;
        this.second=second;
        this.agent=agent;
    }

    public New_Rel() {
	}

	/**
     * Devuelve el primer término de la relación
     * @return
     */
    public New_World getFirst(){
        return first;
    }

    /**
     * Devuelve el segundo término de la relación
     * @return
     */
    public New_World getSecond(){
        return second;
    }

    /**
     * Devuelve el agente de la relación
     * @return
     */
    public char getAgent(){
        return agent;
    } 

    /**
     * Devuelve la relacion
     */
    public String getString(){
        String first= this.getFirst().getName();
        String second= this.getSecond().getName();
        String agent = Character.toString(this.getAgent()); 
        String relation = first+","+second+","+agent;
        return relation;
    }

}
