package com.academia.Academia.model.service;

import java.util.List;

import com.academia.Academia.model.entity.Asignatura;
import com.academia.Academia.model.entity.AsignaturaCursada;
import com.academia.Academia.model.entity.AsignaturaPlan;
import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.PlanEstudio;
import com.academia.Academia.model.entity.Profesor;
import com.academia.Academia.model.entity.ProgramaAcademico;

public interface AcademiaService {
    // Servicios existentes...

    // Nuevo método para filtrar cursos por programa
    List<Curso> findCursosByPeriodoAndPrograma(String periodo, Long programaId);

    List<CursoMatriculado> findCursosMatriculadosByEstudianteId(Long estudianteId);

    // Servicios para ProgramaAcademico
    List<ProgramaAcademico> findAllProgramas();

    ProgramaAcademico findProgramaById(Long id);

    void savePrograma(ProgramaAcademico programa);

    void deleteProgramaAcademicoById(Long id);

    List<ProgramaAcademico> findProgramasAcademicosByFacultad(String facultad);

    ProgramaAcademico findProgramaAcademicoByNombre(String nombre);

    // Servicios para Asignatura
    List<Asignatura> findAllAsignaturas();

    Asignatura findAsignaturaById(Long id);

    void saveAsignatura(Asignatura asignatura);

    void deleteAsignaturaById(Long id);

    List<Asignatura> findAsignaturasByDepartamento(String departamento);

    // Servicios para Curso
    List<Curso> findAllCursos();

    List<Curso> findCursosByPeriodo(String periodo);

    // List<Curso> findCursosByAsignaturaId(Long asignaturaId);
    // List<Curso> findCursosByProfesorId(Long profesorId);
    Curso findCursoById(Long id);

    void saveCurso(Curso curso);

    void deleteCursoById(Long id);

    // Servicios para CursoMatriculado
    List<CursoMatriculado> findCursosMatriculadosByEstudiante(Long estudianteId);

    List<CursoMatriculado> findCursosMatriculadosByPeriodo(String periodo);

    void saveCursoMatriculado(CursoMatriculado cursoMatriculado);

    Integer calcularCreditosMatriculados(Long estudianteId, String periodo);

    boolean verificarPrerequisitos(Long estudianteId, Long asignaturaId);

    boolean verificarAsignaturaCursada(Long estudianteId, Long asignaturaId);

    // Servicios para Profesor
    List<Profesor> findAllProfesores();

    Profesor findProfesorById(Long id);

    void saveProfesor(Profesor profesor);

    void deleteProfesorById(Long id);

    // Servicios para Plan Estudio
    List<PlanEstudio> findAllPlanesEstudio();

    PlanEstudio findPlanEstudioById(Long id);

    void savePlanEstudio(PlanEstudio planEstudio);

    void deletePlanEstudioById(Long id);

    List<PlanEstudio> findPlanesEstudioByProgramaAcademicoId(Long programaAcademicoId);

    PlanEstudio findPlanEstudioByAnioVigencia(Integer anioVigencia);

    // Servicios para Asignatura plan
    List<AsignaturaPlan> findAllAsignaturasPlan();

    AsignaturaPlan findAsignaturaPlanById(Long id);

    void saveAsignaturaPlan(AsignaturaPlan asignaturaPlan);

    void deleteAsignaturaPlanById(Long id);

    List<AsignaturaPlan> findAsignaturaPlanByPlanEstudio(Long planId);

    List<AsignaturaPlan> findAsignaturaPlanBySemestre(Integer semestreNivel);

    List<AsignaturaPlan> findAsignaturaPlanByPrerequisito(Long prerequisitoId);

    boolean validarPrerrequisitos(AsignaturaPlan asignaturaPlan);

    // Método para encontrar cursos por programa académico
    List<Curso> findCursosByPrograma(Long programaId);

    // Métodos de verificación para el controlador de matrícula
    boolean verificarCursoPertenecePrograma(Long cursoId, Long programaId);

    boolean verificarCupoDisponible(Long cursoId);

    // Nuevo método para obtener asignaturas cursadas
    List<AsignaturaCursada> findAsignaturasCursadasByEstudiante(Long estudianteId);

    // Nuevos métodos para gestión de estados de cursos
    void actualizarEstadoCurso(Long cursoMatriculadoId, Double notaFinal);

    void retirarCurso(Long cursoMatriculadoId);

    List<CursoMatriculado> findCursosMatriculadosByEstudianteAndPeriodo(Long estudianteId, String periodo);

    boolean estudianteMatriculado(Long estudianteId, Long cursoId);

    CursoMatriculado findCursoMatriculadoById(Long cursoId);

    void deleteCursoMatriculado(Long cursoId);
}