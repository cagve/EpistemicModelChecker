/* Visualiza el grafo:
 * setSimpleName -> Nombra a cada mundo
 * setAgent -> Establece a cada relación el agente.
 * getRelations -> return the relations of all agent
 * getWorld -> return the worlds's list
 */

/**
 * @version 1.010320
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 01/03/2020 
 */

package Graphs;

import org.graphstream.graph.*;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;


public class GraphVisualitation {
		
	private Graph graph;
	private CreateGraph graphCreate;

	
	
	public GraphVisualitation(Graph graph) {
			this.graph=graph;
	}
	
	public GraphVisualitation(CreateGraph graphCreate) {
		this.graphCreate=graphCreate;
	}
		
	/*Comentado establece el nombre de cada mundo*/
	/*public void setName(String node, String name, int size) {
		SpriteManager sman = new SpriteManager(graph);
    	Sprite a = sman.addSprite(node);
    	a.addAttribute("ui.class", "world");
    	a.addAttribute("ui.label", name);
    	a.attachToNode(node);
    	a.setPosition(Units.PX, -(size/2), 180, 90);   	
	}*/
	
	/*Establece el nombre de cada mundo*/
	public void setSimpleName(String node, String name) {
		SpriteManager sman = new SpriteManager(graph);
    	Sprite a = sman.addSprite(node);
    	a.addAttribute("ui.class", "world");
    	a.addAttribute("ui.label", name);  
    	a.attachToNode(node);
    	a.setPosition(Units.PX, -20, 180, 90);   	
	}
	
	/*Establece a las relaciones el nombre del agente*/
	public void setAgent(String edge, String agent) {
		SpriteManager sman = new SpriteManager(graph);
		Sprite r1 = sman.addSprite(edge);
		String cadAgent="";
		if(r1.getAttribute("ui.label")==null) {
			//NO HACE NADA
		}else {
		 cadAgent = r1.getAttribute("ui.label")+",";
		}
		String finalCad = cadAgent+agent;
		r1.attachToEdge(edge);
    	r1.addAttribute("ui.class", "relation");
    	r1.addAttribute("ui.label", finalCad);
    	r1.setPosition(0.5);
	}
	
	/*Establece a cada mundo el conjunto de átomos que es verdadero en el mundo*/	
	public void setAtoms(String node, String atoms) {
		Node e1=graph.getNode(node);
		e1.addAttribute("ui.label", atoms);
    	
	}
}
