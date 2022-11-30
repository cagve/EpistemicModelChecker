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
    public boolean WorldReasoner(RelationalFormula formula, EpistemicModel model, World world) {
        FormulaManager man = new FormulaManager();
        ArrayList<RelationalFormula> subformula =  man.getSubFormula(formula);

        for (int i=0; i<=man.getMaxLg(subformula); i++){
            for (int j=0; j<subformula.size(); j++){
                RelationalFormula currentFormula = subformula.get(j);
                if (man.getLg(currentFormula) == i){
                    if (currentFormula.getClass() == atom.getClass()) {
                        if (world.contains(currentFormula.toString().charAt(0))) {
                            world.addTrueFormula(currentFormula);
                        } 
                    } else if (currentFormula.getClass() == con.getClass()) {
                        con = (Conjunction) currentFormula;
                        if (world.containsTrueFormula(con.get(0)) && world.containsTrueFormula(con.get(1))){
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (currentFormula.getClass() == dis.getClass()) {
                        dis = (Disjunction) currentFormula;
                        if (world.containsTrueFormula(dis.get(0)) || world.containsTrueFormula(dis.get(1))){
                            world.addTrueFormula(currentFormula);
                        };
                    } else if (formula.getClass() == know.getClass()) {
                         know = (Knowledge) formula;
                         char agent = know.getAgent();
                         for (int k=0; k<model.getAccWorld(world, agent).getWorldSet().size();k++){
                             World accWorld = model.getAccWorld(world,agent).getWorldSet().get(k);
                             if (!accWorld.containsTrueFormula(know.getFormula())){
                                 break;
                             }
                             world.addTrueFormula(currentFormula);
                         }
                    }
                }
            }
        }
        return world.containsTrueFormula(formula);
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
            result = result + "The formula " + t.convertNormal(formula) + " is "
                    + this.WorldReasoner(formulaFinal, model, worldSet.getWorldSet().get(y)) + " in w" + y + "\n";
        }
        result = result + "The formula " + t.convertNormal(formula) + " is " + this.ModelReasoner(formulaFinal, model)
                + " in the model";
        return result;
    }
}
