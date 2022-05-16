package com.ibm.academia.restapi.universidad.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;
import com.ibm.academia.restapi.universidad.servicios.ProfesorDAO;

@RestController
@RequestMapping("/restapi")
public class ProfesorController {

	private final static Logger logger = LoggerFactory.getLogger(ProfesorController.class);

	@Autowired
	@Qualifier("profesorDAOImpl")
	private PersonaDAO profesorDao;

	
	@PostMapping("/profesor")
	public ResponseEntity<?> crearProfesor(@RequestBody Persona profesor) {
		Persona profesorGuardado = profesorDao.guardar(profesor);
		return new ResponseEntity<Persona>(profesorGuardado, HttpStatus.CREATED);
	}

	
	@GetMapping("/profesores/lista")
	public ResponseEntity<?> obtenerTodos() {
		List<Persona> profesores = (List<Persona>) profesorDao.buscarTodos();
		if (profesores.isEmpty())
			throw new NotFoundException("No existen profesores");

		return new ResponseEntity<List<Persona>>(profesores, HttpStatus.OK);
	}

	
	@GetMapping("/profesor/profesorId/{profesorId}")
	public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Long profesorId) {
		Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
		if (!oProfesor.isPresent())
			throw new NotFoundException(String.format("El profesor con id: %d no existe", profesorId));
		return new ResponseEntity<Persona>(oProfesor.get(), HttpStatus.OK);
	}

	
	@DeleteMapping("/profesor/eliminar/profesorId/{profesorId}")
	public ResponseEntity<?> eliminarProfesor(@PathVariable Long profesorId) {
		Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
		if (!oProfesor.isPresent())
			throw new NotFoundException(String.format("El alumno con id: %d no existe", profesorId));

		profesorDao.eliminarPorId(oProfesor.get().getId());
		return new ResponseEntity<String>("El profesor con id: " + profesorId + " se eliminó", HttpStatus.NO_CONTENT);
	}

	
	@PutMapping("/profesor/actualizar/profesorId/{profesorId}")
	public ResponseEntity<?> actualizarProfesor(@PathVariable Long profesorId, @RequestBody Persona profesor,
			BindingResult result) {
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<String> listaErrores = result.getFieldErrors().stream()
					.map(errores -> "Campo: " + errores.getField() + " " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}

		Persona profesorActualizado = null;

		try {
			profesorActualizado = ((ProfesorDAO) profesorDao).actualizar(profesorId, profesor);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}

		return new ResponseEntity<Persona>(profesorActualizado, HttpStatus.OK);
	}

	
	@PutMapping("/profesor/asociar-carrera-profesor")
	public ResponseEntity<?> asociarCarrera(@RequestParam Long carreraId, @RequestParam Long profesorId) {
		Persona profesor = ((ProfesorDAO) profesorDao).asociarCarreraProfesor(carreraId, profesorId);
		return new ResponseEntity<Persona>(profesor, HttpStatus.OK);
	}
	
	
	@GetMapping("/profesor/nombreCarrera/{nombreCarrera}")
	public ResponseEntity<?> buscarProfesoresPorNombreCarrera(@PathVariable String nombreCarrera){
		List<Persona> profesores = (List<Persona>) ((ProfesorDAO)profesorDao).findProfesoresByCarrera(nombreCarrera);
		if(profesores.isEmpty())
			throw new NotFoundException("No hay coincidencias para la carrera ingresada");
		
		return new ResponseEntity<List<Persona>>(profesores, HttpStatus.OK);
	}

}
