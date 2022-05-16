package com.ibm.academia.restapi.universidad.datos;

import java.math.BigDecimal;

import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Direccion;
import com.ibm.academia.restapi.universidad.modelo.entidades.Empleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;

public class DatosDummy {

	public static Persona alumno01() {
		return new Alumno(null, "Ulises", "Lupercio", "456456", "ulises",
				new Direccion("Av. Vallarta", "01", "1111", "1", "1", "Jalisco"));
	}

	public static Persona alumno02() {
		return new Alumno(null, "Salvador", "Lupercio", "456564", "ulises",
				new Direccion("Av. Vallarta", "5516", "45665", "55", "5", "Jalisco"));
	}

	public static Persona alumno03() {
		return new Alumno(null, "Ulises", "Bocanegra", "15656", "ulises",
				new Direccion("Av. Vallarta", "123", "3123312", "864", "6", "Jalisco"));
	}

	public static Aula aula01() {
		return new Aula(null, 1, "10x20", 25, TipoPizarron.PIZARRON_BLANCO, "ulises");
	}

	public static Aula aula02() {
		return new Aula(null, 2, "10x25", 30, TipoPizarron.PIZARRON_TIZA, "ulises");
	}

	public static Aula aula03() {
		return new Aula(null, 3, "10x20", 25, TipoPizarron.PIZARRON_BLANCO, "ulises");
	}

	public static Aula aula04() {
		return new Aula(null, 4, "10x25", 30, TipoPizarron.PIZARRON_TIZA, "ulises");
	}

	public static Carrera carrera01() {
		return new Carrera(null, "Ingenieria en Sistemas", 60, 5, "ulises");
	}

	public static Carrera carrera02() {
		return new Carrera(null, "Licenciatura en Sistemas", 55, 4, "ulises");
	}

	public static Carrera carrera03() {
		return new Carrera(null, "Ingenieria Quimica", 40, 5, "ulises");
	}

	public static Carrera carrera04() {
		return new Carrera(null, "Ingenieria en Electronica", 50, 4, "ulises");
	}

	public static Persona empleado01() {
		return new Empleado(null, "Salvador", "Bocanegra", "94315", "ulises",
				new Direccion("Av. Vallarta ", "12", "53135", "03", "486", "Jalisco"), new BigDecimal("80000.00"),
				TipoEmpleado.ADMINISTRATIVO);
	}

	public static Persona empleado02() {
		return new Empleado(null, "Ulises", "Lupercio", "648156", "ulises",
				new Direccion("Av. Vallarta ", "13", "123315", "561", "123", "Jalisco"), new BigDecimal("50000.00"),
				TipoEmpleado.MANTENIMIENTO);
	}

	public static Pabellon pabellon01() {
		return new Pabellon(null, 1000.00, "Ingenierias",
				new Direccion("Av. Vallarta ", "156456", "615", "153", "15", "Jalisco"), "ulises");
	}
	
	public static Pabellon pabellon02() {
		return new Pabellon(null, 1000.00, "Licenciaturas",
				new Direccion("Av. Vallarta ", "546", "156", "165", "156", "Jalisco"), "ulises");
	}

	public static Persona profesor01() {
		return new Profesor(null, "Salvador", "Lupercio", "1561533", "ulises",
				new Direccion("Av. Vallarta", "48", "95720", "846", "48", "Jalisco"), new BigDecimal("100000.00"));
	}

	public static Persona profesor02() {
		return new Profesor(null, "Ulises", "Bocanegra", "36872279", "ulises",
				new Direccion("Av. Vallarta", "456", "29629", "789", "132", "Jalisco"), new BigDecimal("90000.00"));
	}

}
