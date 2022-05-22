package com.ibm.academia.restapi.ruleta.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ruleta implements Serializable {

	private static final long serialVersionUID = -2535891266606092175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "estado_apuesta",nullable = false)
	private Boolean estadoApuesta;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@OneToMany(mappedBy = "ruleta",fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","ruleta"})
	private List<Apuesta> apuestas;
	
	@PrePersist
	private void beforePersist() {
		fechaCreacion = new Date();
		estadoApuesta = false;
	}
	
	@PreUpdate
	private void beforeUpdate() {
		fechaModificacion = new Date();
	}
	

}
