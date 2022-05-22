package com.ibm.academia.restapi.ruleta.servicios;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.academia.restapi.ruleta.excepciones.NotFoundException;
import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;
import com.ibm.academia.restapi.ruleta.repositorios.RuletaRepositorio;

@Service
public class RuletaServiceImpl implements RuletaService{

	@Autowired
	private RuletaRepositorio ruletaRepositorio;

	@Override
	public List<Ruleta> buscarTodos() {
		List<Ruleta> ruleta = (List<Ruleta>) ruletaRepositorio.findAll();
		if(ruleta.isEmpty()) {
		}
		return ruleta;
	}
	
	@Override
	public Integer crearRuleta() {
		Ruleta nuevaRuleta = new Ruleta();
		nuevaRuleta=ruletaRepositorio.save(nuevaRuleta);
		return nuevaRuleta.getId();
	}
	
	@SuppressWarnings("unused")
	private Integer girarRuleta() {
		Random random = new Random();
		int maximo = 37;
		return random.nextInt(maximo);
	}
	
	@Override
	public Optional<Ruleta> buscarPorId(Integer id) {
		Optional<Ruleta> ruleta = ruletaRepositorio.findById(id);
		if (!ruleta.isPresent()) {
		throw new NotFoundException(String.format("La ruleta ID %d no existe", id));
		}
		return ruleta;
	}

	@Override
	public Boolean abrirRuleta(Ruleta ruleta) {
		if (ruleta.getEstadoApuesta()) {
			return true;
		}
		ruleta.setEstadoApuesta(true);
		ruletaRepositorio.save(ruleta);
		return true;
	}

	@Override
	public Boolean cerrarRuleta(Ruleta ruleta) {
		if (ruleta.getEstadoApuesta()) {
			ruleta.setEstadoApuesta(false);
			ruletaRepositorio.save(ruleta);
			return true;
		}
		return true;
	}



}
