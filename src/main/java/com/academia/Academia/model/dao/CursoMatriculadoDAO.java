package com.academia.Academia.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.Estudiante;

@Repository
public interface CursoMatriculadoDAO extends JpaRepository<CursoMatriculado, Long> {
    // long countByCursoId(Long cursoId);
    List<CursoMatriculado> findByEstudianteId(Long estudianteId);

    boolean existsByEstudianteAndCurso(Estudiante estudiante, Curso curso);

    @Query("SELECT cm FROM CursoMatriculado cm JOIN FETCH cm.curso WHERE cm.estudiante.id = :estudianteId")
    List<CursoMatriculado> findCursosMatriculadosByEstudianteId(@Param("estudianteId") Long estudianteId);

    // List<CursoMatriculado> findByEstudianteId(Long estudianteId);

    @Query("SELECT cm FROM CursoMatriculado cm WHERE cm.estudiante.id = :estudianteId AND cm.periodo = :periodo")


    List<CursoMatriculado> findByEstudianteIdAndPeriodo(@Param("estudianteId") Long estudianteId,
            @Param("periodo") String periodo);

    // boolean existsByEstudianteAndCurso(Estudiante estudiante, Curso curso);

    @Query("SELECT COUNT(cm) FROM CursoMatriculado cm WHERE cm.curso.id = :cursoId")
    Long countByCursoId(@Param("cursoId") Long cursoId);

    @Query("SELECT CASE WHEN COUNT(cm) > 0 THEN true ELSE false END FROM CursoMatriculado cm " +
            "WHERE cm.estudiante.id = :estudianteId AND cm.curso.id = :cursoId AND cm.estadoCurso = 'Activo'")
    boolean existsActivoByEstudianteIdAndCursoId(@Param("estudianteId") Long estudianteId,
            @Param("cursoId") Long cursoId);

}