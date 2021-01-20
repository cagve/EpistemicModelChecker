package org.epistemic;
import java.util.HashSet;
import org.epistemic.World;

public class WorldSet extends HashSet<World>{
    private String name;
    private World world;
    private World currentWorld;

	public WorldSet(){
    }

	public WorldSet(String name){
        this.name=name;
    }
   
    public String getName(){
        return name;
    }

    public World get(int index){
        World[] array = this.toArray( new World[this.size()] );
        return array[index];
    }

    public World getWorldByString(String worldName){
        for(int i=0; i<this.size();i++){
            currentWorld=get(i);
            if(currentWorld.getName().equals(worldName)){
                world=currentWorld;
            }
        } 
        return world;
    }
    public World getWorld(World world){
        if(!this.contains(world)){
            world=null;
        }
        return world;
    }
}
