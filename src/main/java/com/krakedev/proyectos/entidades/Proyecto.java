package com.krakedev.proyectos.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name = "proyectos")
public class Proyecto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String nombre;
	
	@Column(length = 250)
	private String descripcion;
	
	@Column(nullable = false)
	private LocalDate fechaInicio;
	
	@JsonIgnore
	@OneToMany(mappedBy = "proyecto")
	private List<tareas> tareas;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public List<tareas> getTareas() {
		return tareas;
	}

	public void setTareas(List<tareas> tareas) {
		this.tareas = tareas;
	}

	public Proyecto() {
		
	}

	public Proyecto(int id, String nombre, String descripcion, LocalDate fechaInicio,
			List<tareas> tareas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.tareas = tareas;
	}
	
	

}
