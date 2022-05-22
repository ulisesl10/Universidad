package com.ibm.academia.restapi.ruleta.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.restapi.ruleta.modelo.entidades.Apuesta;

@Repository
public interface ApuestaRepositorio extends CrudRepository<Apuesta, Integer> {

}
