package com.ibm.academia.restapi.universidad.excepciones;

public class NotFoundException extends RuntimeException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5114579296038571480L;

	public NotFoundException(String mensaje) {
		super(mensaje);
	}

}
