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

import com.academia.Academia.model.entity.ProgramaAcademico;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/programas")
@SessionAttributes("programa")
public class ProgramaAcademicoController {

    private final AcademiaService academiaService;

    public ProgramaAcademicoController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/listar")
    public String listarProgramasAcademicos(Model model) {
        model.addAttribute("titulo", "Listado de Programas Académicos");
        model.addAttribute("programas", academiaService.findAllProgramas());
        return "programas/listado_programas";
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        ProgramaAcademico programa = academiaService.findProgramaById(id);
        if (programa == null) {
            flash.addFlashAttribute("error", "El programa académico no existe en la base de datos");
            return "redirect:/programas/listar";
        }
        model.addAttribute("titulo", "Detalle del Programa Académico: ");
        model.addAttribute("programaAcademico", programa);
        return "programas/consulta_programa";
    }

    @GetMapping("/form")
    public String crearProgramaForm(Model model) {
        model.addAttribute("titulo", "Crear Programa Académico");
        model.addAttribute("programaAcademico", new ProgramaAcademico());
        return "programas/form_programa";
    }

    @PostMapping("/form")
    public String guardarPrograma(@Valid ProgramaAcademico programaAcademico, BindingResult result, Model model,RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", programaAcademico.getId() == null ? "Crear Programa Académico" : "Editar Programa Académico");
        //model.addAttribute("titulo", curso.getId() == null ? "Crear Curso" : "Editar Curso");
            return "programas/form_programa";
        }

        String mensajeFlash = (programaAcademico.getId() != null) ? "Programa Académico editado con éxito" : "Programa Académico creado con éxito";
        academiaService.savePrograma(programaAcademico);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/programas/listar";
    }

    @GetMapping("/form/{id}")
    public String editarProgramaForm(@PathVariable Long id, Model model, RedirectAttributes flash) {
        ProgramaAcademico programa = academiaService.findProgramaById(id);
        if (programa == null) {
            flash.addFlashAttribute("error", "El programa académico no existe en la base de datos");
            return "redirect:/programas/listar";
        }
        model.addAttribute("titulo", "Editar Programa Académico");
        model.addAttribute("programaAcademico", programa);
        return "programas/form_programa";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPrograma(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deleteProgramaAcademicoById(id);
            flash.addFlashAttribute("success", "Programa Académico eliminado con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el programa académico");
        }
        return "redirect:/programas/listar";
    }
    
}