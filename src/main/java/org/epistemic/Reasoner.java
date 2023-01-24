/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020
 */
package org.epistemic;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.*;

import java.io.IOException;
import java.util.ArrayList;

public class Reasoner {
    private Predicate q = new Predicate("q");
    private char a;
    private RelationalFormula atom = new FolAtom(q);
    private Negation neg = new Negation(atom);
    private Conjunction con = new Conjunction();
    private Disjunction dis = new Disjunction();
    private Implication imp = new Implication(atom, atom);
    private Equivalence eq = new Equivalence(atom, atom);
    private Knowledge know = new Knowledge(atom, a);
    private PosKnowledge posKnow = new PosKnowledge(atom, a);
    private WorldSet accRelSetAgent;
    private World currentWorld;
    private boolean bol;

    /**
     * Ejecuta el razonador en el mundo introducido
     * 
     * @param formula
     * @param model
     * @param world
     * @return
     */

    public boolean WorldReasoner(RelationalFormula formula, EpistemicModel model, World worldToAnalize) {
        FormulaManager man = new FormulaManager();
        ArrayList<RelationalFormula> subformula =  man.getSubFormula(formula);

        System.out.println(">>> Analizando reasoner");
        for (int i=0; i<=man.getMaxLg(subformula); i++){
            ArrayList<RelationalFormula> subformulaILg = man.getFormulaLg(i,subformula);
            if (subformulaILg.size()>0){
            for (int w=0; w<model.getWorldSet().getWorldSet().size();w++){
                World world = model.getWorldSet().getWorldSet().get(w);
                for (int j=0; j<subformulaILg.size(); j++){
                    RelationalFormula currentFormula = subformulaILg.get(j);
                    System.out.println(currentFormula+ "is "+ currentFormula.getClass());
                    if (currentFormula.getClass() == atom.getClass()) {
                        // System.out.println(" atom is in "+world.getFormulaList()+" "+world.contains(currentFormula.toString().charAt(0)));
                        if (world.contains(currentFormula.toString().charAt(0))) {
                            world.addTrueFormula(currentFormula);
                        } 
                    } else if (currentFormula.getClass() == neg.getClass()) {
                        neg = (Negation) currentFormula;
                        System.out.println("NEG");
                        System.out.println("The formula "+neg.getFormula()+"is in "+world.getFormulaList());
                        if (!world.containsTrueFormula(neg.getFormula())){
                            world.addTrueFormula(currentFormula);
                        System.out.println("Añadiendo formula "+currentFormula);
                        };
                    } else if (currentFormula.getClass() == con.getClass()) {
                        con = (Conjunction) currentFormula;
                            System.out.println("CON");
                            System.out.println("The formula "+con.getFormula()+"is in "+world.getFormulaList());
                        if (world.containsTrueFormula(con.get(0)) && world.containsTrueFormula(con.get(1))){
                            System.out.println("Añadiendo formula "+currentFormula);
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (currentFormula.getClass() == dis.getClass()) {
                        dis = (Disjunction) currentFormula;
                        if (world.containsTrueFormula(dis.get(0)) || world.containsTrueFormula(dis.get(1))){
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (currentFormula.getClass() == imp.getClass()) {
                        imp = (Implication) currentFormula;
                        if (!world.containsTrueFormula(imp.getFormulas().getFirst()) || world.containsTrueFormula(imp.getFormulas().getSecond())){
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (currentFormula.getClass() == eq.getClass()) {
                        eq = (Equivalence) currentFormula;
                        if (world.containsTrueFormula(eq.getFormulas().getFirst()) == world.containsTrueFormula(eq.getFormulas().getSecond())){
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (currentFormula.getClass() == know.getClass()) {
                        know = (Knowledge) currentFormula;
                        char agent = know.getAgent();
                        if (model.getAccWorld(world, agent).getWorldSet().size()==0){
                            world.addTrueFormula(currentFormula);
                        }else{
                            for (int k=0; k<model.getAccWorld(world, agent).getWorldSet().size();k++){
                                World accWorld = model.getAccWorld(world,agent).getWorldSet().get(k);
                                // System.out.print(k+":"+know.getFormula()+" is in "+accWorld.getName()+accWorld.getFormulaList());
                                // System.out.println(accWorld.containsTrueFormula(posKnow.getFormula()));
                                if (!accWorld.containsTrueFormula(know.getFormula())){
                                    break;
                                }
                                world.addTrueFormula(currentFormula);
                            }
                        }
                    } else if (currentFormula.getClass() == posKnow.getClass()) {
                        posKnow = (PosKnowledge) currentFormula;
                        // System.out.print("posknow > ");
                        char agent = posKnow.getAgent();
                        for (int k=0; k<model.getAccWorld(world, agent).getWorldSet().size();k++){
                            World accWorld = model.getAccWorld(world,agent).getWorldSet().get(k);
                            System.out.print(k+":"+posKnow.getFormula()+" is in "+accWorld.getName()+accWorld.getFormulaList());
                            System.out.println(accWorld.containsTrueFormula(posKnow.getFormula()));
                            if (accWorld.containsTrueFormula(posKnow.getFormula())){
                                world.addTrueFormula(currentFormula);
                                break;
                            }
                        }
                    }
                }
                }
            }
        }
        System.out.println("Formula "+formula+" in "+worldToAnalize.getFormulaList());
        return worldToAnalize.containsTrueFormula(formula);
    }

    /**
     * Ejecuta el razonador en el modelo introducido
     * 
     * @param formula
     * @param model
     * @return
     */
    public boolean ModelReasoner(RelationalFormula formula, EpistemicModel model) {
        WorldSet worldSet = model.getWorldSet();
        bol = true;
        for (int i = 0; i < worldSet.getWorldSet().size(); i++) {
            currentWorld = worldSet.getWorldSet().get(i);
            if (this.WorldReasoner(formula, model, currentWorld)) {

            } else {
                bol = false;
                break;
            }
        }

        return bol;
    }

    /**
     * Ejecuta ambos razonadores en el modelo introducido
     * 
     * @param formula
     * @param modelPath
     * @return
     * @throws IOException
     */
    public String runReasoner(String formula, String modelPath) throws IOException {
        String result = "";
        ReadTxt txt = new ReadTxt(modelPath);
        WorldSet worldSet = txt.buildWorldSet();
        RelationSet relationSet = txt.buildRelationSet(worldSet);
        EpistemicModel model = new EpistemicModel(worldSet, relationSet);
        EpistemicParser epistemicParser = new EpistemicParser();

        FolSignature sig = new FolSignature();
        sig.add(new Predicate("p", 0));
        sig.add(new Predicate("q", 0));
        sig.add(new Predicate("r", 0));
        sig.add(new Predicate("s", 0));
        sig.add(new Predicate("t", 0));
        sig.add(new Predicate("u", 0));
        sig.add(new Predicate("v", 0));
        sig.add(new Predicate("w", 0));
        sig.add(new Predicate("x", 0));
        sig.add(new Predicate("y", 0));
        sig.add(new Predicate("z", 0));
        epistemicParser.setSignature(sig);

        Traductor t = new Traductor();
        formula = t.convertEMC(formula);
        RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);

        for (int y = 0; y < worldSet.getWorldSet().size(); y++) {
            World world = worldSet.getWorldSet().get(y);
            System.out.println("===============================================");
            System.out.println("Analizando mundo "+world.getName());
            System.out.println("Lista de formulas "+world.getFormulaList());
            boolean value = this.WorldReasoner(formulaFinal, model, worldSet.getWorldSet().get(y));
            result = result + "The formula " + t.convertNormal(formula) + " is "
                    + value + " in " + world.getName() ;
            if (!value && formulaFinal.getClass() == know.getClass()){
                know = (Knowledge) formulaFinal;
                char agent = know.getAgent();
                for (int k=0; k<model.getAccWorld(world, agent).getWorldSet().size();k++){
                    World accWorld = model.getAccWorld(world,agent).getWorldSet().get(k);
                    if (!accWorld.containsTrueFormula(know.getFormula())){
                        result = result + " because agent "+agent+" accesses "+accWorld.getName()+" and "+t.convertNormal(know.getFormula().toString())+" is false in that world\n";
                        break;
                    }
                }
            }else if (value && formulaFinal.getClass() == know.getClass()){
                know = (Knowledge) formulaFinal;
                char agent = know.getAgent();
				if(model.getAccWorld(world,agent).getWorldSet().size() < 1){
					result = result + " because agent "+agent+" does not access any world.\n";
				}else{
					result = result + " because agent "+agent+" accesses to "+model.getAccWorld(world,agent).getWorldSetString()+" and "+t.convertNormal(know.getFormula().toString())+" is true in those worlds.\n";
				}
            } else if(value && formulaFinal.getClass() == posKnow.getClass()){
                posKnow = (PosKnowledge) formulaFinal;
                char agent = posKnow.getAgent();
                for (int k=0; k<model.getAccWorld(world, agent).getWorldSet().size();k++){
                    World accWorld = model.getAccWorld(world,agent).getWorldSet().get(k);
                    if (accWorld.containsTrueFormula(posKnow.getFormula())){
                        result = result + " because agent "+agent+" accesses "+accWorld.getName()+" and "+t.convertNormal(posKnow.getFormula().toString())+" is true in that world\n";
                        break;
                    }
                }
            } else if(!value && formulaFinal.getClass() == posKnow.getClass()){
                posKnow = (PosKnowledge) formulaFinal;
                char agent = posKnow.getAgent();
				if(model.getAccWorld(world,agent).getWorldSet().size() < 1){
					result = result + " because agent "+agent+" does not access any world.\n";
				}else{
					result = result + " because agent "+agent+" accesses "+model.getAccWorld(world,agent).getWorldSetString()+" and "+t.convertNormal(know.getFormula().toString())+" is false in those worlds.\n";
				}
            }else{
                result = result + "\n";
            }
        System.out.println("Finalizando mundo "+world.getName());
        System.out.println("Lista de formulas "+world.getFormulaList());
        System.out.println("===============================================");
        }
        result = result + "The formula " + t.convertNormal(formula) + " is " + this.ModelReasoner(formulaFinal, model)
                + " in the model";
        return result;
    }
}
