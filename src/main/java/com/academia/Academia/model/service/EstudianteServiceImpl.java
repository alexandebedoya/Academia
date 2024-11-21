package com.academia.Academia.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academia.Academia.model.dao.CursoDAO;
import com.academia.Academia.model.dao.CursoMatriculadoDAO;
import com.academia.Academia.model.dao.EstudianteDAO;
import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.entity.Estudiante;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteDAO estudianteDAO;
    private final CursoDAO cursoDAO; // Inyectamos el servicio del curso
    private final CursoMatriculadoDAO cursoMatriculadoDAO; // Inyectamos el DAO para manejar la relación de
                                                           // matriculación

    public EstudianteServiceImpl(EstudianteDAO estudianteDAO, CursoDAO cursoDAO,
            CursoMatriculadoDAO cursoMatriculadoDAO) {
        this.estudianteDAO = estudianteDAO;
        this.cursoDAO = cursoDAO;
        this.cursoMatriculadoDAO = cursoMatriculadoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        return estudianteDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Estudiante findById(Long id) {
        return estudianteDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Estudiante estudiante) {
        estudianteDAO.save(estudiante);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        estudianteDAO.deleteById(id);
    }

    // Método para verificar si un estudiante está matriculado en un curso
    @Override
    @Transactional(readOnly = true)
    public boolean estudianteMatriculado(Long estudianteId, Long cursoId) {
        Estudiante estudiante = findById(estudianteId); // Buscar estudiante por ID
        Curso curso = cursoDAO.findById(cursoId).orElse(null); // Buscar curso por ID
        
        // Verificar si el estudiante y el curso existen antes de comprobar la matriculación
        if (estudiante == null || curso == null) {
            return false; // Si no se encuentran el estudiante o el curso, retornamos false
        }
        
        // Verificar si existe la relación de matriculación
        return cursoMatriculadoDAO.existsByEstudianteAndCurso(estudiante, curso); // Retornar si está matriculado
    }
}