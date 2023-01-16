/* CAMBIAR DOS PARAMETROS
 */

package org.graph;

import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import org.epistemic.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
// import org.graphstream.ui.swingViewer.ViewPanel;
// import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CreateGraph {

	public boolean checkEdgeExist(String cad, Graph graph) {
		boolean bool=false;
		Collection<Edge> list = graph.getEdgeSet();
		ArrayList<Edge> newList=new ArrayList<Edge>(list);
        // System.out.println(newList);
		int j=0;
		while(bool==false&&j<newList.size()) {
			String compare=newList.get(j).toString();
			bool=compare.equals(cad);
			j++;
		}

		return bool;
	}


	public  JPanel createGraph(EpistemicModel model) throws ParserConfigurationException, TransformerException, IOException {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		String name;
		WorldSet worldSet=model.getWorldSet();
		RelationSet relationSet = model.getRelationSet();
		Relation currentRel;
		String first;
		String second;
		char currentAgent;
		String cad="";
		String finalAtom = "";
		ArrayList<Character> atoms = new ArrayList<Character>();

		Graph graph = new MultiGraph("KripkeModel");
		GraphVisualitation visu = new GraphVisualitation(graph);

		/*Crear los mundos*/
		for(int i= 0;i<worldSet.getWorldSet().size();i++) {
			cad="w"+ String.valueOf(i); //Cadena que recoje el nombre del nodo
			graph.addNode(cad);
			atoms=model.getAtomOf(worldSet.getWorldSet().get(i));
			visu.setSimpleName(cad, cad);
			finalAtom=atoms.toString().replace("[","").replace("]","").replace(",","");
			visu.setAtoms(cad,finalAtom);
		}
		
		/*Crear las relaciones*/
		for(int j=0;j<relationSet.getRelationSet().size();j++) {
			currentRel=relationSet.getRelationSet().get(j);
			currentAgent=currentRel.getAgent();
			first=currentRel.getFirst().getName();
			second=currentRel.getSecond().getName();
			name=first+second;
            // String compare="[edge "+name+" ("+first+" -> "+second+")]"; VERSION 1
            String compare=name+"["+first+"->"+second+"]"; // VERSION 1.3
			if(this.checkEdgeExist(compare, graph)) {
				visu.setAgent(name, currentAgent);
			}else {
				graph.addEdge(name,first,second,true);
				visu.setAgent(name, currentAgent);	
			}
		
		}
		
		// graph.addAttribute("ui.stylesheet", "url('file:resources/graphstyle.css')"); //src/main/java/resources/style.css El directorio para .jar
		// graph.addAttribute("ui.stylesheet", "url('file:/home/karu/EMC/resources/graphstyle.css')"); //.res/style.css Para compilar .jar
		graph.addAttribute("ui.stylesheet", "url('file:graphstyle.css')"); //.res/style.css Para compilar .jar
        graph.addAttribute("ui.antialias");

        //OLD
        // Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        // ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".
        //NEW 
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".
        viewer.enableAutoLayout();
       
		return viewPanel;
        
	}
	
	public JPanel createGraphAtoms(EpistemicModel model,ArrayList<RelationalFormula> list) throws ParserConfigurationException, TransformerException, IOException {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Traductor t = new Traductor();
		WorldSet worldSet = model.getWorldSet();
        Reasoner reasoner = new Reasoner();
		RelationSet relationSet = model.getRelationSet();
		ArrayList<Character> agentSet = model.getAgentList();
		Relation currentRel;
		Character currentAgent;
		String cad;
		String first;
		String second;
		String name;


		Graph graph = new MultiGraph("KripkeModel");
		GraphVisualitation visu = new GraphVisualitation(graph);

		for(int i= 0;i<worldSet.getWorldSet().size();i++) {
			cad="w"+ String.valueOf(i); //Cadena que recoje el nombre del nodo
			graph.addNode(cad);
		}

		for(int j=0;j<relationSet.getRelationSet().size();j++) {
			currentRel=relationSet.getRelationSet().get(j);
			currentAgent=currentRel.getAgent();
			first=currentRel.getFirst().getName();
			second=currentRel.getSecond().getName();
			name=first+second;
            // String compare="[edge "+name+" ("+first+" -> "+second+")]"; VERSION 1
            String compare=name+"["+first+"->"+second+"]"; // VERSION 1.3
			if(this.checkEdgeExist(compare, graph)) {
				visu.setAgent(name, currentAgent);
			}else {
				graph.addEdge(name,first,second,true);
				visu.setAgent(name, currentAgent);
			}

		}

		String currentWorld;
		int i=0;
		while(i<worldSet.getWorldSet().size()) {
            String formulasToAdd = "";
			currentWorld="w"+String.valueOf(i);
			for(int j=0;j<list.size();j++) {
                reasoner.ModelReasoner(list.get(j), model);
                System.out.println("===="+list.get(j));
                World newWorld = model.getWorldByName(currentWorld);
                System.out.println(newWorld.getFormulaList());
                // System.out.println(currentWorld+reasoner.WorldReasoner(list.get(j),model,model.getWorldSet().getWorldSet().get(i)));
                if (model.getWorldSet().getWorldSet().get(i).containsTrueFormula(list.get(j))){
                    if (formulasToAdd.equals("")){
                        formulasToAdd = t.convertNormal(list.get(j).toString());
                    }else{
                        formulasToAdd = formulasToAdd + ", "+t.convertNormal(list.get(j).toString());
                    }
				}
			}
            visu.setSimpleName(currentWorld, currentWorld);
            visu.setAtoms(currentWorld,formulasToAdd);
            i++;
		}
		// graph.addAttribute("ui.stylesheet", "url('file:resources/graphstyle.css')"); //.res/style.css Para compilar .jar
		// graph.addAttribute("ui.stylesheet", "url('file:/home/karu/EMC/resources/graphstyle.css')"); //.res/style.css Para compilar .jar
		graph.addAttribute("ui.stylesheet", "url('file:graphstyle.css')"); //.res/style.css Para compilar .jar
        graph.addAttribute("ui.antialias");

        //COMPLETAR CON BOTONES
        //OLD
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);   // false indicates "no JFrame".
        // NEW
        // Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        // View viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".
        viewer.enableAutoLayout();
        return viewPanel;

	}
}
