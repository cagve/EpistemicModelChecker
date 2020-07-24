/*
 * CAMBIAR DOS PARAMETROS
 */

package Graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;

import org.graphstream.ui.view.Viewer;

import EpistemicChecker.EpistemicKripkeModel;
import EpistemicChecker.EpistemicReasoner;
import EpistemicChecker.Traductor;
import EpistemicChecker.XMLManager;
import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.ml.syntax.MlBeliefSet;

public class CreateGraph {
	
	public boolean checkEdgeExist(String cad, Graph graph) {
		boolean bool=false;
		Collection<Edge> list = graph.getEdgeSet();
		ArrayList<Edge> newList=new ArrayList<Edge>(list);
		int j=0;
		while(bool==false&&j<newList.size()) {
			String compare=newList.get(j).toString();
			System.out.println("CHECK: "+compare);
			bool=compare.equals(cad);
			j++;
		}
		
		return bool;
	}
	
	
	public  JPanel createGraph(String dir) throws ParserConfigurationException, TransformerException, IOException {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		XMLManager manager = new XMLManager(dir);
		manager.writeXML();
		String name;
		ArrayList<MlBeliefSet> worldSet=manager.getWorldSet();
		ArrayList<String> relsSet = manager.getStringRels();
		String currentRel;
		String currentAgent;
		String cad;
		String first;
		String second;
		String atoms;
		
		Graph graph = new MultiGraph("KripkeModel");
		GraphVisualitation visu = new GraphVisualitation(graph);
	    CreateGraph create = new CreateGraph();
	    
		/*Crear los mundos*/
		for(int i= 0;i<worldSet.size();i++) {
			cad="w"+ String.valueOf(i); //Cadena que recoje el nombre del nodo
			graph.addNode(cad);
			atoms=manager.getAtoms(worldSet.get(i));
			visu.setSimpleName(cad, cad);
			visu.setAtoms(cad,atoms);
		}
		
		/*Crear las relaciones*/
		for(int j=0;j<relsSet.size();j++) {
			currentRel=relsSet.get(j);
			currentAgent=manager.getAgentRel(currentRel);
			first="w"+manager.getFirstTerm(currentRel);
			second="w"+manager.getSecondTerm(currentRel);
			name=first+second;
			String compare=name+"["+first+"->"+second+"]";
			if(create.checkEdgeExist(compare, graph)) {
				visu.setAgent(name, currentAgent);
			}else {
				graph.addEdge(name,first,second,true);
				visu.setAgent(name, currentAgent);	
			}
		
		}
		
		graph.addAttribute("ui.stylesheet", "url('file:resources/graphstyle.css')"); //src/main/java/resources/style.css El directorio para .jar
        graph.addAttribute("ui.antialias");
      
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".	
        viewer.enableAutoLayout();
       
		return viewPanel;
        
	}
	
	public JPanel createGraphAtoms(String dir,ArrayList<RelationalFormula> list) throws ParserConfigurationException, TransformerException, IOException {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		XMLManager manager = new XMLManager(dir);
		manager.writeXML();
		Traductor t = new Traductor();
		
		ArrayList<MlBeliefSet> worldSet=manager.getWorldSet();
		ArrayList<String> relsSet = manager.getStringRels();
		ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> accRelSet = new ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>>();
		ArrayList<Character> agentSet = new ArrayList<Character>();
	
		agentSet=manager.getAgentSet();
		accRelSet=manager.getAccRelSet();
	
		String currentRel;
		String currentAgent;
		String cad;
		String first;
		String second;
		String name;
		Graph graph = new MultiGraph("KripkeModel");
		GraphVisualitation visu = new GraphVisualitation(graph);
		CreateGraph create = new CreateGraph();
		
		for(int i= 0;i<worldSet.size();i++) {
			cad="w"+ String.valueOf(i); //Cadena que recoje el nombre del nodo
			graph.addNode(cad);
		}
		
		for(int j=0;j<relsSet.size();j++) {
			currentRel=relsSet.get(j);
			currentAgent=manager.getAgentRel(currentRel);
			first="w"+manager.getFirstTerm(currentRel);
			second="w"+manager.getSecondTerm(currentRel);
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
		EpistemicReasoner checker = new EpistemicReasoner();
		EpistemicKripkeModel str = new EpistemicKripkeModel(accRelSet,worldSet, agentSet);
		int i=0;
		while(i<worldSet.size()) {
			currentWorld="w"+String.valueOf(i);
			for(int j=0;j<list.size();j++) {
				if(checker.WorldReasoner(list.get(j), str, worldSet.get(i))){
					visu.setAtoms(currentWorld, t.convertNormal(list.get(j).toString()));
				}
			}visu.setSimpleName(currentWorld, currentWorld);
		i++;
		}
		graph.addAttribute("ui.stylesheet", "url('file:style/style.css')"); //.res/style.css Para compilar .jar
        graph.addAttribute("ui.antialias");
        
        //COMPLETAR CON BOTONES
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);   // false indicates "no JFrame".	
        viewer.enableAutoLayout();
        return viewPanel;
        
	}	
	public static void main(String[]args) throws ParserConfigurationException, TransformerException, IOException {
 		CreateGraph g = new CreateGraph();
		g.createGraph("jo");
	}
	
}
