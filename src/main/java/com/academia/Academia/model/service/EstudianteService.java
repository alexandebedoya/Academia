package com.academia.Academia.model.service;

import java.util.List;

import com.academia.Academia.model.entity.Estudiante;

public interface EstudianteService {
    List<Estudiante> findAll();
    Estudiante findById(Long id);
    void save(Estudiante estudiante);
    void deleteById(Long id);
    boolean estudianteMatriculado(Long estudianteId, Long cursoId);

    
}