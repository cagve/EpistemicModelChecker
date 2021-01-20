package org.epistemic;
import java.util.HashSet;

public class RelationSet extends HashSet<Relation>{
    private String name;

	public RelationSet(){
    }

	public RelationSet(String name){
        this.name=name;
    }
   
    public String getName(){
        return name;
    }
    
    public Relation get(int index){
        Relation[] array = this.toArray( new Relation[this.size()] );
        return array[index];
    }

    public Relation getRel(Relation relation){
        Relation finalRel = relation;
        if(!this.contains(relation)){
            finalRel=null;
        }
        return finalRel;
    }
}
