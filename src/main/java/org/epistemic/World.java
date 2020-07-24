package org.epistemic;

import java.util.ArrayList;

public class World {
    private  String name;
    private ArrayList<Character> atomList= new ArrayList<>();


    /**
     * Constructor 1
     * @param name
     */
    public World(String name){
        this.name = name;
    }

    /**
     * Constructor 2
     * @param name
     * @param atomList
     */
    public World(String name, ArrayList<Character> atomList){
       this.name = name;
       this.atomList = atomList;
    }

    /**
     * Devuelve el nombre del mundo
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Devuelve la lísta de átomos
     * @return
     */
    public ArrayList<Character> getAtomList(){
        return atomList;
    }

    /**
     * Devuelve true si contiene el átomo o false si no lo hace
      * @param atom
     * @return
     */
    public boolean contains(char atom){
        boolean result;
        if(!this.getAtomList().isEmpty()){
            if(this.getAtomList().contains(atom)){
                result=true;
            }else{
                result=false;
            }
        }else{
            result=false;
        }
        return result;
    }
}
