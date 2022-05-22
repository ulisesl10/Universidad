package com.ibm.academia.restapi.ruleta.modelo.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apuesta implements Serializable{

	private static final long serialVersionUID = 9062193436175962306L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(optional = true , fetch = FetchType.LAZY , cascade = { CascadeType.PERSIST , CascadeType.MERGE })
	@JsonIgnoreProperties({"hibernateLazyInitializer", "apuestas"})
	private Ruleta ruleta;
	
	@Column(name = "cantidadApuesta")	
	private Double cantidad;
	
	@Column(name = "numero")
	@Min(value = 0, message = "El numero tiene que ser mayor a 0")
	@Max(value = 36, message = "El número limite es 36")
	private Integer numeroApostado;
	
	@Column(name = "color")
	private String colorApostado;
	
	@Column(name = "estado_apuesta")
	private String estadoApuesta;
	


}
	
