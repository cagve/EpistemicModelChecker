package org.epistemic;
import java.util.HashSet;
import org.epistemic.New_World;

public class New_WorldSet extends HashSet<New_World>{
    private String name;

	public New_WorldSet(){
    }

	public New_WorldSet(String name){
        this.name=name;
    }
   
    public String getName(){
        return name;
    }

    public New_World getWorld(New_World world){
        if(!this.contains(world)){
            world=null;
        }
        return world;
    }
}
