package org.epistemic;

/**
 * Clase que representa las relaciones del modelo
 * @version 3.01
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * */
public class Relation{
    private World first;
    private World second;
    private char agent;

    /**
     * Constructor 1
     * @param first Primer mundo de la dupla
     * @param second Segundo mundo de la dupla
     * @param agent Agente de la relación
     */
    public Relation (World first, World second, char agent){
        this.first=first;
        this.second=second;
        this.agent=agent;
    }

    /**
     * Constructor 2
     * */
    public Relation() {
	}
    
    /**
     * Constructor 3
     * @param relation Relación escrita en una cadena
     * @param agent Agente de la relación
     * @deprecated
     */
    public Relation (String relation, Character agent){
        int beginBracket=relation.indexOf('<');
        int endBracket=relation.indexOf('>');
        relation=relation.substring(beginBracket+1, endBracket); 
        String[] field = relation.split(",");
        first = new World(field[0]);
        second = new World(field[1]); 
        this.agent=agent;
    }

    public String getName(){
        return this.getFirst().getName() + this.getSecond().getName();
    }

	/**
     * Devuelve el primer término de la relación
     * @return first Primer múndo
     */
    public World getFirst(){
        return first;
    }

    /**
     * Devuelve el segundo término de la relación
     * @return second Segundo múndo
     */
    public World getSecond(){
        return second;
    }

    /**
     * Devuelve el agente de la relación
     * @return agent Agente de la relación
     */
    public char getAgent(){
        return agent;
    } 

    /**
     * Devuelve la relacion
     * @return Devuelve la relación en una String
     */
    public String getString(){
        String first= this.getFirst().getName();
        String second= this.getSecond().getName();
        String agent = Character.toString(this.getAgent()); 
        String relation = first+","+second+","+agent;
        return relation;
    } 
}
