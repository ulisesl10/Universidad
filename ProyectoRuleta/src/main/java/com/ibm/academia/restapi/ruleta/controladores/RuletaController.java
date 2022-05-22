package com.ibm.academia.restapi.ruleta.controladores;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.restapi.ruleta.excepciones.BadRequestException;
import com.ibm.academia.restapi.ruleta.excepciones.NotFoundException;
import com.ibm.academia.restapi.ruleta.modelo.entidades.Apuesta;
import com.ibm.academia.restapi.ruleta.modelo.entidades.Ruleta;
import com.ibm.academia.restapi.ruleta.servicios.ApuestaService;
import com.ibm.academia.restapi.ruleta.servicios.RuletaService;

@RestController
@RequestMapping("/ruleta")
public class RuletaController {
	
	Logger logger = LoggerFactory.getLogger(RuletaController.class);
	
	@Autowired
	private RuletaService ruletaService;
	
	@Autowired
	private ApuestaService apuestaService;


	@PostMapping("/crearRuleta")
	 public ResponseEntity<?> crearRuleta(){
			return ResponseEntity.status(HttpStatus.CREATED).body(ruletaService.crearRuleta());
	 }
	
	@GetMapping("/listar")
	public ResponseEntity<?> buscarTodas() {
		List<Ruleta> ruletas = ruletaService.buscarTodos();
		if (ruletas.isEmpty()) {
			throw new NotFoundException("No hay ruletas");
		}
		return new ResponseEntity<List<Ruleta>>(ruletas, HttpStatus.OK);
	}
	
	@PutMapping("/abrirRuleta/{ruletaId}")
	public ResponseEntity<?> abrirRuleta(@PathVariable Integer ruletaId){
		
		Optional<Ruleta> ruleta = ruletaService.buscarPorId(ruletaId);
		if (!ruleta.isPresent()) {
			throw new NotFoundException(String.format("No existe ruleta  ID: %d", ruletaId));
		}
		if(ruleta.get().getEstadoApuesta()) {
			throw new BadRequestException(String.format("la ruleta ID: %d está abierta", ruletaId));
		}
		Boolean resultado = ruletaService.abrirRuleta(ruleta.get());
		if (!resultado) {
			throw new NotFoundException(String.format("No se pudo abrir la ruleta ID %d",ruletaId));
		}
		return new ResponseEntity<Ruleta>(ruleta.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> realizarApuestaNumero(@PathVariable Integer ruletaId,@PathVariable Double cantidad,@PathVariable Integer numeroApuesta){
		if (numeroApuesta<0 || numeroApuesta>36) {
			throw new BadRequestException(String.format("Los numeros son del 0-36"));
		}
		if(cantidad>10000) {
			throw new BadRequestException(String.format("La cantidad maxima de apuesta es de 10,000"));
		}
		Optional<Ruleta> ruleta = ruletaService.buscarPorId(ruletaId);
		if (!ruleta.isPresent()) {
			throw new NotFoundException(String.format("No existe ruleta ID: %d", ruletaId));
		}
		Optional<Apuesta> apuestaOptional = null;
		apuestaOptional = apuestaService.apostarNumero(ruleta.get(), numeroApuesta, cantidad);
		return new ResponseEntity<Apuesta>(apuestaOptional.get(), HttpStatus.CREATED);
	}

	@PostMapping("/apuestaNumero/{ruletaId}/cantidad/{cantidad}/numeroApuesta/{numeroApuesta}")
	public ResponseEntity<?> realizarApuestaNumero(@RequestBody Apuesta apuesta, @RequestBody Ruleta ruleta){
		Integer numeroApuesta = apuesta.getNumeroApostado();
		Integer ruletaId = ruleta.getId();
		Double cantidad = apuesta.getCantidad();

		if (numeroApuesta<0 || numeroApuesta>36) {
			throw new BadRequestException(String.format("Los numeros son del 0-36"));
		}
		if(cantidad>10000) {
			throw new BadRequestException(String.format("La cantidad maxima de apuesta es de 10,000"));
		}
		Optional<Ruleta> ruleta1 = ruletaService.buscarPorId(ruletaId);
		if (!ruleta1.isPresent()) {
			throw new NotFoundException(String.format("No existe ruleta ID: %d", ruletaId));
		}
		Optional<Apuesta> apuestaOptional = null;
		apuestaOptional = apuestaService.apostarNumero(ruleta1.get(), numeroApuesta,cantidad);
		return new ResponseEntity<Apuesta>(apuestaOptional.get(), HttpStatus.CREATED);
	}
	
	@PostMapping("/apuestaColor/{ruletaId}/cantidad/{cantidad}/colorApuesta/{colorApuesta}")
	public ResponseEntity<?> realizarApuestaColor(@RequestBody Apuesta apuesta, @RequestBody Ruleta ruleta){
		String colorApuesta = apuesta.getColorApostado();
		Integer ruletaId = ruleta.getId();
		
		if (!colorApuesta.equalsIgnoreCase("negro") && !colorApuesta.equalsIgnoreCase("rojo")) {
			throw new BadRequestException(String.format("Elige un color Rojo-Negro"));
		}
		Optional<Ruleta> ruleta1 = ruletaService.buscarPorId(ruletaId);
		if (!ruleta1.isPresent()) {
			throw new NotFoundException(String.format("No existe ruleta  ID: %d", ruletaId));
		}
		if(apuesta.getCantidad()>10000) {
			throw new BadRequestException(String.format("La cantidad maxima de apuesta es de 10,000"));
		}
		Optional<Apuesta> apuestaOptional = null;
		apuestaOptional = apuestaService.apostarColor(ruleta1.get(), colorApuesta, apuesta.getCantidad());
		return new ResponseEntity<Apuesta>(apuestaOptional.get(), HttpStatus.CREATED);
	}
	
	@PutMapping("/cerrarRuleta/{ruletaId}")
	public ResponseEntity<?> cerrarRuleta(@PathVariable Integer ruletaId){
		
		Optional<Ruleta> ruleta = ruletaService.buscarPorId(ruletaId);
		if (!ruleta.isPresent()) {
			throw new NotFoundException(String.format("No existe ruleta ID: %d", ruletaId));
		}
		if(!ruleta.get().getEstadoApuesta()) {
			throw new BadRequestException(String.format("La ruleta con el ID: %d está cerrada", ruletaId));
		}
		Boolean resultado = ruletaService.cerrarRuleta(ruleta.get());
		if (!resultado) {
			throw new NotFoundException(String.format("No se pudo cerrar la ruleta con el ID %d",ruletaId));
		}
		return new ResponseEntity<Ruleta>(ruleta.get(), HttpStatus.OK);

	}

}
