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
	public ResponseEntity<?> crear(
			@RequestBody Empleado empleado) {
		
        try {

		Empleado creada = servicioEmpleado.crear(empleado);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creada);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear el empleado: " + e.getMessage());
       }
	}

	@GetMapping
	public ResponseEntity<?> listar() {
        try {
        
		List<Empleado> empleados = servicioEmpleado.listar();

		return ResponseEntity.ok(empleados);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al listar los empleados: " + e.getMessage());
    }
  }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(
			@PathVariable int id) {
		try {
		Empleado empleado = servicioEmpleado.buscar(id);

		if (empleado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(empleado);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al buscar el empleado: " + e.getMessage());
    }
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(
			@PathVariable int id,
			@RequestBody Empleado empleado) {
		
		try {
		Empleado actualizado =
				servicioEmpleado.actualizar(id, empleado);

		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar el empleado: " + e.getMessage());
    }
}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(
			@PathVariable int id) {
	try {
		boolean eliminado = servicioEmpleado.eliminar(id);

		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	} catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar el empleado: " + e.getMessage());
    }
}
}
