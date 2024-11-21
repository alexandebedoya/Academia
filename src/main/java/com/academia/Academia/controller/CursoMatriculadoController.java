package com.academia.Academia.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academia.Academia.model.entity.AsignaturaCursada;
import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.Estudiante;
import com.academia.Academia.model.service.AcademiaService;
import com.academia.Academia.model.service.EstudianteService;

@Controller
@RequestMapping("/estudiantes")
public class CursoMatriculadoController {

    private final EstudianteService estudianteService;
    private final AcademiaService academiaService;

    public CursoMatriculadoController(EstudianteService estudianteService, AcademiaService academiaService) {
        this.estudianteService = estudianteService;
        this.academiaService = academiaService;
    }

    @GetMapping("/cursos-matriculados/{id}")
public String cursosMatriculados(@PathVariable Long id, Model model, RedirectAttributes flash) {
    Estudiante estudiante = estudianteService.findById(id);
    if (estudiante == null) {
        flash.addFlashAttribute("error", "El estudiante no existe");
        return "redirect:/estudiantes/listar";
    }

    // Remove the period filtering, simply get all courses for the student
    List<CursoMatriculado> cursosMatriculados = academiaService.findCursosMatriculadosByEstudiante(id);

    model.addAttribute("titulo", "Cursos Matriculados");
    model.addAttribute("estudiante", estudiante);
    model.addAttribute("cursosMatriculados", cursosMatriculados);
    return "estudiante/cursos-matriculados";
}

    @GetMapping("/asignaturas-cursadas/{id}")
    public String asignaturasCursadas(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = estudianteService.findById(id);
        if (estudiante == null) {
            flash.addFlashAttribute("error", "El estudiante no existe");
            return "redirect:/estudiantes/listar";
        }

        List<AsignaturaCursada> asignaturasCursadas = academiaService.findAsignaturasCursadasByEstudiante(id);
        double promedioAcumulado = asignaturasCursadas.stream()
            .mapToDouble(AsignaturaCursada::getNotaFinal)
            .average()
            .orElse(0.0);

        model.addAttribute("titulo", "Historial Académico");
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("asignaturasCursadas", asignaturasCursadas);
        model.addAttribute("promedioAcumulado", promedioAcumulado);
        return "estudiante/asignaturas-cursadas";
    }
}