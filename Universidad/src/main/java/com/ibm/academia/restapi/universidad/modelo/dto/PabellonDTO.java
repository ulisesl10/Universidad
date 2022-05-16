package com.ibm.academia.restapi.universidad.modelo.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PabellonDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2187159326475597162L;
	private Long pabellonId;
	private String nombre;
	private Double metrosCuadrados;
	private Date fechaCreacion;
	


	
}
