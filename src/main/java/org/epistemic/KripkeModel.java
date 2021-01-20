package org.epistemic;


public class KripkeModel {
    private WorldSet worldSet;
    private RelationSet relSet;

    /**
     * Constructor
     * @param worldSet Conjunto de mundos
     * @param relSet Conjunto de relaciones
     */ 
    public KripkeModel(WorldSet worldSet, RelationSet relSet){
        this.worldSet=worldSet;
        this.relSet=relSet;
    }

    /**
     * Devuelve la lista de relaciones
     * @return Lista de relaciones
     */
    public RelationSet getRelSet(){
        return relSet;
    } 

    /**
     * Devuelve la lista de mundos
     * @return Lista de mundos
     */
    public WorldSet getWorldSet(){
        return worldSet;
    }

    /**
     * Devuelve la lista de mundos a los que un agente accede desde un mundo
     * @param agent El agente de la relación
     * @param currentWorld El mundo actual
     * @return Lista de mundos
     */
    public WorldSet accTo(char agent, World currentWorld){
        WorldSet accWorldSet = new WorldSet();
        for (Relation relToCheck : relSet) {
            if(relToCheck.getAgent()==agent && relToCheck.getFirst().getName().equals(currentWorld.getName())){
                accWorldSet.add(worldSet.getWorldByString(relToCheck.getSecond().getName()));
            }
        }
        return accWorldSet;
    }

}
