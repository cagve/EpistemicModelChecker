package org.epistemic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;

class Main {
    public static void main(String[] args) throws IOException {
        String modelPath = "/home/karu/Downloads/example4.txt";
        ReadTxt txt = new ReadTxt(modelPath);
        WorldSet worldSet = txt.buildWorldSet();
        RelationSet relationSet = txt.buildRelationSet(worldSet);
        EpistemicModel model = new EpistemicModel(worldSet, relationSet);
        ArrayList<Character> agents = model.getAgentList();
        EpistemicParser epistemicParser = new EpistemicParser();
        Reasoner reasoner = new Reasoner();

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


        String formula = "Ka(p)";
        Traductor t = new Traductor();
        formula = t.convertEMC(formula);
        RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);

        System.out.println(formulaFinal);


        // DEBUG MODEL 
        System.out.println("===========================DEBUG MODEL==============================");
        for (int y = 0; y < worldSet.getWorldSet().size(); y++) {
            World world = worldSet.getWorldSet().get(y);
            System.out.println("======== Current "+ world.getName());
            for (int j = 0; j < agents.size();j++){
                WorldSet acc = model.getAccWorld(world, agents.get(j));
                acc.debug_worlds("=====Agent "+agents.get(j)+" access to ");
            }
        }

        // REASONER DEBUG
        System.out.println("===========================REASONER DEBUG==============================");
        for (int y = 0; y < worldSet.getWorldSet().size(); y++) {
            System.out.println("The formula " + t.convertNormal(formula) + " is "
                    + reasoner.WorldReasoner(formulaFinal, model, worldSet.getWorldSet().get(y)) + " in w" + y + "\n");
        }
    }
}
