package com.academia.Academia.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.Estudiante;
import com.academia.Academia.model.service.AcademiaService;
import com.academia.Academia.model.service.EstudianteService;

@Controller
@RequestMapping("/matricula")
public class MatriculaController {

    private final AcademiaService academiaService;
    private final EstudianteService estudianteService;

    public MatriculaController(AcademiaService academiaService, EstudianteService estudianteService) {
        this.academiaService = academiaService;
        this.estudianteService = estudianteService;
    }

    @GetMapping("/cursos/{estudianteId}")
    public String listarCursosDisponibles(@PathVariable Long estudianteId, Model model) {
        Estudiante estudiante = estudianteService.findById(estudianteId);
        if (estudiante == null) {
            return "redirect:/estudiantes/listar";
        }

        // Obtener cursos filtrados solo por programa académico
        List<Curso> cursosDisponibles = academiaService.findCursosByPrograma(estudiante.getPrograma().getId());

        // Calcular los créditos matriculados
        int creditosMatriculados = 0;
        List<CursoMatriculado> cursosMatriculados = academiaService.findCursosMatriculadosByEstudiante(estudianteId);
        for (CursoMatriculado matricula : cursosMatriculados) {
            creditosMatriculados += matricula.getCurso().getAsignatura().getNumeroCreditos();
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("cursos", cursosDisponibles);
        model.addAttribute("creditosMatriculados", creditosMatriculados); // Pasar el total de créditos matriculados
        model.addAttribute("maxCreditos", 22); // Agregar el máximo de créditos permitido
        return "matricula/cursos_disponibles";
    }

    @PostMapping("/matricular")
    public String matricularCurso(@RequestParam Long estudianteId,
            @RequestParam Long cursoId,
            RedirectAttributes flash) {
        Estudiante estudiante = estudianteService.findById(estudianteId);
        Curso curso = academiaService.findCursoById(cursoId);

        if (estudiante == null || curso == null) {
            flash.addFlashAttribute("error", "Datos de matrícula inválidos");
            return "redirect:/estudiantes/listar";
        }

        // Verificar si el curso pertenece al programa del estudiante
        if (!academiaService.verificarCursoPertenecePrograma(curso.getId(), estudiante.getPrograma().getId())) {
            flash.addFlashAttribute("error", "Este curso no pertenece a tu programa académico");
            return "redirect:/matricula/cursos/" + estudianteId;
        }

        // Verificar cupo disponible
        if (!academiaService.verificarCupoDisponible(curso.getId())) {
            flash.addFlashAttribute("error", "No hay cupos disponibles en este curso");
            return "redirect:/matricula/cursos/" + estudianteId;
        }

        // Verificar si el estudiante ya está matriculado en el curso
        if (estudianteService.estudianteMatriculado(estudiante.getId(), curso.getId())) {
            flash.addFlashAttribute("error", "El estudiante ya está matriculado en este curso");
            return "redirect:/matricula/cursos/" + estudianteId;
        }

        // Verificar si el estudiante supera el límite de créditos
        int creditosMatriculados = 0;
        List<CursoMatriculado> cursosMatriculados = academiaService.findCursosMatriculadosByEstudiante(estudianteId);
        for (CursoMatriculado matricula : cursosMatriculados) {
            creditosMatriculados += matricula.getCurso().getAsignatura().getNumeroCreditos();
        }
        int creditosPorMatricular = curso.getAsignatura().getNumeroCreditos();
        if (creditosMatriculados + creditosPorMatricular > 22) {
            flash.addFlashAttribute("error", "No puedes matricular más de 22 créditos");
            return "redirect:/matricula/cursos/" + estudianteId;
        }

        // Verificar prerrequisitos
        if (!academiaService.verificarPrerequisitos(estudianteId, curso.getAsignatura().getId())) {
            flash.addFlashAttribute("error", "No cumple con los prerrequisitos necesarios");
            return "redirect:/matricula/cursos/" + estudianteId;
        }

        // Crear y guardar la matrícula
        CursoMatriculado matricula = new CursoMatriculado();
        matricula.setEstudiante(estudiante);
        matricula.setCurso(curso);
        matricula.setEstadoCurso("Activo");

        // Asignar valor por defecto al periodo si es necesario
        if (matricula.getPeriodo() == null) {
            matricula.setPeriodo("2025-1"); // O cualquier valor que se deba asignar
        }

        academiaService.saveCursoMatriculado(matricula);
        flash.addFlashAttribute("success", "Curso matriculado exitosamente");

        return "redirect:/matricula/cursos/" + estudianteId;
    }

    @GetMapping("/cursos-matriculados/{id}")
    public String verCursosMatriculados(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = estudianteService.findById(id);
        if (estudiante == null) {
            flash.addFlashAttribute("error", "El estudiante no existe");
            return "redirect:/estudiantes/listar";
        }
        
        String periodoActual = "2024-01";
        List<CursoMatriculado> cursosMatriculados = academiaService.findCursosMatriculadosByEstudiante(id)
            .stream()
            .filter(cm -> cm.getPeriodo().equals(periodoActual))
            .toList();
        
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("cursosMatriculados", cursosMatriculados);
        return "estudiante/cursos_matriculados";
    }
    public String verCursosMatriculados(@RequestParam("estudianteId") Long estudianteId, Model model) {
        // Obtener los cursos matriculados
        List<CursoMatriculado> cursosMatriculados = academiaService.findCursosMatriculadosByEstudiante(estudianteId);
        
        // Agregar los cursos al modelo para que se muestren en la vista
        model.addAttribute("cursosMatriculados", cursosMatriculados);
        
        // Retornar el nombre de la vista
        return "estudiantes/consultarestudiante";  // Vista que mostrará los cursos matriculados
    }



    //------------------------

    

}
