package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

	private final ProyectoService servicioProyecto;

	public ProyectoController(ProyectoService servicioProyecto) {
		this.servicioProyecto = servicioProyecto;
	}

	@PostMapping
	public ResponseEntity<Proyecto> crear(
			@RequestBody Proyecto proyecto) {

		Proyecto creada = servicioProyecto.crear(proyecto);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creada);
	}

	@GetMapping
	public ResponseEntity<List<Proyecto>> listar() {

		List<Proyecto> proyectos = servicioProyecto.listar();

		return ResponseEntity.ok(proyectos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Proyecto> buscar(
			@PathVariable int id) {

		Proyecto proyecto = servicioProyecto.buscar(id);

		if (proyecto == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(proyecto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Proyecto> actualizar(
			@PathVariable int id,
			@RequestBody Proyecto proyecto) {

		Proyecto actualizado =
				servicioProyecto.actualizar(id, proyecto);

		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@PathVariable int id) {

		boolean eliminado = servicioProyecto.eliminar(id);

		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
