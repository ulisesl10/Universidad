package com.ibm.academia.restapi.ruleta.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;

@Repository
public interface RuletaRepositorio extends CrudRepository<Ruleta, Integer>{

}
