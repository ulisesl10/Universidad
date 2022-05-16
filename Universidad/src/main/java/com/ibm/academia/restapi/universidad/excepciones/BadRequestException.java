package com.ibm.academia.restapi.universidad.excepciones;


public class BadRequestException extends RuntimeException {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6668916681243010155L;

	public BadRequestException(String mensaje) {
		super(mensaje);
	}

}
