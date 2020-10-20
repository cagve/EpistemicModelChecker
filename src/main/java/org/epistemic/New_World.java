package org.epistemic;
import java.util.HashSet;
import net.sf.tweety.logics.fol.syntax.*;

public class New_World extends HashSet<FolAtom>{
    private  String name;
    /**
     * Constructor 1
     * @param name
     */
    public New_World(String name){
        this.name = name;
    }

    /**
     * Devuelve el name del mundo
     * @return
     */
    public String getName(){
        return name;
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
        New_World other = (New_World) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
