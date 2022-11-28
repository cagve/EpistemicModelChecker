package org.epistemic;
import java.util.*;

/**
 * Clase que construye un modelo epistémico
 */
public class EpistemicModel {

    private final WorldSet worldSet;
    private final RelationSet relationSet;

    /**
     * Constructor
     * @param worldSet
     * @param relationSet
     */
    public EpistemicModel(WorldSet worldSet, RelationSet relationSet){
       this.worldSet=worldSet;
       this.relationSet=relationSet;
    }

    /**
     * Devuelve el conjunto de relaciones del agente que parten del mundo introducido
     * @param world
     * @param agent
     * @return
     */
    public RelationSet getRelationOf(World world, char agent) {
        ArrayList<Relation> relationArraySet= new ArrayList<Relation>();
        for (int i = 0; i < relationSet.getRelationSet().size(); i++) {
            String currentWorldName = relationSet.getRelationSet().get(i).getFirst().getName();
            String worldName = world.getName();
            if (currentWorldName.equals(worldName) && relationSet.getRelationSet().get(i).getAgent() == agent) {
                relationArraySet.add(relationSet.getRelationSet().get(i));
            }
        }
        RelationSet relationSet= new RelationSet(relationArraySet);
        return relationSet;
    }

    /**
     * Devuelve el conjunto de relaciones
     * @return
     */
    public RelationSet getRelationSet() {
        return relationSet;
    }

    /**
     * Devuelve el conjunto de mundos a los que accede el agente desde el mundo introducido
     * @param world
     * @param agent
     * @return
     */
    public WorldSet getAccWorld(World world, char agent){
        RelationSet relationSet = this.getRelationOf(world,agent);
        ArrayList<World> worldArrayList = new ArrayList<>();

        for(int i= 0; i<relationSet.getRelationSet().size();i++){
            worldArrayList.add(relationSet.getRelationSet().get(i).getSecond());
        }
        WorldSet worldSet = new WorldSet(worldArrayList);
        return worldSet;
    }

    /**
     * Devuelve el conjunto de mundos
     * @return
     */
    public WorldSet getWorldSet(){
        return worldSet;
    }

    public World getWorldByName(String name){
        World not = new World("No existe");
        for(int j=0;j<worldSet.getWorldSet().size();j++){
            World world = worldSet.getWorldSet().get(j);
            if (world.getName() == name){
                return world;
            }
        }
        return not;
    }

    /**
     * Devuelve el conjunto de átomos
     * @return
     */
    public ArrayList<Character> getAtomList(){
        ArrayList<Character> atomList = new ArrayList<>();
        for(int i =0; i< worldSet.getWorldSet().size();i++){
            atomList.addAll(worldSet.getWorldSet().get(i).getAtomList());
        }
        return atomList;
    }



    public ArrayList<Character> removeDuplicate(ArrayList<Character> yourList){
        Set<Character> set = new HashSet<Character>(yourList);
        yourList.clear();
        yourList.addAll(set);
        return yourList;
    }
    /**
     * Devuelve el resultado de los átomos que son verdaderos en el mundo
     * @param world
     * @return
     */
    public ArrayList<Character>getAtomOf(World world){
        ArrayList<Character> atomList = this.getAtomList();
        ArrayList<Character>resultList= new ArrayList<>();

        for(int i=0;i<atomList.size();i++){
            if(world.contains(atomList.get(i))){
                resultList.add(atomList.get(i));
            }
        }
        resultList=this.removeDuplicate(resultList);
        return resultList;
    }

    /**
     * Devuelve los agentes. Ahora mismo da soporte para 4 agentes, a, b, c, d
     * @return
     */
    public ArrayList<Character> getAgentList(){
        ArrayList<Character> agentList = new ArrayList<>();
        agentList.add('a');
        agentList.add('b');
        agentList.add('c');
        agentList.add('d');
        return agentList;
    }
 }


