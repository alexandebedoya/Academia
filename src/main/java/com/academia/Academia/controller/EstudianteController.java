package com.academia.Academia.controller;

// import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.academia.Academia.model.entity.CursoMatriculado;
import com.academia.Academia.model.entity.Estudiante;
import com.academia.Academia.model.service.AcademiaService;
import com.academia.Academia.model.service.EstudianteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
    
    private final EstudianteService estudianteService;
    private final AcademiaService academiaService;
    
    public EstudianteController(EstudianteService estudianteService, AcademiaService academiaService) {
        this.estudianteService = estudianteService;
        this.academiaService = academiaService;
    }
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Estudiantes");
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "estudiante/listar";
    }
    
    @GetMapping("/form")
    public String crear(Model model) {
        Estudiante estudiante = new Estudiante();
        model.addAttribute("titulo", "Formulario de Estudiante");
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("programaAcademico", academiaService.findAllProgramas());

        return "estudiante/form";
    }
    
    @PostMapping("/form")
    public String guardar(@Valid Estudiante estudiante, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Estudiante");
            return "estudiante/form";
        }
        
        String mensaje = (estudiante.getId() != null) ? "Estudiante editado con éxito" : "Estudiante creado con éxito";
        estudianteService.save(estudiante);
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/estudiantes/listar";
    }
    
    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Estudiante estudiante = null;
        if (id > 0) {
            estudiante = estudianteService.findById(id);
            if (estudiante == null) {
                flash.addFlashAttribute("error", "El ID del estudiante no existe en la base de datos");
                return "redirect:/estudiantes/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del estudiante no puede ser cero");
            return "redirect:/estudiantes/listar";
        }
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("titulo", "Editar Estudiante");
        model.addAttribute("programaAcademico", academiaService.findAllProgramas());

        return "estudiante/form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            estudianteService.deleteById(id);
            flash.addFlashAttribute("success", "Estudiante eliminado con éxito");
        }
        return "redirect:/estudiantes/listar";
    }
}