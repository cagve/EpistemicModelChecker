package org.epistemic;
import java.util.HashSet;

public class New_RelSet extends HashSet<New_Rel>{
    private String name;

	public New_RelSet(){
    }

	public New_RelSet(String name){
        this.name=name;
    }
   
    public String getName(){
        return name;
    }

    public New_Rel getRel(New_Rel relation){
        New_Rel finalRel = relation;
        if(!this.contains(relation)){
            finalRel=null;
        }
        return finalRel;
    }
}
