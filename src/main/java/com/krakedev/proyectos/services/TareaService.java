package com.krakedev.proyectos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaService {
		
		private final ProyectoRepository proyectoRepository;
		private final EmpleadoRepository empleadoRepository;
		private final TareaRepository tareaRepository;
		
		public TareaService(ProyectoRepository proyectoRepository, EmpleadoRepository empleadoRepository, TareaRepository tareaRepository) {
			
			this.proyectoRepository = proyectoRepository;
			this.empleadoRepository = empleadoRepository;
			this.tareaRepository = tareaRepository;
		}
		  
		public Tarea guardar(Tarea tarea) {
			
			Proyecto proyecto = proyectoRepository.findById(tarea.getProyectos().getId())
					.orElseThrow(() -> new RuntimeException("Proyecto no existe"));
			
			List<Empleado> empleadoDB = new ArrayList<>();
			
			for(Empleado empleado: tarea.getEmpleados()) {
				Empleado empleadoReal = empleadoRepository.findById(empleado.getId())
						.orElseThrow(() -> new RuntimeException("Empleado no existe"));
						
			    empleadoDB.add(empleadoReal);
			}
			
			tarea.setProyectos(proyecto);
			tarea.setEmpleados(empleadoDB);
			
			return tareaRepository.save(tarea);
		}
		
		public List<Tarea> listar() {
			return tareaRepository.findAll();
		}
		
		public Tarea buscar(int id) {
			Optional<Tarea> resultado = tareaRepository.findById(id);
			return resultado.orElse(null);
		}
		
		public Tarea actualizar(int id, Tarea datos) {
			
			Tarea tarea = buscar(id);
			
			if(tarea == null) {
				return null;
			}
			
			tarea.setDescripcion(datos.getDescripcion());
			
			tarea.setCostoEstimado(datos.getCostoEstimado());
			
			tarea.setFechaLimite(datos.getFechaLimite());
			
			return tareaRepository.save(tarea);
			
		}
		
		public boolean eliminar(int id) {
			
			Tarea tarea = buscar(id);
			
			if(tarea == null) {
				return false;
			}
			
			tareaRepository.deleteById(id);
			
			return true;
		}
	}
