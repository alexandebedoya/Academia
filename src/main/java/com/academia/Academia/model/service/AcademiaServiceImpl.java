package com.academia.Academia.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academia.Academia.model.dao.AsignaturaCursadaDAO;
import com.academia.Academia.model.dao.AsignaturaDAO;
import com.academia.Academia.model.dao.AsignaturaPlanDAO;
import com.academia.Academia.model.dao.CursoDAO;
import com.academia.Academia.model.dao.CursoMatriculadoDAO;
import com.academia.Academia.model.dao.PlanEstudioDAO;
import com.academia.Academia.model.dao.ProfesorDAO;
import com.academia.Academia.model.dao.ProgramaAcademicoDAO;
import com.academia.Academia.model.entity.Asignatura;
import com.academia.Academia.model.entity.AsignaturaCursada;
import com.academia.Academia.model.entity.AsignaturaPlan;
import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.PlanEstudio;
import com.academia.Academia.model.entity.Profesor;
import com.academia.Academia.model.entity.ProgramaAcademico;

@Service
public class AcademiaServiceImpl implements AcademiaService {

    private final ProgramaAcademicoDAO programaDAO;
    private final AsignaturaDAO asignaturaDAO;
    private final CursoDAO cursoDAO;
    private final CursoMatriculadoDAO cursoMatriculadoDAO;
    private final ProfesorDAO profesorDAO;
    private final AsignaturaCursadaDAO asignaturaCursadaDAO;
    private final AsignaturaPlanDAO asignaturaPlanDAO;
    private final PlanEstudioDAO planestudioDAO;

    public AcademiaServiceImpl(ProgramaAcademicoDAO programaDAO, AsignaturaDAO asignaturaDAO,
            CursoDAO cursoDAO, CursoMatriculadoDAO cursoMatriculadoDAO,
            ProfesorDAO profesorDAO, AsignaturaCursadaDAO asignaturaCursadaDAO,
            AsignaturaPlanDAO asignaturaPlanDAO, PlanEstudioDAO planestudioDAO) {
        this.programaDAO = programaDAO;
        this.asignaturaDAO = asignaturaDAO;
        this.cursoDAO = cursoDAO;
        this.cursoMatriculadoDAO = cursoMatriculadoDAO;
        this.profesorDAO = profesorDAO;
        this.asignaturaCursadaDAO = asignaturaCursadaDAO;
        this.asignaturaPlanDAO = asignaturaPlanDAO;
        this.planestudioDAO = planestudioDAO;

    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findCursosByPeriodoAndPrograma(String periodo, Long programaId) {
        return cursoDAO.findAll().stream()
                .filter(curso -> curso.getAsignatura() != null) // Filter out cursos with null asignatura
                .filter(curso -> curso.getAsignatura().getPlan() != null) // Filter out asignaturas with null plan
                .filter(curso -> curso.getAsignatura().getPlan().getPrograma() != null) // Filter out plans with null
                                                                                        // programa
                .filter(curso -> curso.getPeriodo().equals(periodo) &&
                        curso.getAsignatura().getPlan().getPrograma().getId().equals(programaId))
                .collect(Collectors.toList());
    }
    // Implementación del nuevo método
    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaCursada> findAsignaturasCursadasByEstudiante(Long estudianteId) {
        return asignaturaCursadaDAO.findAll().stream()
                .filter(ac -> ac.getEstudiante().getId().equals(estudianteId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramaAcademico> findAllProgramas() {
        return programaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaAcademico findProgramaById(Long id) {
        return programaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void savePrograma(ProgramaAcademico programa) {
        programaDAO.save(programa);
    }

    @Override
    @Transactional
    public void deleteProgramaAcademicoById(Long id) {
        if (!programaDAO.existsById(id)) {
            throw new RuntimeException("ProgramaAcademico no encontrado con ID: " + id);
        }
        programaDAO.deleteById(id);
    }

    @Override
    public List<ProgramaAcademico> findProgramasAcademicosByFacultad(String facultad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProgramasAcademicosByFacultad'");
    }

    @Override
    public ProgramaAcademico findProgramaAcademicoByNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProgramaAcademicoByNombre'");
    }

    // Asignaturas
    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> findAllAsignaturas() {
        return asignaturaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Asignatura findAsignaturaById(Long id) {
        return asignaturaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveAsignatura(Asignatura asignatura) {
        asignaturaDAO.save(asignatura);
    }

    @Override
    @Transactional
    public void deleteAsignaturaById(Long id) {
        if (!asignaturaDAO.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada con ID: " + id);
        }
        asignaturaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> findAsignaturasByDepartamento(String departamento) {
        return asignaturaDAO.findByDepartamento(departamento);
    }

    // Cursos
    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAllCursos() {
        return cursoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findCursosByPeriodo(String periodo) {
        return cursoDAO.findAll().stream()
                .filter(curso -> curso.getPeriodo().equals(periodo))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoById(Long id) {
        return cursoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveCurso(Curso curso) {
        cursoDAO.save(curso);
    }

    @Override
    @Transactional
    public void deleteCursoById(Long id) {
        cursoDAO.deleteById(id);
    }

    // Cursos_Matriculados
    @Override
    @Transactional(readOnly = true)
    public List<CursoMatriculado> findCursosMatriculadosByEstudiante(Long estudianteId) {
        return cursoMatriculadoDAO.findByEstudianteId(estudianteId);
    }


    // @Override
    // @Transactional(readOnly = true)
    // public List<CursoMatriculado> findCursosMatriculadosByEstudiante(Long estudianteId) {
    //     return cursoMatriculadoDAO.findAll().stream()
    //             .filter(cm -> cm.getEstudiante().getId().equals(estudianteId))
    //             .collect(Collectors.toList());
    // }

    @Override
    @Transactional(readOnly = true)
    public List<CursoMatriculado> findCursosMatriculadosByPeriodo(String periodo) {
        return cursoMatriculadoDAO.findAll().stream()
                .filter(cm -> cm.getPeriodo().equals(periodo))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveCursoMatriculado(CursoMatriculado cursoMatriculado) {
        // Verifica si la matrícula ya existe para evitar duplicados
        if (cursoMatriculadoDAO.existsByEstudianteAndCurso(cursoMatriculado.getEstudiante(),
                cursoMatriculado.getCurso())) {
            throw new IllegalArgumentException("El estudiante ya está matriculado en este curso");
        }

        // Guarda la matrícula
        cursoMatriculadoDAO.save(cursoMatriculado);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer calcularCreditosMatriculados(Long estudianteId, String periodo) {
        return findCursosMatriculadosByEstudiante(estudianteId).stream()
                .filter(cm -> cm.getPeriodo().equals(periodo))
                .mapToInt(cm -> cm.getCurso().getAsignatura().getNumeroCreditos())
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarPrerequisitos(Long estudianteId, Long asignaturaId) {
        List<AsignaturaPlan> prerequisitos = asignaturaPlanDAO.findAll().stream()
                .filter(ap -> ap.getAsignatura().getId().equals(asignaturaId))
                .collect(Collectors.toList());

        if (prerequisitos.isEmpty()) {
            return true;
        }

        for (AsignaturaPlan ap : prerequisitos) {
            if (ap.getPrerrequisito() != null) {
                boolean prerequisitoCursado = asignaturaCursadaDAO.findAll().stream()
                        .anyMatch(ac -> ac.getEstudiante().getId().equals(estudianteId) &&
                                ac.getAsignatura().getId().equals(ap.getPrerrequisito().getId()) &&
                                ac.getNotaFinal() >= 3.0);
                if (!prerequisitoCursado) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarAsignaturaCursada(Long estudianteId, Long asignaturaId) {
        return asignaturaCursadaDAO.findAll().stream()
                .anyMatch(ac -> ac.getEstudiante().getId().equals(estudianteId) &&
                        ac.getAsignatura().getId().equals(asignaturaId) &&
                        ac.getNotaFinal() >= 3.0);
    }

    // Profesor
    @Override
    @Transactional(readOnly = true)
    public List<Profesor> findAllProfesores() {
        return profesorDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Profesor findProfesorById(Long id) {
        return profesorDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveProfesor(Profesor profesor) {
        profesorDAO.save(profesor);
    }

    @Override
    @Transactional
    public void deleteProfesorById(Long id) {
        profesorDAO.deleteById(id);
    }

    // Plan estudio
    @Override
    @Transactional(readOnly = true)
    public List<PlanEstudio> findAllPlanesEstudio() {
        return planestudioDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanEstudio findPlanEstudioById(Long id) {
        return planestudioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanEstudio no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void savePlanEstudio(PlanEstudio planEstudio) {
        planestudioDAO.save(planEstudio);
    }

    @Override
    @Transactional
    public void deletePlanEstudioById(Long id) {
        if (!planestudioDAO.existsById(id)) {
            throw new RuntimeException("PlanEstudio no encontrado con ID: " + id);
        }
        planestudioDAO.deleteById(id);
    }

    @Override
    public List<PlanEstudio> findPlanesEstudioByProgramaAcademicoId(Long programaAcademicoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPlanesEstudioByProgramaAcademicoId'");
    }

    @Override
    public PlanEstudio findPlanEstudioByAnioVigencia(Integer anioVigencia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPlanEstudioByAnioVigencia'");
    }

    // Implementación de servicios para Asignaturas planes
    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaPlan> findAllAsignaturasPlan() {
        return asignaturaPlanDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AsignaturaPlan findAsignaturaPlanById(Long id) {
        return asignaturaPlanDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveAsignaturaPlan(AsignaturaPlan asignaturaPlan) {
        if (validarPrerrequisitos(asignaturaPlan)) {
            asignaturaPlanDAO.save(asignaturaPlan);
        } else {
            throw new RuntimeException("Los prerrequisitos no son válidos");
        }
    }

    @Override
    @Transactional
    public void deleteAsignaturaPlanById(Long id) {
        if (asignaturaPlanDAO.findByPrerrequisitoId(id).isEmpty()) {
            asignaturaPlanDAO.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar porque es prerrequisito de otras asignaturas");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaPlan> findAsignaturaPlanByPlanEstudio(Long planId) {
        return asignaturaPlanDAO.findByPlanEstudioIdOrdered(planId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaPlan> findAsignaturaPlanBySemestre(Integer semestreNivel) {
        return asignaturaPlanDAO.findBySemestreNivel(semestreNivel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaPlan> findAsignaturaPlanByPrerequisito(Long prerequisitoId) {
        return asignaturaPlanDAO.findByPrerrequisitoId(prerequisitoId);
    }

    @Override
    public boolean validarPrerrequisitos(AsignaturaPlan asignaturaPlan) {
        if (asignaturaPlan.getPrerrequisito() == null) {
            return true;
        }

        AsignaturaPlan prerequisito = findAsignaturaPlanById(asignaturaPlan.getPrerrequisito().getId());
        if (prerequisito == null) {
            return false;
        }

        // Validar que el prerrequisito esté en un semestre anterior
        return prerequisito.getSemestreNivel() < asignaturaPlan.getSemestreNivel();
    }

    // Implementaciones de los nuevos métodos
    @Override
    @Transactional(readOnly = true)
    public List<Curso> findCursosByPrograma(Long programaId) {
        return cursoDAO.findByProgramaId(programaId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarCursoPertenecePrograma(Long cursoId, Long programaId) {
        return cursoDAO.existsCursoInPrograma(cursoId, programaId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarCupoDisponible(Long cursoId) {
        Curso curso = cursoDAO.findById(cursoId).orElse(null);
        if (curso == null)
            return false;

        long matriculasActuales = cursoMatriculadoDAO.countByCursoId(cursoId);
        return matriculasActuales < curso.getCupoMaximo();
    }

    @Override
    @Transactional
    public List<CursoMatriculado> findCursosMatriculadosByEstudianteId(Long estudianteId) {
        return cursoMatriculadoDAO.findAll().stream()
        .filter(cm -> cm.getEstudiante().getId().equals(estudianteId))
        .collect(Collectors.toList());
    }

    
    @Override
    @Transactional
    public void actualizarEstadoCurso(Long cursoMatriculadoId, Double notaFinal) {
        // Validar nota
        if (notaFinal < 0 || notaFinal > 5) {
            throw new RuntimeException("La nota debe estar entre 0 y 5");
        }

        CursoMatriculado matricula = cursoMatriculadoDAO.findById(cursoMatriculadoId)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        // Verificar que el curso esté activo
        if (!"Activo".equals(matricula.getEstadoCurso())) {
            throw new RuntimeException("Solo se pueden calificar cursos activos");
        }

        // Actualizar nota y estado
        matricula.setNotaFinal(notaFinal);
        matricula.setEstadoCurso(notaFinal >= 3.0 ? "Aprobado" : "Reprobado");
        cursoMatriculadoDAO.save(matricula);

        // Registrar en asignaturas cursadas
        registrarAsignaturaCursada(matricula);
    }

    @Override
    @Transactional
    public void retirarCurso(Long cursoMatriculadoId) {
        CursoMatriculado matricula = cursoMatriculadoDAO.findById(cursoMatriculadoId)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        if (!"Activo".equals(matricula.getEstadoCurso())) {
            throw new RuntimeException("Solo se pueden retirar cursos activos");
        }

        matricula.setEstadoCurso("Retirado");
        cursoMatriculadoDAO.save(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoMatriculado> findCursosMatriculadosByEstudianteAndPeriodo(Long estudianteId, String periodo) {
        return cursoMatriculadoDAO.findByEstudianteIdAndPeriodo(estudianteId, periodo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estudianteMatriculado(Long estudianteId, Long cursoId) {
        return cursoMatriculadoDAO.existsActivoByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    private void registrarAsignaturaCursada(CursoMatriculado matricula) {
        // Verificar si ya existe un registro para esta asignatura
        if (!asignaturaCursadaDAO.existsByEstudianteAndAsignatura(
                matricula.getEstudiante(), 
                matricula.getCurso().getAsignatura())) {
            
            AsignaturaCursada asignaturaCursada = new AsignaturaCursada();
            asignaturaCursada.setEstudiante(matricula.getEstudiante());
            asignaturaCursada.setAsignatura(matricula.getCurso().getAsignatura());
            asignaturaCursada.setNotaFinal(matricula.getNotaFinal());
            
            asignaturaCursadaDAO.save(asignaturaCursada);
        }
    }
    
    @Override
    public CursoMatriculado findCursoMatriculadoById(Long cursoId) {
        return cursoMatriculadoDAO.findById(cursoId).orElse(null);
    }

    @Override
    public void deleteCursoMatriculado(Long cursoId) {
        cursoMatriculadoDAO.deleteById(cursoId);
    }


}