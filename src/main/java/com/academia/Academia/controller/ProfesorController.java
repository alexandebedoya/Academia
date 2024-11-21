package com.academia.Academia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.academia.Academia.model.entity.Profesor;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/profesores")
@SessionAttributes("profesor")
public class ProfesorController {

    private final AcademiaService academiaService;

    public ProfesorController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/listar")
    public String listarProfesores(Model model) {
        model.addAttribute("titulo", "Listado de Profesores");
        model.addAttribute("profesores", academiaService.findAllProfesores());
        return "profesores/listar";
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Profesor profesor = academiaService.findProfesorById(id);
        if (profesor == null) {
            flash.addFlashAttribute("error", "El profesor no existe en la base de datos");
            return "redirect:/profesores/listar";
        }
        model.addAttribute("titulo", "Detalle del Profesor: " );
        model.addAttribute("profesor", profesor);
        return "profesores/consulta_profesor";
    }

    @GetMapping("/form")
    public String crearProfesorForm(Model model) {
        model.addAttribute("titulo", "Crear Profesor");
        model.addAttribute("profesor", new Profesor());
        return "profesores/form"; // Vista de formulario de creación de Profesor
    }

    @PostMapping("/form")
    public String guardarProfesor(@Valid Profesor profesor, BindingResult result, Model model,
            RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", profesor.getId() == null ? "Crear Profesor" : "Editar Profesor");
            return "profesores/form"; // Si hay errores, mostrar nuevamente el formulario
        }

        String mensajeFlash = (profesor.getId() != null) ? "Profesor editada con éxito" : "Profesor creada con éxito";
        academiaService.saveProfesor(profesor);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/profesores/listar"; // Redirección después de guardar
    }

    @GetMapping("/form/{id}")
    public String editarProfesorForm(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Profesor profesor = academiaService.findProfesorById(id);
        if (profesor == null) {
            flash.addFlashAttribute("error", "El profesor no existe en la base de datos");
            return "redirect:/profesores/listar";
        }
        model.addAttribute("titulo", "Editar Profesor");
        model.addAttribute("profesor", profesor);
        return "profesores/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProfesor(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deleteProfesorById(id);
            flash.addFlashAttribute("success", "Profesor eliminado con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el profesor");
        }
        return "redirect:/profesores/listar";
    }

    @GetMapping("/buscar")
    public String buscarProfesor(@RequestParam String termino, Model model) {
        model.addAttribute("titulo", "Resultados de la búsqueda: " + termino);
        // model.addAttribute("profesores",
        // academiaService.findProfesoresByNombreOrApellido(termino));
        return "profesores/listado_profesores";
    }

}