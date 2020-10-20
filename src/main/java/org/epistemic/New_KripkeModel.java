package org.epistemic;
import org.epistemic.*;

import net.sf.tweety.logics.fol.syntax.FolAtom;

public class New_KripkeModel {
     private New_WorldSet worldSet;
	 private New_RelSet relSet;

	public New_KripkeModel(New_WorldSet worldSet, New_RelSet relSet){
        this.worldSet=worldSet;
        this.relSet=relSet;
   }

   public New_RelSet getRelSet(){
       return relSet;
   }
   public New_WorldSet getWorldSet(){
       return worldSet;
   }
   /* Devuelve el dominio de relaciones*/
   public int relationsDom(){
       return worldSet.size();
   }
   /* Devuelve el dominio de mundos */
   public int worldsDom(){
       return worldSet.size();
   }
   // /* Te dice a donde accede un agente desde un mundo dado */
   public New_WorldSet accTo(char agent, New_World currentWorld){
       New_WorldSet accWorldSet = new New_WorldSet();
       for (New_Rel relToCheck : relSet) {
           if(relToCheck.getAgent()==agent && relToCheck.getFirst()==currentWorld){
               accWorldSet.add(relToCheck.getSecond());
           }
       }
       return accWorldSet;
   }
}
