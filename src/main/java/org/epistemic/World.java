package org.epistemic;

import java.util.ArrayList;

import net.sf.tweety.logics.commons.syntax.RelationalFormula;

public class World {
    private  String name;
    private ArrayList<Character> atomList= new ArrayList<>();
    private ArrayList<RelationalFormula> formulaList= new ArrayList<RelationalFormula>();


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

    public ArrayList<RelationalFormula> getFormulaList(){
        return this.formulaList;
    }

    public void addTrueFormula(RelationalFormula formula){
        // if (!this.containsTrueFormula(formula)){
            this.formulaList.add(formula);
        // }
    }

    
    public boolean containsTrueFormula(RelationalFormula formula){
        ArrayList<String> formulaListString = new ArrayList<>();
        for (int j=0; j<this.formulaList.size(); j++){
            formulaListString.add(this.formulaList.get(j).toString());
             
        }
        return formulaListString.contains(formula.toString());
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
