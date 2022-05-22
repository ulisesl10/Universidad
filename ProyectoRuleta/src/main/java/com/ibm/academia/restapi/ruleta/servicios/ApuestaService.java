package com.ibm.academia.restapi.ruleta.servicios;

import java.util.Optional;

import com.ibm.academia.restapi.ruleta.modelo.entidades.Apuesta;
import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;

public interface ApuestaService {
	

	public Optional<Apuesta> apostarNumero(Ruleta ruleta, Integer numero,Double monto);
	public Optional<Apuesta> apostarColor(Ruleta ruleta,String color, Double monto);	

}
