/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 */
package org.epistemic;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.*;


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
    private World currentWorld;
    private boolean bol;

    /**
     * Ejecuta el razonador en el mundo introducido
     * @param formula Formula a evalúa
     * @param model Modelo en el que se evalúa la fórmula
     * @param world Mundo en el que se evalúa la fórmula
     * @return True or False
     */
    public boolean WorldReasoner(RelationalFormula formula, KripkeModel model, World world) {
        if (formula.getClass() == atom.getClass()) {
            if (world.contains(formula)) {
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
            // Implication imp1 = new Implication(eq.getFormulas().getFirst(), eq.getFormulas().getSecond());
            // Implication imp2 = new Implication(eq.getFormulas().getSecond(), eq.getFormulas().getFirst());
            if (WorldReasoner(eq.getFormulas().getFirst(), model, world) == WorldReasoner(eq.getFormulas().getSecond(), model, world)) {
                bol = true;
            } else {
                bol = false;
            }
        } else

        if (formula.getClass() == know.getClass()) {
            know = (Knowledge) formula;
            char agent = know.getAgent();
            WorldSet accessWorldSet = model.accTo(agent, world);
            bol = true;
            if (accessWorldSet.isEmpty()) {// Si no tiene relaciones
                bol = true;
            } else {
                for (World accWorld : accessWorldSet) {
                    bol = WorldReasoner(know.getFormula(), model, accWorld);
                    if (bol == false) {
                        break;
                    }

                }
            }

        } else if (formula.getClass() == posKnow.getClass()) {
            posKnow = (PosKnowledge) formula;
            char agent = posKnow.getAgent();
            WorldSet accessWorldSet = model.accTo(agent, world);
            bol = false;
            if (!accessWorldSet.isEmpty()) {// Si no tiene relaciones
                for (World accWorld : accessWorldSet) {
                    bol = WorldReasoner(posKnow.getFormula(), model, accWorld);
                    if (bol == true){
                        break;
                    }
                }
            }
        }
        return bol;
    }

    /**
     * Ejecuta el razonador en el modelo introducido
     * @param formula Fórmula a evaluar
     * @param model Modelo donde se evalúa
     * @return True or False
     */
    public boolean ModelReasoner(RelationalFormula formula, KripkeModel model) {
        WorldSet worldSet = model.getWorldSet();
        bol = true;
        for (int i = 0; i < worldSet.size(); i++) {
            currentWorld = worldSet.get(i);
            if (!this.WorldReasoner(formula, model, currentWorld)) {
                bol = false;
                break;
            }
        } 
        return bol;
    }
}
