package org.epistemic;

public class ExceptionManager extends Exception{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionManager(String mensajeError){
        super(mensajeError);
    }
}
