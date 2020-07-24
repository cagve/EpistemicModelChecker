package EpistemicChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.ml.parser.MlParser;
import net.sf.tweety.logics.ml.syntax.MlBeliefSet;

public class XMLManager {
	String dirTXT;
	String dirXML="XMLModel.xml"; //home/carlos/Documentos/Universidad/TFG/JavaProject/EMCv(2.01)/Ejecutable/modelo.xml"; 
	ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> accRelSet = new ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>>();
	ArrayList<MlBeliefSet> worldSet = new ArrayList<MlBeliefSet>();
	ArrayList<Pair<MlBeliefSet,MlBeliefSet>> accRelSetA = new ArrayList<Pair<MlBeliefSet,MlBeliefSet>>();
	ArrayList<Pair<MlBeliefSet,MlBeliefSet>> accRelSetB = new ArrayList<Pair<MlBeliefSet,MlBeliefSet>>();
	ArrayList<Pair<MlBeliefSet,MlBeliefSet>> accRelSetC = new ArrayList<Pair<MlBeliefSet,MlBeliefSet>>();
	ArrayList<Pair<MlBeliefSet,MlBeliefSet>> accRelSetD = new ArrayList<Pair<MlBeliefSet,MlBeliefSet>>();
	ArrayList<Character> atoms = new ArrayList<Character>();
	ArrayList<Character> agentsSet = new ArrayList<Character>();
	ArrayList<String> stringSetPair = new ArrayList<String>();
	int first=0;
	int second;
	String agent;
	String cad="";
	char cha;
	
	public XMLManager(String dirTXT) {
		this.dirTXT=dirTXT;
	}
	
	public 	ArrayList<ArrayList<Pair<MlBeliefSet,MlBeliefSet>>> getAccRelSet(){
	    try {
	    //CREAR PARSER 
	 	
		File fXmlFile = new File(dirXML);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
				
		doc.getDocumentElement().normalize();
		NodeList rList = doc.getElementsByTagName("accRel");
		worldSet=this.getWorldSet();
		
		
		for (int i = 0; i < rList.getLength(); i++) {
		    // Cojo el nodo actual
		    Node nodo = rList.item(i);
		    // Compruebo si el nodo es un elemento
		    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
	             Element element = (Element) nodo;
	             first=Integer.parseInt(element.getElementsByTagName("first").item(0).getTextContent());
	             second=Integer.parseInt(element.getElementsByTagName("second").item(0).getTextContent());
	             
	             if(element.getElementsByTagName("agent").item(0).getTextContent().contains("a")) {
	            	 accRelSetA.add(new Pair<MlBeliefSet,MlBeliefSet>(worldSet.get(first),worldSet.get(second)));
	             }else  if(element.getElementsByTagName("agent").item(0).getTextContent().contains("b")) {
	            	 accRelSetB.add(new Pair<MlBeliefSet,MlBeliefSet>(worldSet.get(first),worldSet.get(second)));
	             } else  if(element.getElementsByTagName("agent").item(0).getTextContent().contains("c")) {
	            	 accRelSetC.add(new Pair<MlBeliefSet,MlBeliefSet>(worldSet.get(first),worldSet.get(second)));
	             }else  if(element.getElementsByTagName("agent").item(0).getTextContent().contains("d")) {
	            	 accRelSetD.add(new Pair<MlBeliefSet,MlBeliefSet>(worldSet.get(first),worldSet.get(second)));
	             }
			 }
		   }
		accRelSet.add(accRelSetA);
		accRelSet.add(accRelSetB);
		accRelSet.add(accRelSetC);
		accRelSet.add(accRelSetD);
		
		return accRelSet;
	    }catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
	    
	}
	
	public 	ArrayList<MlBeliefSet> getWorldSet(){

	    try {
	    //CREAR PARSER 
	 	MlParser parser = new MlParser();
	 	FolSignature sig = new FolSignature();
	 	sig.add(new Predicate("p",0));
	 	sig.add(new Predicate("q",0));
	 	sig.add(new Predicate("r",0));			
	 	parser.setSignature(sig);
	 			
		File fXmlFile = new File(dirXML);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList wList = doc.getElementsByTagName("world");
		
		Builder builder = new Builder();
		worldSet = builder.BuildWorld(wList.getLength());
		int i;
		
		NodeList atomsList;
		for (int u = 0; u < wList.getLength(); u++) {
			Node nNode = wList.item(u);
			atomsList = nNode.getChildNodes();
			for(i=0; i<atomsList.getLength();i++) {
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 worldSet.get(u).add(parser.parseFormula(atomsList.item(i).getTextContent()));
				}
			}	
		}		
		return worldSet;
	    }
	    catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
	    
		
	}

	public ArrayList<Character> getAgentSet(){	
		agentsSet.add('a');
		agentsSet.add('b');
		agentsSet.add('c');
		agentsSet.add('d');
		
		
		return agentsSet;
		
	}
	
	public ArrayList<String> getStringRels() {
		  try {	   	
				File fXmlFile = new File(dirXML);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				
				NodeList rList = doc.getElementsByTagName("accRel");
				for (int i = 0; i < rList.getLength(); i++) {
				    Node nodo = rList.item(i);
				    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
			            Element element = (Element) nodo;
			            agent=element.getElementsByTagName("agent").item(0).getTextContent();
			            first=Integer.parseInt(element.getElementsByTagName("first").item(0).getTextContent());
				        second=Integer.parseInt(element.getElementsByTagName("second").item(0).getTextContent());
				        cad="<"+agent+","+first+","+second+">";
				        stringSetPair.add(cad);
				    }
				}
				
				return stringSetPair;
				
			    }catch (Exception e) {
				e.printStackTrace();
				return null;
			    }
		
			
	}

	public  String convertString(String cad) { //Convierte cadena en un formato legible para el programa +3-1
		String result = "+";
		
		for(int i =0; i<cad.length();i++) {
			if(cad.charAt(i)>='0'&&cad.charAt(i)<='9') {
				result = result+cad.charAt(i)+"-";
			}
		}
		if(result.charAt(result.length()-1)=='-') {//Borrar el �ltimo guion
			return result.substring(0,result.length()-1);
		}else {
			return null;
		}	
	}
	
	public String getFirstTerm(String cad) {//PRIMER MUNDO DE LA RELACION
		if(cad.charAt(0)=='<') {
			cad=this.convertString(cad);
		}
		
		int posIni = cad.indexOf('+');
		int posFin = cad.indexOf('-');
		String result;
		
		result=cad.substring(posIni+1,posFin);		
		return result;
	}
	
	public String getSecondTerm(String cad) {//SEGUNDO MUNDO DE LA RELACION
		if(cad.charAt(0)=='<') {
			cad=this.convertString(cad);
		}
		int posIni = cad.indexOf('-');
		String result;
		
		result=cad.substring(posIni+1);		
		return result;
	}
	
	public String getAtoms(MlBeliefSet world) {
		cad = world.getMinimalSignature().toString();
		String result="";
		for(int i=0;i<cad.length();i++) {
			if(cad.charAt(i)>='p'&& cad.charAt(i)<='z') {
				result=result+cad.charAt(i)+" ";
			}
		}
		
		return result;
	}
	
	public String getAgentRel(String cad) {
		String result= String.valueOf(cad.charAt(1));
		return result;
	}
	
	public void writeXML() throws ParserConfigurationException, TransformerException, IOException {
		ArrayList <String> worldList = new ArrayList<String>();
		ArrayList <String> agentList = new ArrayList<String>();
		ArrayList <String> relList = new ArrayList<String>();
		ArrayList <String> atomsList= new ArrayList<String>();
		String current;
		String currentAgent;
	
		XMLManager r= new XMLManager(dirXML);
		ReadTXT read = new ReadTXT(dirTXT);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();
	
		//MODELO
		Element modelElement = xmlDoc.createElement("model");
		xmlDoc.appendChild(modelElement);
		
		//MUNDOS
		Element worldsElement = xmlDoc.createElement("worlds");
		modelElement.appendChild(worldsElement);
		
		worldList= read.getWorldList();
		for(int i=0; i<worldList.size();i++) {
			Element worldElement = xmlDoc.createElement("world");
			worldElement.setAttribute("id", worldList.get(i).toString());
			worldsElement.appendChild(worldElement);

			int atomInt = 0;
			atomsList=read.getAtoms();
			while(atomInt<atomsList.size()) {
				if(read.getEvaluationList(atomsList.get(atomInt)).contains( worldList.get(i).toString())){
					System.out.println(worldList.get(i));
					System.out.println(atomsList.get(atomInt));
					Element atomsElement = xmlDoc.createElement("atoms");
 					//printAtoms = printAtoms + "," + atomsList.get(atomInt);
					worldElement.appendChild(atomsElement);
					atomsElement.setTextContent(atomsList.get(atomInt));
				}				
				atomInt++;
			}
		}
		
	
			
		
		//AGENTES
		Element agentsElement = xmlDoc.createElement("agents");
		modelElement.appendChild(agentsElement);
		
		agentList=read.getAgentList();
		for(int j=0;j<agentList.size();j++) {
		Element aElement = xmlDoc.createElement("a");
		agentsElement.appendChild(aElement);
		aElement.setTextContent(agentList.get(j).toString());		
		}
		

		//RELACIONES
		Element relsElement = xmlDoc.createElement("rels");
		modelElement.appendChild(relsElement);

		relList = read.getRelationList();
		for(int w=0; w<relList.size();w++) {
			Element accRelElement = xmlDoc.createElement("accRel");
			relsElement.appendChild(accRelElement);
		
			current=relList.get(w);
			
			currentAgent=String.valueOf(current.charAt(1));
			Element agentElement = xmlDoc.createElement("agent");
			accRelElement.appendChild(agentElement);
			agentElement.setTextContent(String.valueOf(currentAgent));
		
			
			Element firstElement = xmlDoc.createElement("first");
			accRelElement.appendChild(firstElement);
			firstElement.setTextContent(r.getFirstTerm(current));
		
			Element secondElement = xmlDoc.createElement("second");
			accRelElement.appendChild(secondElement);	
			secondElement.setTextContent(r.getSecondTerm(current));
		}
				
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult streamResult = new StreamResult(new File(dirXML));
		transformer.transform(source, streamResult);
		System.out.println("Changed");
		
	}

	
}
