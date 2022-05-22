package com.ibm.academia.restapi.ruleta.servicios;


import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.academia.restapi.ruleta.modelo.entidades.Apuesta;
import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;
import com.ibm.academia.restapi.ruleta.repositorios.ApuestaRepositorio;


@Service
public class ApuestaServiceImpl implements ApuestaService{
	

	@Autowired
	private ApuestaRepositorio apuestaRepositorio;


	private Integer girarRuleta() {
		Random random = new Random();
		int maximo = 37;
		return random.nextInt(maximo);
	}
	
	public Optional<Apuesta> apostarNumero(Ruleta ruleta, Integer numero,Double cantidad) {
		Apuesta apuesta = new Apuesta();
		apuesta.setRuleta(ruleta);
		apuesta.setCantidad(cantidad);
		apuesta.setNumeroApostado(numero);
		ruleta.setId(ruleta.getId());
		Integer resultadoObtenidoInteger = girarRuleta();
		System.out.println(numero);
		if(numero==(resultadoObtenidoInteger)) {
			apuesta.setEstadoApuesta("Gano");
		}else {
			apuesta.setEstadoApuesta("Perdio");
		}
		Optional<Apuesta> resultadoApuesta = Optional.of(apuestaRepositorio.save(apuesta));
		
		return resultadoApuesta; 
	}

	public Optional<Apuesta> apostarColor(Ruleta ruleta, String color, Double cantidad) {
		if(color.equalsIgnoreCase("negro") ||color.equalsIgnoreCase("rojo")  ) {
		}
		Apuesta apuesta = new Apuesta();
		apuesta.setRuleta(ruleta);
		apuesta.setCantidad(cantidad);
		apuesta.setColorApostado(color);
		ruleta.setId(ruleta.getId());
		
		
		Integer resultadoObtenido = girarRuleta();
		if (resultadoObtenido % 2 == 0) {
			apuesta.setEstadoApuesta("Gano");
		}else {
			apuesta.setEstadoApuesta("Perdio");
		}
		Optional<Apuesta> resultadoApuesta = Optional.of(apuestaRepositorio.save(apuesta));
		return resultadoApuesta; 
	}

}
