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

import com.academia.Academia.model.entity.Asignatura;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/asignaturas")
@SessionAttributes("asignatura")
public class AsignaturaController {

    private final AcademiaService academiaService;

    public AsignaturaController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/listar")
    public String listarAsignaturas(Model model) {
        model.addAttribute("titulo", "Listado de Asignaturas");
        model.addAttribute("asignaturas", academiaService.findAllAsignaturas());
        return "asignaturas/listado_asignaturas"; // Ruta ajustada para las vistas
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Asignatura asignatura = academiaService.findAsignaturaById(id);
        if (asignatura == null) {
            flash.addFlashAttribute("error", "La asignatura no existe en la base de datos");
            return "redirect:/asignaturas/listar";
        }
        model.addAttribute("titulo", "Detalle de la Asignatura: ");
        model.addAttribute("asignatura", asignatura);
        return "asignaturas/consulta_asignatura";
    }

    @GetMapping("/form")
    public String crearAsignaturaForm(Model model) {
        model.addAttribute("titulo", "Crear Asignatura");
        model.addAttribute("asignatura", new Asignatura());
        return "asignaturas/form_asignatura"; // Vista de formulario de creación de asignatura
    }

    @PostMapping("/form")
    public String guardarAsignatura(@Valid Asignatura asignatura, BindingResult result, Model model,
            RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", asignatura.getId() == null ? "Crear Asignatura" : "Editar Asignatura");
            return "asignaturas/form_asignatura"; // Si hay errores, mostrar nuevamente el formulario
        }

        String mensajeFlash = (asignatura.getId() != null) ? "Asignatura editada con éxito" : "Asignatura creada con éxito";
        academiaService.saveAsignatura(asignatura);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/asignaturas/listar"; // Redirección después de guardar
    }

    @GetMapping("/form/{id}")
    public String editarAsignaturaForm(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Asignatura asignatura = academiaService.findAsignaturaById(id);
        if (asignatura == null) {
            flash.addFlashAttribute("error", "La asignatura no existe en la base de datos");
            return "redirect:/asignaturas/listar"; // Redirección si no se encuentra la asignatura
        }
        model.addAttribute("titulo", "Editar Asignatura");
        model.addAttribute("asignatura", asignatura);
        return "asignaturas/form_asignatura"; // Vista de formulario de edición de asignatura
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deleteAsignaturaById(id);
            flash.addFlashAttribute("success", "Asignatura eliminada con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la asignatura");
        }
        return "redirect:/asignaturas/listar"; // Redirección después de eliminar
    }

}
