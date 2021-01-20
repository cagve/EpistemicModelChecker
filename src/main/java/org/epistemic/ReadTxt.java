package org.epistemic; 
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.fol.syntax.FolAtom;

public class ReadTxt {

	 private String archivo;
     private String errorMsg="";
     private String indicator;
     private String atomRegex = "[p-z]";
     private String agentRegex = "[a-d]";
     private String worldRegex = "w[0-9]*";
     private String relationRegex = "<"+worldRegex+","+worldRegex+">";
     private String worldSetRegex = "\\{(("+worldRegex+")+(?:,("+worldRegex+")+)*)\\}";
     private String relationSetRegex = "\\{(("+relationRegex+")+(?:,("+relationRegex+")+)*)\\}";
     private String worldExpRegex = "W="+worldSetRegex;
     private String relationExpRegex = "R"+agentRegex+"="+relationSetRegex;
     private String evaluationExpRegex = "V\\("+atomRegex+"\\)="+worldSetRegex;
     private String inputString;
     private char currentChar;
     private WorldSet worldSet;
     private RelationSet relationSet;
     private World world;
     private ArrayList<Character> charList = new ArrayList<Character>();
     private ArrayList<String> worldList = new ArrayList<String>();
     private String inputLine;


	/**
	 * Constructor
	 * @param archivo Entrada del modelo
	 */
	public ReadTxt(String archivo) {
		 this.archivo=archivo;
	 }

     /**
      * Clase general que se encarga de eliminar los elementos duplicados de un array list.
      * @param list ArratList con elementos a eliminar
      * @return list ArrayList con sin elementos duplicados
      */
     public static ArrayList<String> removeDuplicates(ArrayList<String> list){ 
        //  Crea un conjunto de tipo linkedhash
        Set<String> set = new LinkedHashSet<>(); 
        //  Añade los elemenetos de la lista al conjunto. Elimina en este paso los duplicados
        set.addAll(list); 

        list.clear(); 
        list.addAll(set); 

        return list; 
    }

    /**
     * Devuelve los átomos
     * @return charList ArrayList de átomos
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<Character> getAtoms() throws IOException{
        charList.clear();
        for(int i=0;i<getEvaluationList().size();i++){
            charList.add(getEvaluationList().get(i).charAt(2));
        }
        Set<Character> set = new LinkedHashSet<>(); 
        //  Añade los elemenetos de la lista al conjunto. Elimina en este paso los duplicados
        set.addAll(charList); 
        charList.clear(); 
        charList.addAll(set); 
        return charList;
    }

    /**
     * Devuelve los agentes 
     * @return charList Arraylist de agentes
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<Character> getAgent() throws IOException{
        charList.clear();
        for(int i=0;i<getRelationList().size();i++){
            charList.add(getRelationList().get(i).charAt(1));
        }
        Set<Character> set = new LinkedHashSet<>(); 
        //  Añade los elemenetos de la lista al conjunto. Elimina en este paso los duplicados
        set.addAll(charList); 
        charList.clear(); 
        charList.addAll(set); 
        return charList;
    }

 	/**
 	 * Devuelve la lista de mundos 
 	 * @return worldsList Arraylist que contiene los mundos
 	 * @throws IOException Si no existe el archivo
 	 */
    public ArrayList<String> getWorldList() throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String worlds="";
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.startsWith("W")) {
                worlds=inputLine;
            }
        }
        ArrayList<String> worldsList = new ArrayList<String>();
        int beginBracket=worlds.indexOf('{');
        int endBracket=worlds.indexOf('}');
        worlds=worlds.substring(beginBracket+1, endBracket); 
        String[] field = worlds.split(",");
        for (int i=0; i<field.length;i++) {
            worldsList.add(field[i]);
        }
        b.close();
        return worldsList;
    }
    
    /**
     * Devuelve cada una de las listas de relaciones
     * @return relationsTable  Arraylist que contiene las relaciones 
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<String> getRelationList() throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String relationLine ="";
        ArrayList<String> relationsTable = new ArrayList<String>();
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.startsWith("R")) {
                relationLine=inputLine;
                relationsTable.add(relationLine);
            }
        } 
        b.close();
        return relationsTable;
    }

    /**
     * Devuelve cada una de las listas de relaciones de un agente dado
     * @return relationsTable  Arraylist que contiene las relaciones 
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<String> getRelationListOfAgent(Character agent) throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String relationLine ="";
        ArrayList<String> worldsList = new ArrayList<String>();
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.contains(String.valueOf(agent))) {
                relationLine=inputLine;
            }
        } 
        // Con expresiones regulares extraemos el contenido de las relacioces
        Matcher matcher = Pattern.compile("\\<([^\\>]+)").matcher(relationLine); 
        int pos = -1;
        while (matcher.find(pos+1)){
            pos = matcher.start();
            worldsList.add(matcher.group(1));
        }
        return worldsList;
    }
    

    /**
     * Devuelve la lista de relaciones de un agente
     * @param agent Agente de las relaciones
     * @return relationList ArrayList que contiene la lista de relaciones de un agente
     * @throws IOException Si no existe el archivo
     /
    public ArrayList<String> getRelationListOfAgent(char agent) throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String relationLine ="";
        ArrayList<String> relationList= new ArrayList<String>();
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.contains(String.valueOf(agent))) {
                relationLine=inputLine;
            }
        } 
        int beginBracket=relationLine.indexOf('{');
        int endBracket=relationLine.indexOf('}');
        relationLine=relationLine.substring(beginBracket+1, endBracket); 
        char currentChar;
        for (int i=0; i<relationLine.length();i++) {
            currentChar=relationLine.charAt(i);
            if(currentChar==','){ //Modifica las comas que separan relaciones por ;
                if(relationLine.charAt(i+1)=='<'){
                    relationLine=relationLine.substring(0,i)+";"+relationLine.substring(i+1,relationLine.length());
                }
            }
        }
        String[] field = relationLine.split(";");
        for (int i=0; i<field.length;i++) {
            relationList.add(field[i]);
        }
        b.close();
        return relationList;
    }


    /**
     * Devuelve la lista de evaluaciones
     * @return  evaluationTable Arraylist que contiene la lista de evaluaciones [w0,w1,w2,w3]
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<String> getEvaluationList() throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String evaluationLine ="";
        ArrayList<String> evaluationTable = new ArrayList<String>();
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.startsWith("V")) {
                evaluationLine=inputLine;
                evaluationTable.add(evaluationLine);
            }
        } 
        b.close();
        return evaluationTable;
    }

    /**
     * Devuelve la lista de evaluaciones de un átomo concreto
     * @param atom Átomo
     * @return worldsList Arraylist que contiene los mundos en los que es verdadero el átomo. [w0,w1,w2,w3]
     * @throws IOException Si no existe el archivo
     */
    public ArrayList<String> getEvaluationListOfAtom(char atom) throws IOException{
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String evaluationLine ="";
        while((inputLine = b.readLine())!=null) {
            inputLine=inputLine.replaceAll("\\s",""); //Elimina espacios en blanco
            if(inputLine.contains(String.valueOf(atom))) {
                evaluationLine=inputLine;
            }
        } 
        ArrayList<String> worldsList = new ArrayList<String>();
        worldsList=getWorlds(evaluationLine);
        return worldsList;
    }

    /**
     * Devuelve si la expresión es válida, es decir, que encajen con las expresiones regulares.
     * @param entrada Expresión a evaluar
     * @return boolean value
     */
    public boolean isValid(String entrada) {
        return entrada.matches(worldExpRegex) || entrada.matches(relationExpRegex) || entrada.matches(evaluationExpRegex);
    }
    
    /**
     * Devuelve si la expresión es un conjunto de mundos, relaciones o evaluación.
     * @param entrada Expresión a evaluar 
     * @return entrada
     */
    public String getType(String entrada){
        if(entrada.matches(worldExpRegex)){
            entrada="World";
        }else if(entrada.matches(relationExpRegex)){
            entrada="Relation set";
        }else if(entrada.matches(evaluationExpRegex)){
            entrada="Evaluation set";
        }else{
            entrada="Unknown";
        } 
        return entrada;
    }


    /**
     * Devuelve el indicador de una línea introducida
     * @param entrada Expresión a evaluar
     * @return indicator Indicador de la línea
     */
    public String getIndicator(String entrada){
        int endIndex= entrada.indexOf('=');
        indicator = entrada.substring(0, endIndex);
        return indicator; 
    }

    /**
     * Devuelve únicamente los mundos que componen la entrada
     * @param entrada Expresión sobre la que extraer los mundos
     * @return worldList ArrayList de mundos
     */
    public ArrayList<String> getWorlds(String entrada){
        String cadena="";
        
        for(int i=0;i<entrada.length();i++){
            if(entrada.charAt(i)=='w' || Character.isDigit(entrada.charAt(i))){ //Añade a la cadena los caracteres que forman el mundo
                cadena=cadena+entrada.charAt(i);
            }else if(entrada.charAt(i)==',' || entrada.charAt(i)=='}' && Character.isDigit(entrada.charAt(i-1))){ //Si es una coma o un paréntesis cerrado y el carácter anterior es un dígito. Añade la cadena al array y la limpia para el siguiente paso del bucle
                worldList.add(cadena);
                cadena="";
            }
        } 

        return removeDuplicates(worldList);
    } 
    
    /**
     * Combrueba si un conjunto es subconjunto de otro o no.
     * @param mainSet Conjunto primero
     * @param subset Posible subconjunto
     * @return flag True si es subconjunto o False si no lo es
     * @throws IOException Si no existe el archivo
     */
    public boolean subSet(ArrayList<String> mainSet, ArrayList<String> subset) throws IOException{
        worldList = getWorldList();
        boolean flag=true;
        for(int i=0;i<subset.size();i++){
            if(!mainSet.contains(subset.get(i))){
                flag=false;
                break; 
            }
        } 
        return flag;
    }

    
    /**
     * Comprueba si el archivo de texto sintácticamente correcto
     * @return Cadena vacía si es válido
     * @throws FileNotFoundException Si no existe el archivo
     * @throws IOException Si no existe el archivo
     */
    public String parsingFile() throws FileNotFoundException, IOException{
        FileReader f = new FileReader(archivo);
        String returnString="";
        BufferedReader b = new BufferedReader(f);
        ArrayList<String> arrayIndicadores = new ArrayList<String>(); //CAMBIAR POR ARRAY
        int line=1;
        int initialBracket;
        int finalBracket;
        boolean flag=true;
        while((inputString = b.readLine())!=null) {

            //Ignorar lineas en blanco
            if(inputString.isBlank()){ 
                line++;
                continue;
            }

            //Elimina espacios en blanco 
            inputString=inputString.replaceAll("\\s","");

            //Si el conjunto es vacío debemos ignorarlo
            initialBracket=inputString.indexOf('{');
            finalBracket=inputString.indexOf('}');
            if(initialBracket==finalBracket-1){
                line++;
                continue;
            }

            //Condición para que no haya duplicados
            if(!arrayIndicadores.contains(getIndicator(inputString))){ 
                arrayIndicadores.add(getIndicator(inputString));
            }else{
                errorMsg="Check line "+line+"; Duplicate entry"; 
                flag=false;
                break;
            }

            //Condición para que no haya errores sintácticos
            if(!isValid(inputString)){
                errorMsg="Check line "+line+"; Syntax error"; 
                flag=false;
                break; 
            }

            //Condición para que los mundos de las relaciones formen un subconjunto de W
            if(getType(inputString).equals("Relation set")){
                if(!subSet(getWorldList(), getWorlds(inputString))){
                    errorMsg="Check line "+line+ "; world not found in W";
                    flag=false;
                    break;
                }
            }

            //Condición para que los mundos del conjunto de evaluación sea subconjunto de W
            if(getType(inputString).equals("Evaluation set")){
                if(!subSet(getWorldList(), getWorlds(inputString))){ 
                    errorMsg="Check line "+line+ "; world not found in W";
                    flag=false;
                    break;
                } 
            }

            line++;
        }
        b.close();

        //Muestra los mensaje
        if(!flag){
            returnString="Error in model file. " + errorMsg;
        }
        return returnString;
    }

    /**
     * Se encarga de construir el conjunto de mundos
     * @return worldSet Conjunto de mundos
     * @throws IOException Si no existe el archivo
     */
    public WorldSet buildWorldSet() throws IOException{
        String worldName;
        FolAtom atom = new FolAtom();
        ArrayList<String> evaluationWorldList = new ArrayList<String>();
        worldSet = new WorldSet();
        for(int i=0;i<getWorldList().size();i++){ //Recorre la lista de mundos
            worldName=getWorldList().get(i); 
            world=new World(worldName);
            worldSet.add(world);
            } 
        for(int j=0; j<getAtoms().size();j++){ //recorre la lista de átomos
            currentChar=getAtoms().get(j);
            atom = new FolAtom(new Predicate(String.valueOf(currentChar)));
            evaluationWorldList.clear();
            evaluationWorldList = getEvaluationListOfAtom(currentChar);
                for(int k = 0; k<evaluationWorldList.size();k++){
                    worldSet.getWorldByString(evaluationWorldList.get(k)).add(atom);
                } 
           }
        return worldSet; 
    }

    /**
     * Se encarga de construir las relaciones 
     * @return relationSet Conjunto de relaciones
     * @throws IOException Si no existe el archivo
     */
    public RelationSet buildRelationSet() throws IOException{
        char currentAgent;
        Relation currentRelation;
        ArrayList<String> currentRelationList = new ArrayList<String>();
        relationSet = new RelationSet();
        worldSet = buildWorldSet();
        for(int i=0;i<getAgent().size();i++){ //Recorre cada agente
            currentAgent=getAgent().get(i); 
            currentRelationList = getRelationListOfAgent(currentAgent);
            for(int j=0;j<currentRelationList.size();j++){
                String[] field = currentRelationList.get(j).split(",");
                currentRelation = new Relation(worldSet.getWorldByString(field[0]), worldSet.getWorldByString(field[1]), currentAgent);
                relationSet.add(currentRelation);
            } 
        } 
        return relationSet;
    }
}
