/* CAMBIAR DOS PARAMETROS
*/

package org.graph;

import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import org.epistemic.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CreateGraph {
    String style = "graph{padding:82px,82px,82px,82px; }"
        +"node {"
        +"	size-mode:dyn-size;"
        +"    size: 40px;"
        +"    shape: circle;"
        +"	text-background-mode:plain;"
        +"	text-background-color:#8facb4;"
        +"    fill-color: #8facb4;"
        +"    stroke-mode: plain;"
        +"    stroke-color: black;"
        +"	fill-mode:plain;"
        +"	text-size:15;"
        +"}"
        +""
        +"sprite.long{	"
        +"	padding:6px,19px;"
        +"	text-background-mode: rounded-box; "
        +"	text-background-color:#8facb4;	"
        +"	text-padding: 5px, 2px;"
        +"	text-alignment:under;"
        +"	fill-mode:none;"
        +"}"
        +"	"
        +"edge {"
        +"	arrow-shape: arrow; "
        +"	arrow-size: 15px,7.5px;"
        +"	size:           1.5px;	"
        +"    fill-mode:      plain;"
        +"    fill-color: black;"
        +"	/*changing edge layout here also doesn't work*/"
        +"}"
        +""
        +"sprite.relation{"
        +"	text-padding:2px, 2px;"
        +"	text-alignment:center;"
        +"	text-size:11;"
        +"	text-background-color:white;	"
        +"	text-color:black;"
        +"	text-background-mode:plain;	"
        +"	fill-mode:none;	 "
        +"}"
        +"sprite.world{ "
        +"	text-background-mode: rounded-box; "
        +"	text-padding:5px,2.5px;"
        +"	text-alignment:under;"
        +"	text-background-color: #8facb480;"
        +"	fill-mode:none;"
        +"	text-size:11;"
        +"}";

    public boolean checkEdgeExist(String cad, Graph graph) {
        boolean bool=false;
        Collection<Edge> list = graph.getEdgeSet();
        ArrayList<Edge> newList=new ArrayList<Edge>(list);
        int j=0;
        while(bool==false&&j<newList.size()) {
            String compare=newList.get(j).toString();
            bool=compare.equals(cad);
            j++;
        }

        return bool;
    }


    public  JPanel createGraph(KripkeModel model) throws ParserConfigurationException, TransformerException, IOException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String name;
        WorldSet worldSet= model.getWorldSet();
        RelationSet relationSet = model.getRelSet();
        Relation currentRel;
        String first;
        String second;
        char currentAgent;
        String cad="";
        String finalAtom = "";
        ArrayList<Character> atoms = new ArrayList<Character>();

        Graph graph = new MultiGraph("KripkeModel");
        GraphVisualitation visu = new GraphVisualitation(graph);
        CreateGraph create = new CreateGraph();

        /*Crear los mundos*/
        for(int i= 0;i<worldSet.size();i++) {
            name = worldSet.get(i).getName();
            graph.addNode(name);
            atoms = worldSet.get(i).toArrayList();
            visu.setSimpleName(name, name);
            finalAtom=atoms.toString().replace("[","").replace("]","").replace(",","");
            System.out.println(finalAtom);
            System.out.println(atoms);
            visu.setAtoms(name,finalAtom);
        }

        /*Crear las relaciones*/
        for(int j=0;j<relationSet.size();j++) {
            currentRel=relationSet.get(j);
            currentAgent=currentRel.getAgent();
            first=currentRel.getFirst().getName();
            second=currentRel.getSecond().getName();
            name=first+second;
            String compare=name+"["+first+"->"+second+"]";
            if(create.checkEdgeExist(compare, graph)) {
                visu.setAgent(name, currentAgent);
            }else {
                graph.addEdge(name,first,second,true);
                visu.setAgent(name, currentAgent);	
            }

        }

        graph.addAttribute("ui.stylesheet", style); 
        graph.addAttribute("ui.antialias");

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".
        viewer.enableAutoLayout();

        return viewPanel;

    }

    public JPanel createGraphAtoms(KripkeModel model,ArrayList<RelationalFormula> list) throws ParserConfigurationException, TransformerException, IOException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Traductor t = new Traductor();
        WorldSet worldSet = model.getWorldSet();
        RelationSet relationSet = model.getRelSet();
        // ArrayList<Character> agentSet = model.getAgent();
        Relation currentRel;
        Character currentAgent;
        String cad;
        String first;
        String second;
        String name;

        Graph graph = new MultiGraph("KripkeModel");
        GraphVisualitation visu = new GraphVisualitation(graph);
        CreateGraph create = new CreateGraph();

        for(int i= 0;i<worldSet.size();i++) {
            name = worldSet.get(i).getName();
            graph.addNode(name);
        }

        for(int j=0;j<relationSet.size();j++) {
            currentRel=relationSet.get(j);
            currentAgent=currentRel.getAgent();
            first=currentRel.getFirst().getName();
            second=currentRel.getSecond().getName();
            name=first+second;
            String compare=name+"["+first+"->"+second+"]";
            if(create.checkEdgeExist(compare, graph)) {
                visu.setAgent(name, currentAgent);
            }else {
                graph.addEdge(name,first,second,true);
                visu.setAgent(name, currentAgent);
            }

        }

        String currentWorld;
        Reasoner reasoner = new Reasoner();
        int i=0;
        while(i<worldSet.size()) {
            currentWorld=worldSet.get(i).getName();
            for(int j=0;j<list.size();j++) {
                if(reasoner.WorldReasoner(list.get(j),model, worldSet.get(i))){
                    visu.setAtoms(currentWorld, t.convertNormal(list.get(j).toString()));
                }
            }visu.setSimpleName(currentWorld, currentWorld);
            i++;
        }
        graph.addAttribute("ui.stylesheet", style);
        graph.addAttribute("ui.antialias");

        //COMPLETAR CON BOTONES
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".
        viewer.enableAutoLayout();
        return viewPanel;

    }
}
