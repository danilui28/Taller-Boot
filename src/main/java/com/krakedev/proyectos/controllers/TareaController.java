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
	public ResponseEntity<?> crear(
			@RequestBody Tarea tarea) {
    try {
		Tarea creada = servicioTarea.guardar(tarea);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creada);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear la tarea: " + e.getMessage());
       }
	}

	@GetMapping
	public ResponseEntity<?> listar() {
    try {
		List<Tarea> tareas = servicioTarea.listar();

		return ResponseEntity.ok(tareas);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al listar las tareas: " + e.getMessage());
    }
  }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(
			@PathVariable Integer id) {
    try {
		Tarea tarea = servicioTarea.buscar(id);

		if (tarea == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tarea);
		
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al buscar la tarea: " + e.getMessage());
    }
}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(
			@PathVariable Integer id,
			@RequestBody Tarea tarea) {
    try {
		Tarea actualizado =
				servicioTarea.actualizar(id, tarea);
		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar la tarea: " + e.getMessage());
    }
}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(
			@PathVariable Integer id) {
    try {
		boolean eliminado = servicioTarea.eliminar(id);
 
		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar la tarea: " + e.getMessage());
    }
}}
