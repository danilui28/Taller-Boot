package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;

@Service
public class ProyectoService {

	private final ProyectoRepository proyectoRepository;

	public ProyectoService(ProyectoRepository proyectoRepository) {
		this.proyectoRepository = proyectoRepository;
	}

	public Proyecto crear(Proyecto proyecto) {
		return proyectoRepository.save(proyecto);
	}

	public List<Proyecto> listar() {
		return proyectoRepository.findAll();
	}

	public Proyecto buscar(int id) {
		Optional<Proyecto> resultado = proyectoRepository.findById(id);
		return resultado.orElse(null);
	}

	public Proyecto actualizar(int id, Proyecto datos) {
		
		Proyecto proyecto = buscar(id);
		
		if(proyecto == null) {
			return null;
		}
		
		proyecto.setNombre(datos.getNombre());
		
		proyecto.setDescripcion(datos.getDescripcion());
		
		proyecto.setFechaInicio(datos.getFechaInicio());
		
		return proyectoRepository.save(proyecto);
	}

	public boolean eliminar(int id) {
		
		Proyecto proyecto = buscar(id);
		
		if(proyecto == null) {
			return false;
		}
		
		proyectoRepository.deleteById(id);

		return true;
	}
}
