package org.epistemic;

public class ExceptionManager extends Exception{
    private String codigoError;
    private String mensajeError;

    public ExceptionManager(String codigoError){
        super();
        this.codigoError=codigoError;
    }
    
    public void showError(){
        System.out.println(codigoError);
    }



}
