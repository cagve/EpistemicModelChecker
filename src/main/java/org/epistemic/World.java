package org.epistemic;

import java.util.ArrayList;
import java.util.HashSet;
import net.sf.tweety.logics.fol.syntax.*;

public class World extends HashSet<FolAtom>{
    private  String name;

    /**
     * Constructor 1
     * @param name Nombre del mundo
     */
    public World(String name){
        this.name = name;
    }

    /**
     * Devuelve el name del mundo
     * @return Nombre del mundo
     */
    public String getName(){
        return name;
    }

    /**
     * Añade los átomos
     * @param atomList Lista de átomos
     */
    public void addAtoms(ArrayList<FolAtom> atomList){
        for(int i = 0; i < atomList.size();i++){
            this.add(atomList.get(i));
        } 
    }

    public ArrayList<Character> toArrayList(){
        ArrayList<Character> returnList = new ArrayList<Character>();
         for( FolAtom atom : this ){
             returnList.add(atom.toString().charAt(0));
        } 
        return returnList;
    }

    /**
     * A la hora de comprobar si dos mundos son iguales checkea el nombre de cada mundo
     **/
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        World other = (World) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
