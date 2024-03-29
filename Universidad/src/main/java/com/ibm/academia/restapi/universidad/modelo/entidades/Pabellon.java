package com.ibm.academia.restapi.universidad.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "pabellones", schema = "universidad")
public class Pabellon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6752307806319258226L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "No puede ser nulo")
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "metros_cuadrados")
	private Double metrosCuadrados;

	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacío")
	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;

	@NotNull(message = "No puede ser nulo")
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "codigoPostal", column = @Column(name = "codigo_postal")),
			@AttributeOverride(name = "departamento", column = @Column(name = "departamento")) })
	private Direccion direccion;

	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacío")
	@Column(name = "usuario_creacion", nullable = false)
	private String usuarioCreacion;

	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;

	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@OneToMany(mappedBy = "pabellon", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"pabellon"})
	private Set<Aula> aulas;
	


	public Pabellon(Long id, Double metrosCuadrados, String nombre, Direccion direccion, String usuarioCreacion) {
		this.id = id;
		this.metrosCuadrados = metrosCuadrados;
		this.nombre = nombre;
		this.direccion = direccion;
		this.usuarioCreacion = usuarioCreacion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pabellon [id=");
		builder.append(id);
		builder.append(", metrosCuadrados=");
		builder.append(metrosCuadrados);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", direccion=");
		builder.append(direccion);
		builder.append(", usuarioCreacion=");
		builder.append(usuarioCreacion);
		builder.append(", fechaCreacion=");
		builder.append(fechaCreacion);
		builder.append(", fechaModificacion=");
		builder.append(fechaModificacion);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Pabellon))
			return false;
		Pabellon other = (Pabellon) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}

	@PrePersist
	private void antesPersistir() {
		this.fechaCreacion = new Date();
	}

	@PreUpdate
	private void antesActualizar() {
		this.fechaModificacion = new Date();
	}

}