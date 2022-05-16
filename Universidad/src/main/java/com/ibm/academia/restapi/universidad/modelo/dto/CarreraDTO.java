package com.ibm.academia.restapi.universidad.modelo.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarreraDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1434956835503150508L;
	private Long carreraId;
	private String nombre;
	private Integer cantidadMaterias;
	private Integer cantidadAnios;
	private Date fechaCreacion;
	

}
