package org.epistemic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParserManager{
    // ES IMPORTANTE QUE AL LEER EL ARCHIVO SE BORREN TODOS LOS ESPACIOS EN BLANCO
    private String atomRegex = "[p-z]";
    private String agentRegex = "[a-d]";
    private String worldRegex = "w[0-9]+";
    private String relationRegex = "<"+worldRegex+","+worldRegex+">";
    private String worldSetRegex = "\\{(("+worldRegex+")+(?:,("+worldRegex+")+)*)\\}";
    private String relationSetRegex = "\\{(("+relationRegex+")+(?:,("+relationRegex+")+)*)\\}";
    private String worldExpRegex = "W="+worldSetRegex;
    private String relationExpRegex = "R"+agentRegex+"="+relationSetRegex;
    private String evaluationExpRegex = "V\\("+atomRegex+"\\)="+worldSetRegex;

    public boolean isWorldExp(String entrada){
        return entrada.matches(worldExpRegex);
    }

    public boolean isRelationExp(String entrada){
        return entrada.matches(relationExpRegex);
    }

    public boolean isEvaluationExp(String entrada){
        return entrada.matches(evaluationExpRegex);
    }

    public boolean isValid(String entrada){
        return isWorldExp(entrada) || isRelationExp(entrada) || isEvaluationExp(entrada);
    }

    public String getType(String entrada){
        if(entrada.matches(worldExpRegex)){
            entrada="Es de tipo world";
        }else if(entrada.matches(relationExpRegex)){
            entrada="Es de tipo relation";
        }else if(entrada.matches(evaluationExpRegex)){
            entrada="Es de tipo evaluadora";
        }else{
            entrada="Error al introducir fórmula";
        } 
        return entrada;
    }
}
