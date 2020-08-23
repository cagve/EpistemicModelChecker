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
        if (formula.getClass() == atom.getClass()) {
            if (world.contains(formula.toString().charAt(0))) {
                bol = true;
            } else {
                bol = false;
            }
        } else if (formula.getClass() == neg.getClass()) {
            neg = (Negation) formula;
            if (WorldReasoner(neg.getFormula(), model, world) == true) {
                bol = false;
            } else {
                bol = true;
            }
        } else if (formula.getClass() == con.getClass()) {
            con = (Conjunction) formula;
            if (WorldReasoner(con.get(0), model, world) == true && WorldReasoner(con.get(1), model, world)) {
                bol = true;
            } else {
                bol = false;
            }
        } else if (formula.getClass() == dis.getClass()) {
            dis = (Disjunction) formula;
            if (WorldReasoner(dis.get(0), model, world) == true || WorldReasoner(dis.get(1), model, world) == true) {
                bol = true;
            } else {
                bol = false;
            }
        } else if (formula.getClass() == imp.getClass()) {
            imp = (Implication) formula;
            if (WorldReasoner(imp.getFormulas().getFirst(), model, world) == true
                    && WorldReasoner(imp.getFormulas().getSecond(), model, world) == false) {
                bol = false;
            } else {
                bol = true;
            }
        } else if (formula.getClass() == eq.getClass()) {
            eq = (Equivalence) formula;
            Implication imp1 = new Implication(eq.getFormulas().getFirst(), eq.getFormulas().getSecond());
            Implication imp2 = new Implication(eq.getFormulas().getSecond(), eq.getFormulas().getFirst());
            if (WorldReasoner(imp1, model, world) == true && WorldReasoner(imp2, model, world) == true) {
                bol = true;
            } else {
                bol = false;
            }
        } else

        if (formula.getClass() == know.getClass()) {
            know = (Knowledge) formula;
            char agent = know.getAgent();
            WorldSet accessWorldSet = model.getAccWorld(world, agent);
            int j = 0;
            int length = accessWorldSet.getWorldSet().size();
            bol = true;
            if (accessWorldSet.getWorldSet().isEmpty()) {// Si no tiene relaciones
                bol = true;
            } else {
                while (j < length) {
                    bol = WorldReasoner(know.getFormula(), model, accessWorldSet.getWorldSet().get(0));
                    if (bol == false) {
                        break;
                    } else {
                        j++;
                    }

                }
            }

        } else if (formula.getClass() == posKnow.getClass()) {
            posKnow = (PosKnowledge) formula;
            char c = posKnow.getAgent();
            accRelSetAgent = model.getAccWorld(world, c);
            int i = 0;
            int length = accRelSetAgent.getWorldSet().size();
            bol = false;
            if (accRelSetAgent.getWorldSet().isEmpty()) {// Si no tiene relaciones
                bol = false;
            } else {
                while (i < length) {
                    accRelSetAgent = model.getAccWorld(world, c);
                    currentWorld = accRelSetAgent.getWorldSet().get(i);
                    bol = WorldReasoner(posKnow.getFormula(), model, currentWorld);
                    if (bol == true) {
                        break;
                    } else {
                        i++;
                    }

                }
            }
        }
        return bol;
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
