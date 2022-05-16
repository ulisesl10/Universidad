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

import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.excepciones.NotFoundException;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.servicios.EmpleadoDAO;
import com.ibm.academia.restapi.universidad.servicios.PersonaDAO;

@RestController
@RequestMapping("/restapi")
public class EmpleadoController {
	
	private final static Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

	@Autowired
	@Qualifier("empleadoDAOImpl")
	private PersonaDAO empleadoDao;
	
	
	@PostMapping("/empleado")
	public ResponseEntity<?> crearEmpleado(@RequestBody Persona empleado) {
		Persona empleadoGuardado = empleadoDao.guardar(empleado);
		return new ResponseEntity<Persona>(empleadoGuardado, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/empleados/lista")
	public ResponseEntity<?> obtenerTodos() {
		List<Persona> empleados = (List<Persona>) empleadoDao.buscarTodos();
		if (empleados.isEmpty())
			throw new NotFoundException("No existen empleados");

		return new ResponseEntity<List<Persona>>(empleados, HttpStatus.OK);
	}
	
	
	@GetMapping("/empleado/empleadoId/{empleadoId}")
	public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Long empleadoId) {
		Optional<Persona> oEmpleado = empleadoDao.buscarPorId(empleadoId);
		if (!oEmpleado.isPresent())
			throw new NotFoundException(String.format("El empleado con id: %d no existe", empleadoId));
		return new ResponseEntity<Persona>(oEmpleado.get(), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/empleado/eliminar/empleadoId/{empleadoId}")
	public ResponseEntity<?> eliminarEmpleado(@PathVariable Long empleadoId) {
		Optional<Persona> oEmpleado = empleadoDao.buscarPorId(empleadoId);
		if (!oEmpleado.isPresent())
			throw new NotFoundException(String.format("El empleado con id: %d no existe", empleadoId));

		empleadoDao.eliminarPorId(oEmpleado.get().getId());
		return new ResponseEntity<String>("El empleado con id: " + empleadoId + " se eliminó", HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping("/empleado/actualizar/empleadoId/{empleadoId}")
	public ResponseEntity<?> actualizarEmpleado(@PathVariable Long empleadoId, @RequestBody Persona empleado, BindingResult result) {
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
			profesorActualizado = ((EmpleadoDAO) empleadoDao).actualizar(empleadoId, empleado);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}

		return new ResponseEntity<Persona>(profesorActualizado, HttpStatus.OK);
	}
	
	
	@GetMapping("/empleados/tipoEmpleado/{tipoEmpleado}")
	public ResponseEntity<?> buscarPorTipoEmpleado(@PathVariable TipoEmpleado tipoEmpleado){
		List<Persona> empleados = (List<Persona>) ((EmpleadoDAO)empleadoDao).findEmpleadoByTipoEmpleado(tipoEmpleado);
		if(empleados.isEmpty())
			throw new NotFoundException("No hay coincidencias para el tipo de empleado ingresado");

		return new ResponseEntity<List<Persona>>(empleados, HttpStatus.OK);
	}

	
	@PutMapping("/empleado/asociar-pabellon")
	public ResponseEntity<?> asociarPabellon(@RequestParam Long empleadoId,@RequestParam Long pabellonId){
		Persona empleado = ((EmpleadoDAO) empleadoDao).asociarPabellon(empleadoId, pabellonId);
		return new ResponseEntity<Persona>(empleado, HttpStatus.OK);
	}
	
	
}
