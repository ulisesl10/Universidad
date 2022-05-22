package com.ibm.academia.restapi.ruleta.excepciones;

public class NotFoundException extends RuntimeException{


	private static final long serialVersionUID = -4460596829156860352L;

	public NotFoundException(String mensaje) {
		super(mensaje);
	}
	

}