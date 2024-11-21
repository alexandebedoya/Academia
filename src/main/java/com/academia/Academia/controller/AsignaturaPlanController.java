package com.academia.Academia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.academia.Academia.model.entity.AsignaturaPlan;
import com.academia.Academia.model.entity.PlanEstudio;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/asignaturasplan")
@SessionAttributes("asignaturaPlan")
public class AsignaturaPlanController {

    private final AcademiaService academiaService;

    public AsignaturaPlanController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<AsignaturaPlan> asignaturasPlan = academiaService.findAllAsignaturasPlan();
        model.addAttribute("titulo", "Listado de Asignaturas del Plan");
        model.addAttribute("asignaturasPlan", asignaturasPlan != null ? asignaturasPlan : new ArrayList<>());
        return "asignaturasplan/listado_asignaturas_plan";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        AsignaturaPlan asignaturaPlan = new AsignaturaPlan();
        prepararFormulario(model, asignaturaPlan);
        return "asignaturasplan/form_asignatura_plan";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        AsignaturaPlan asignaturaPlan = academiaService.findAsignaturaPlanById(id);
        if (asignaturaPlan == null) {
            flash.addFlashAttribute("error", "La asignatura no existe en el plan");
            return "redirect:/asignaturasplan/listar";
        }
        prepararFormulario(model, asignaturaPlan);
        return "asignaturasplan/form_asignatura_plan";
    }

    private void prepararFormulario(Model model, AsignaturaPlan asignaturaPlan) {
        List<Asignatura> asignaturas = academiaService.findAllAsignaturas();
        List<PlanEstudio> planesEstudio = academiaService.findAllPlanesEstudio();
        List<AsignaturaPlan> asignaturasDisponibles = academiaService.findAllAsignaturasPlan();

        // Filtrar asignaturas disponibles si el plan o semestre están establecidos
        if (asignaturaPlan.getPlan() != null && asignaturaPlan.getSemestreNivel() != null) {
            asignaturasDisponibles = asignaturasDisponibles.stream()
                    .filter(ap -> ap.getSemestreNivel() < asignaturaPlan.getSemestreNivel())
                    .collect(Collectors.toList());
        }

        model.addAttribute("titulo",
                asignaturaPlan.getId() == null ? "Crear Asignatura en Plan" : "Editar Asignatura en Plan");
        model.addAttribute("asignaturaPlan", asignaturaPlan);
        model.addAttribute("asignaturas", asignaturas);
        model.addAttribute("plan", planesEstudio);
        model.addAttribute("asignaturasDisponibles", asignaturasDisponibles);
    }

    @PostMapping("/form")
    public String guardar(@Valid AsignaturaPlan asignaturaPlan, BindingResult result, Model model,
            RedirectAttributes flash) {
        if (result.hasErrors()) {
            prepararFormulario(model, asignaturaPlan);
            return "asignaturasplan/form_asignatura_plan";
        }

        if (!academiaService.validarPrerrequisitos(asignaturaPlan)) {
            flash.addFlashAttribute("error", "El prerrequisito debe estar en un semestre anterior");
            prepararFormulario(model, asignaturaPlan);
            return "asignaturasplan/form_asignatura_plan";
        }

        try {
            academiaService.saveAsignaturaPlan(asignaturaPlan);
            flash.addFlashAttribute("success", "Asignatura guardada con éxito en el plan");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/asignaturasplan/form";
        }

        return "redirect:/asignaturasplan/listar";
    }

    @GetMapping("/consultar/{id}")
    public String consultarAsignatura(@PathVariable Long id, Model model) {
        AsignaturaPlan asignaturaPlan = academiaService.findAsignaturaPlanById(id);
        if (asignaturaPlan == null) {
            return "error"; // O el nombre de una vista de error.
        }
        model.addAttribute("asignaturaPlan", asignaturaPlan);
        return "asignaturasplan/consulta_asignatura_plan";
    }

    // Agregar la funcionalidad de eliminar una asignatura del plan
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deleteAsignaturaPlanById(id);
            flash.addFlashAttribute("success", "Asignatura eliminada con éxito.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar la asignatura: " + e.getMessage());
        }
        return "redirect:/asignaturasplan/listar";
    }
    @GetMapping("/listarPorPlan")
    public String listarPorPlan(Model model) {
        List<AsignaturaPlan> asignaturasPlan = academiaService.findAllAsignaturasPlan();
    
        // Agrupar asignaturas por plan
        Map<PlanEstudio, List<AsignaturaPlan>> asignaturasPorPlan = asignaturasPlan.stream()
                .collect(Collectors.groupingBy(AsignaturaPlan::getPlan));
    
        model.addAttribute("titulo", "Asignaturas por Plan de Estudio");
        model.addAttribute("asignaturasPorPlan", asignaturasPorPlan);
        return "asignaturasplan/listado_asignaturas_por_plan";
    }
    

    
}
