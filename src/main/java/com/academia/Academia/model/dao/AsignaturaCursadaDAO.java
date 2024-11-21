package com.academia.Academia.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Asignatura;
import com.academia.Academia.model.entity.AsignaturaCursada;
import com.academia.Academia.model.entity.Estudiante;

@Repository
public interface AsignaturaCursadaDAO extends JpaRepository<AsignaturaCursada, Long> {
    boolean existsByEstudianteAndAsignatura(Estudiante estudiante, Asignatura asignatura);
    
}