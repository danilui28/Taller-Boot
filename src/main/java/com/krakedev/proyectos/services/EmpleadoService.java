package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

	private final EmpleadoRepository empleadoRepository;

	public EmpleadoService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public Empleado crear(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public List<Empleado> listar() {
		return empleadoRepository.findAll();
	}

	public Empleado buscar(int id) {
		Optional<Empleado> resultado = empleadoRepository.findById(id);
		return resultado.orElse(null);
	}

	public Empleado actualizar(int id, Empleado datos) {
		
		Empleado empleado = buscar(id);
		
		if(empleado == null) {
			return null;
		}
		
		empleado.setNombre(datos.getNombre());
		
		empleado.setCargo(datos.getCargo());
		
		return empleadoRepository.save(empleado);
	}

	public boolean eliminar(int id) {
		
		Empleado proyecto = buscar(id);
		
		if(proyecto == null) {
			return false;
		}
		
		empleadoRepository.deleteById(id);

		return true;
	}
	
}
