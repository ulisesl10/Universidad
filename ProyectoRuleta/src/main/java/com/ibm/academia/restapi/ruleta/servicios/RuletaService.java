package com.ibm.academia.restapi.ruleta.servicios;

import java.util.List;
import java.util.Optional;

import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;

public interface RuletaService {
	
	public Integer crearRuleta();	
	public Boolean abrirRuleta(Ruleta ruleta);
	public Boolean cerrarRuleta(Ruleta ruleta);
	public Optional<Ruleta> buscarPorId(Integer id);
	public List<Ruleta> buscarTodos();

}
