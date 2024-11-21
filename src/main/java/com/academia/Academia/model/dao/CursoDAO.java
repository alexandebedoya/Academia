package com.academia.Academia.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Curso;

@Repository
public interface CursoDAO extends JpaRepository<Curso, Long> {
    @Query("SELECT c FROM Curso c " +
"JOIN c.asignatura a " +
"JOIN a.asignaturasPlanes ap " +
"JOIN ap.plan pe " +
"WHERE pe.programa.id = :programaId")
List<Curso> findByProgramaId(@Param("programaId") Long programaId);


@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
"FROM Curso c " +
"JOIN c.asignatura a " +
"JOIN a.asignaturasPlanes ap " +
"JOIN ap.plan pe " +
"WHERE c.id = :cursoId AND pe.programa.id = :programaId")
boolean existsCursoInPrograma(@Param("cursoId") Long cursoId, @Param("programaId") Long programaId);
}