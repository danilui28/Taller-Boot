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

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

	private final EmpleadoService servicioEmpleado;

	public EmpleadoController(EmpleadoService servicioEmpleado) {
		this.servicioEmpleado = servicioEmpleado;
	}

	@PostMapping
	public ResponseEntity<Empleado> crear(
			@RequestBody Empleado empleado) {

		Empleado creada = servicioEmpleado.crear(empleado);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creada);
	}

	@GetMapping
	public ResponseEntity<List<Empleado>> listar() {

		List<Empleado> empleados = servicioEmpleado.listar();

		return ResponseEntity.ok(empleados);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empleado> buscar(
			@PathVariable int id) {

		Empleado empleado = servicioEmpleado.buscar(id);

		if (empleado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(empleado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empleado> actualizar(
			@PathVariable int id,
			@RequestBody Empleado empleado) {

		Empleado actualizado =
				servicioEmpleado.actualizar(id, empleado);

		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@PathVariable int id) {

		boolean eliminado = servicioEmpleado.eliminar(id);

		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
