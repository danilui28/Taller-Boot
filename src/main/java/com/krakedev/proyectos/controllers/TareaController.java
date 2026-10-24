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

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

	private final TareaService servicioTarea;

	public TareaController(TareaService servicioTarea) {
		this.servicioTarea = servicioTarea;
	}

	@PostMapping
	public ResponseEntity<Tarea> crear(
			@RequestBody Tarea tarea) {

		Tarea creada = servicioTarea.guardar(tarea);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creada);
	}

	@GetMapping
	public ResponseEntity<List<Tarea>> listar() {

		List<Tarea> tareas = servicioTarea.listar();

		return ResponseEntity.ok(tareas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarea> buscar(
			@PathVariable int id) {

		Tarea tarea = servicioTarea.buscar(id);

		if (tarea == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tarea);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tarea> actualizar(
			@PathVariable int id,
			@RequestBody Tarea tarea) {

		Tarea actualizado =
				servicioTarea.actualizar(id, tarea);

		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@PathVariable int id) {

		boolean eliminado = servicioTarea.eliminar(id);

		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
