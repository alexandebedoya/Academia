package com.academia.Academia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academia.Academia.model.entity.Curso;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cursos")
@SessionAttributes("curso")
public class CursoController {

    private final AcademiaService academiaService;

    public CursoController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/listar")
    public String listarCursos(Model model) {
        model.addAttribute("titulo", "Listado de Cursos");
        model.addAttribute("cursos", academiaService.findAllCursos());
        return "cursos/listado_cursos";
    }
    

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Curso curso = academiaService.findCursoById(id);
        if (curso == null) {
            flash.addFlashAttribute("error", "El curso no existe en la base de datos");
            return "redirect:/cursos/listar";
        }
        // model.addAttribute("titulo", "Detalle del Curso: " + curso.getAsignatura().getNombre());
        model.addAttribute("titulo", "Detalle del curso: " );
        model.addAttribute("curso", curso);
        return "cursos/consulta_curso";
    }

    
    @GetMapping("/form")
    public String crearCursoForm(Model model) {
        model.addAttribute("titulo", "Crear Curso");
        model.addAttribute("curso", new Curso());
        model.addAttribute("profesores", academiaService.findAllProfesores()); // Agregar profesores al modelo
        model.addAttribute("asignaturas", academiaService.findAllAsignaturas()); // Agregar asignaturas al modelo
        
        return "cursos/form_curso"; // Vista de formulario de creación de Profesor
    }
    

    @PostMapping("/form")
    public String guardarCurso(@Valid Curso curso, BindingResult result, Model model,
            RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", curso.getId() == null ? "Crear Curso" : "Editar Curso");
            return "cursos/form_curso"; // Si hay errores, mostrar nuevamente el formulario
        }

        String mensajeFlash = (curso.getId() != null) ? "Curso editado con éxito" : "Curso creado con éxito";
        academiaService.saveCurso(curso);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/cursos/listar"; // Redirección después de guardar
    }

    @GetMapping("/form/{id}")
    public String editarCursoForm(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Curso curso = academiaService.findCursoById(id);
        if (curso == null) {
            flash.addFlashAttribute("error", "El curso no existe en la base de datos");
            return "redirect:/cursos/listar";
        }
        model.addAttribute("titulo", "Editar Curso");
        model.addAttribute("curso", curso);
        model.addAttribute("asignaturas", academiaService.findAllAsignaturas());
        model.addAttribute("profesores", academiaService.findAllProfesores());
        return "cursos/form_curso";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deleteCursoById(id);
            flash.addFlashAttribute("success", "Curso eliminado con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el curso");
        }
        return "redirect:/cursos/listar";
    }

    @GetMapping("/por-periodo/{periodo}")
    public String listarCursosPorPeriodo(@PathVariable String periodo, Model model) {
        model.addAttribute("titulo", "Cursos del Periodo: " + periodo);
        model.addAttribute("cursos", academiaService.findCursosByPeriodo(periodo));
        return "cursos/listado_cursos";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Curso curso = academiaService.findCursoById(id);
        if (curso == null) {
            flash.addFlashAttribute("error", "El curso no existe");
            return "redirect:/cursos/listar";
        }
        model.addAttribute("curso", curso);
        model.addAttribute("titulo", "Detalle del Curso: " + curso.getAsignatura().getNombre());
        return "curso/ver";
    }

    
}