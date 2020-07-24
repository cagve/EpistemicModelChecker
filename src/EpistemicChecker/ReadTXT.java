/*
 * Function that reads models:
 * ------------------------------
 * EXAMPLE:  
 * W={w0,w1,w2}
 * Ra={<w0,w1>}
 * Rb={<w1,w0>}
 * V(p)={w0}
 * V(q)={w1}
 */


package EpistemicChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

public class ReadTXT {
	
	 private  String archivo;
	 private  String cad;

	public ReadTXT(String archivo) {
		 this.archivo=archivo;
	 }
	
    public void muestraContenido() throws FileNotFoundException, IOException {
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);  
        while((cad = b.readLine())!=null) {
            System.out.println(cad);
        }
        b.close();
    }
    
    public String  getWorldString() throws IOException {
    	 FileReader f = new FileReader(archivo);
         BufferedReader b = new BufferedReader(f);
         String worlds="";
    	while((cad = b.readLine())!=null) {
    		if(cad.startsWith("W")) {
            worlds=cad;
    		}
    	}
		return worlds;
    }
    
    public ArrayList<String> getWorldList() throws IOException{
    	ArrayList<String> worlds = new ArrayList<String>();
    	ReadTXT read = new ReadTXT(archivo);
    	cad=read.getWorldString();
    	String cad2="";
    	int posFin;
    	int posFinal;
    	for (int i=0; i<cad.length();i++) {
    		posFin=i+1;
    		posFinal=i+2;
    		if(cad.charAt(i)=='w'&&cad.charAt(posFinal)!=','&&cad.charAt(posFinal)!='}') {//Si tiene dos n�meros w10 w11 w12
       			cad2=String.valueOf(cad.charAt(posFin));
    			cad2=cad2.concat(String.valueOf(cad.charAt(posFinal)));
    			worlds.add("w".concat(cad2));
    		}else if(cad.charAt(i)=='w') { //Si tiene un �nico n�mero 
    			cad2=String.valueOf(cad.charAt(posFin));
    			worlds.add("w".concat(cad2));
    		}
    	}
    	
    	return worlds;
    }

    public  ArrayList<String>  getRelationString() throws IOException {
   	 	FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        ArrayList<String> relations = new ArrayList<String>();
        String current;
        while((cad = b.readLine())!=null) {
        	if(cad.startsWith("R")) {
        		relations.add(cad);
        	}
        }
        
        return relations;
   }
    
    public ArrayList<String> getRelationList() throws IOException{
	   	ArrayList<String> relations = new ArrayList<String>();
	   	ArrayList<String> relationsList = new ArrayList<String>();
	   	String current;
	   	int pos;
	   	int posFinal;
	   	String newCad = null;
	   	String agent;
	   	
	   	relations=this.getRelationString();
	   	
	   	for(int i=0; i<relations.size();i++) {
	   	current=relations.get(i);
	   	agent=String.valueOf(current.charAt(1));
	   	
	   		for(int j=0; j<current.length();j++) {
	   			if(current.charAt(j)=='<') {
	   				pos=j;
	   				while(current.charAt(pos)!='>') {
	   					pos++;
	   				}
	   				newCad="<"+agent+","+current.substring(j+1, pos+1);
	   				relationsList.add(newCad);
	   			}
	   		}
	   	}
	   	return relationsList;
    }

    public ArrayList<String> getAgentList() throws FileNotFoundException{
    	Scanner entrada = null;
         String linea;
         int numeroDeLinea = 1;
         boolean contiene = false;
         Scanner sc = new Scanner(System.in);
         ArrayList<String> agentList = new ArrayList<String>();
         File f = new File(archivo);
         entrada = new Scanner(f);
         
         while (entrada.hasNext()) { //mientras no se llegue al final del fichero
             linea = entrada.nextLine();  //se lee una l�nea
             if (linea.contains("a")) {   //si la l�nea contiene el texto buscado se muestra por pantalla
                agentList.add("a");
             } else if (linea.contains("b")) {   //si la l�nea contiene el texto buscado se muestra por pantalla
                 agentList.add("b");
              } else if (linea.contains("c")) {   //si la l�nea contiene el texto buscado se muestra por pantalla
                  agentList.add("c");
              } else if (linea.contains("d")) {   //si la l�nea contiene el texto buscado se muestra por pantalla
                  agentList.add("d");
              } else 
             numeroDeLinea++; //se incrementa el contador de l�neas
         }
    	return agentList;
    }

    public ArrayList<String> getEvaluationString() throws IOException{
 	 	FileReader f = new FileReader(archivo);
    	BufferedReader b = new BufferedReader(f);
        ArrayList<String> eva = new ArrayList<String>();
        while((cad = b.readLine())!=null) {
        	if(cad.startsWith("V")) {
        		eva.add(cad);
           	}
        }
   		return eva;
      }
    
    public ArrayList<String> getEvaluationList(String atom) throws IOException{
    	ArrayList<String> worldList = new ArrayList<String>();
    	ArrayList<String> returnList = new ArrayList<String>();
    	worldList=getEvaluationString();
    	
    	String current;
    	for(int i=0;i<worldList.size();i++) {
    		current=worldList.get(i);
    		if(current.contains(atom)) {
    			for (int j=0; j<current.length();j++) {
    	    		int posFin = j+1;
    	    		int posFinal = j+2;
    	    		String cad2;
					if(current.charAt(j)=='w'&&current.charAt(posFinal)!=','&&current.charAt(posFinal)!='}') {//Si tiene dos n�meros w10 w11 w12
    	       			cad2=String.valueOf(current.charAt(posFin));
    	    			cad2=cad2.concat(String.valueOf(current.charAt(posFinal)));
    	    			returnList.add("w".concat(cad2));
    	    		}else if(current.charAt(j)=='w') { //Si tiene un �nico n�mero 
    	    			cad2=String.valueOf(current.charAt(posFin));
    	    			returnList.add("w".concat(cad2));
    	    		}
    	    	}
    		}
    	}
    return returnList;
    }

    public  ArrayList<String> getAtoms() throws IOException{
    	ArrayList<String> atomList = new ArrayList<String>();
    	ArrayList<String> returnList = new ArrayList<String>();
    	atomList=getEvaluationString();
    	
    	for(int i=0;i<atomList.size();i++) {
    		String current = atomList.get(i);
    		returnList.add(String.valueOf(current.charAt(2)));
    	}
    	return returnList;
    }
 
    
    
  
   
}