package org.epistemic;

import org.epistemic.*;

import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.sf.tweety.logics.fol.syntax.FolSignature;


public class Main{
    public static void main(String[]args) throws ParserException, IOException, ExceptionManager{

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

        // ReadTxt read = new ReadTxt("/home/carlos/EMCProject/Examples/example2.txt");
        ReadTxt read = new ReadTxt("/home/carlos/EMCProject/Examples/exampleborrar.txt");
        Reasoner reasoner = new Reasoner();
        WorldSet worldSet = read.buildWorldSet();
        RelationSet relationSet = read.buildRelationSet();
        KripkeModel model = new KripkeModel(worldSet, relationSet);
        // for(int i=0;i<relationSet.size();i++){
        //     System.out.println(relationSet.get(i).getName());
        //     System.out.println(relationSet.get(i).getAgent());
        //     System.out.println(relationSet.get(i).getFirst().getName());
        //     System.out.println(relationSet.get(i).getSecond().getName());
        //     System.out.println("---------------------");
        // }
        String formula = "Ma(p)";
        Traductor traductor = new Traductor();
        formula=traductor.convertEMC(formula);
        RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);
        for(int i=0;i<worldSet.size();i++){
            System.out.println(reasoner.WorldReasoner(formulaFinal, model, worldSet.get(i))); 
        }
    }
}
