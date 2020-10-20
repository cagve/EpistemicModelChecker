package org.epistemic;

import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;

import java.io.IOException;

import org.epistemic.EpistemicParser;
import org.epistemic.Traductor;
import net.sf.tweety.logics.fol.syntax.FolAtom;
import net.sf.tweety.logics.fol.syntax.FolSignature;

import org.epistemic.New_World;
import org.epistemic.New_Rel;
import org.epistemic.New_RelSet;
import org.epistemic.New_Reasoner;

public class Main{
    public static void main(String[]args) throws ParserException, IOException, ExceptionManager{
        //char b = 'b';
        //char c = 'c';
        //Predicate r = new Predicate("r");
        //Predicate t = new Predicate("t");
        //Predicate p = new Predicate("p");
        //Predicate q = new Predicate("q");
        //FolAtom atom_q = new FolAtom(q);
        //FolAtom atom_p = new FolAtom(p);
        //FolAtom atom_r = new FolAtom(r);
        //FolAtom atom_t = new FolAtom(t);

        //New_World w0 = new New_World("w0");
        //New_World w3 = new New_World("w3");
        //New_World w4 = new New_World("w4");
        //w0.add(atom_p);
        //w0.add(atom_q);
        //w3.add(atom_p);

        //New_WorldSet worldSet = new New_WorldSet();
        //worldSet.add(w0);
        //worldSet.add(w3);
        //worldSet.add(w4);


        //New_Rel r1 = new New_Rel(w0,w0,'b');
        //New_Rel r2 = new New_Rel(w0,w0,'c');
        //New_Rel r3 = new New_Rel(w3,w3,'b');
        //New_Rel r4 = new New_Rel(w3,w3,'c');
        //New_Rel r5 = new New_Rel(w4,w4,'b');
        //New_Rel r6 = new New_Rel(w4,w4,'c');
        //New_Rel r7 = new New_Rel(w0,w3,'b');
        //New_Rel r8 = new New_Rel(w3,w4,'c');

        //New_RelSet relset = new New_RelSet();
        //relset.add(r1);
        //relset.add(r2);
        //relset.add(r3);
        //relset.add(r4);
        //relset.add(r5);
        //relset.add(r6);
        //relset.add(r7);
        //relset.add(r8);

        //New_KripkeModel model = new New_KripkeModel(worldSet,relset);
        //New_Reasoner reasoner = new New_Reasoner(); 

        ////Formula

        //EpistemicParser epistemicParser = new EpistemicParser();
        //FolSignature sig = new FolSignature();
        //sig.add(new Predicate("p", 0));
        //sig.add(new Predicate("q", 0));
        //sig.add(new Predicate("r", 0));
        //sig.add(new Predicate("s", 0));
        //sig.add(new Predicate("t", 0));
        //sig.add(new Predicate("u", 0));
        //sig.add(new Predicate("v", 0));
        //sig.add(new Predicate("w", 0));
        //sig.add(new Predicate("x", 0));
        //sig.add(new Predicate("y", 0));
        //sig.add(new Predicate("z", 0));
        //epistemicParser.setSignature(sig);

        //String formula = "Mb(Mc(\\lnot(p)\\land\\lnot(q)))";
        //Traductor traductor = new Traductor();
        //formula=traductor.convertEMC(formula);
        //RelationalFormula formulaFinal = epistemicParser.parseFormula(formula);
        //// System.out.println(worldSet.size());
        //// System.out.println(relset.size());
        //// System.out.println(model.accTo(b, w0));
        //// System.out.println(model.accTo(b, w1));
        //// System.out.println(model.accTo(b, w2));
        //// System.out.println(model.accTo(b, w3));
        //// System.out.println(model.accTo(c, w0));
        //// System.out.println(model.accTo(c, w1));
        //// System.out.println(model.accTo(c, w2));
        //// System.out.println(model.accTo(c, w3));

        //System.out.println(reasoner.WorldReasoner(formulaFinal,model,w0));
        //System.out.println(reasoner.WorldReasoner(formulaFinal,model,w3));
        //System.out.println(reasoner.WorldReasoner(formulaFinal,model,w4));
        ParserManager p = new ParserManager();
        // ReadTxt read = new ReadTxt("/home/carlos/EMCProject/Examples/exampledef.txt");
        ReadTxt read = new ReadTxt("/home/carlos/EMCProject/Examples/example1.txt");
        System.out.println(read.parseFile());
        // System.out.println(p.isValid("Rp={<w1,w123>}"));
        // System.out.println(p.isValid("V(p)={w12341,w1}"));
        // System.out.println(p.isValid("V(b)={w1,w123}"));
        // System.out.println(p.isValid("W={w5,w6,w13}"));
        // p.isValid("Ra={<w2,w123>}");
        // p.isValid("Rp={<w1,w123>}");
        // p.isValid("V(p)={w12341,w1}");
        // p.isValid("V(b)={w1,w123}");
        // p.isValid("W={w5,w6,w13}");
    } 
}
