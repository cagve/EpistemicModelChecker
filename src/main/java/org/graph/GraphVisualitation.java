package org.graph;

import org.graphstream.graph.*;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;


/**
 * Clase encargada de lo relativo a complementar el grafo. Implementa las clases de la librería graphstream
 * @see "http://graphstream-project.org/"
 * @version 3.01
 * @author Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)
 * @date 14/07/2020
 */
public class GraphVisualitation {

    private Graph graph;
    private CreateGraph graphCreate;

    /**
     * Constructor
     * @param graph
     */
    public GraphVisualitation(Graph graph) {
        this.graph=graph;
    }

    /**
     * Segundo constructor
     * @param graphCreate
     */
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

    /**
     * Establece el nombre de un mundo. w0,w1,w2, etc.
     * @param node mundo a nombrar
     * @param name nombre del mundo
     */
    public void setSimpleName(String node, String name) {
        // System.out.println("Set simple name "+node+" "+name);
        SpriteManager sman = new SpriteManager(graph);
        Sprite a = sman.addSprite(node);
        
        // FIX ERROR 2
        // a.addAttribute("ui.class", "world");
        // a.addAttribute("ui.label", name);
        a.setAttribute("ui.class", "world");
        a.setAttribute("ui.style", "text-background-mode: rounded-box; text-padding:5px,2.5px; text-alignment:under; text-background-color: #8facb480; fill-mode:none; text-size:11;");
        a.setAttribute("ui.label", name);
        a.attachToNode(node);
        a.setPosition(Units.PX, -20, 180, 90);
    }

    /**
     * Establece el agente de la relacion, a,b,c o d.
     * @param edge relacion a etiquetar
     * @param agent agente que se etiqueta
     */
    public void setAgent(String edge, char agent) {
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

    /**
     *  Etiqueta cada mundo con  los átomos correspondientes de la fórmula de evaluación
     * @param node Mundo a etiquetar
     * @param atoms Atomos de la función de evaluación
     */
    public void setAtoms(String node, String atoms) {
        Node e1=graph.getNode(node);
        e1.addAttribute("ui.label", atoms);

    }
}
