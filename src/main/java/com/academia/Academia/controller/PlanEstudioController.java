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

import com.academia.Academia.model.entity.PlanEstudio;
import com.academia.Academia.model.service.AcademiaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/planestudio")
@SessionAttributes("planEstudio")
public class PlanEstudioController {

    private final AcademiaService academiaService;

    public PlanEstudioController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }


    @GetMapping("/listar")
    public String listarPlanesEstudio(Model model) {
        model.addAttribute("titulo", "Listado de Planes de Estudio");
        model.addAttribute("planes", academiaService.findAllPlanesEstudio());
        return "planes/listado_planes";
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        PlanEstudio planEstudio = academiaService.findPlanEstudioById(id);
        if (planEstudio == null) {
            flash.addFlashAttribute("error", "El plan de estudio no existe en la base de datos");
            return "redirect:/planestudio/listar";
        }
        model.addAttribute("titulo", "Detalle del Plan de Estudio: ");
        model.addAttribute("planEstudio", planEstudio);
        return "planes/consulta_plan";
    }

    @GetMapping("/form")
    public String crearPlanEstudioForm(Model model) {
        model.addAttribute("titulo", "Crear Plan de Estudio");//findAllProgramasAcademicos()
        model.addAttribute("planEstudio", new PlanEstudio());
        model.addAttribute("programaAcademico", academiaService.findAllProgramas());
        return "planes/form_plan";
    }

    @PostMapping("/form")
    public String guardarPlanEstudio(@Valid PlanEstudio planEstudio, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", planEstudio.getId() == null ? "Crear Plan de Estudio" : "Editar Plan de Estudio");
            return "planes/form_plan";
        }

        String mensajeFlash = (planEstudio.getId() != null) ? "Plan de Estudio editado con éxito" : "Plan de Estudio creado con éxito";
        academiaService.savePlanEstudio(planEstudio);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/planestudio/listar";
    }

    @GetMapping("/form/{id}")
    public String editarPlanEstudioForm(@PathVariable Long id, Model model, RedirectAttributes flash) {
        PlanEstudio planEstudio = academiaService.findPlanEstudioById(id);
        if (planEstudio == null) {
            flash.addFlashAttribute("error", "El plan de estudio no existe en la base de datos");
            return "redirect:/planestudio/listar";
        }
        model.addAttribute("titulo", "Editar Plan de Estudio");
        model.addAttribute("planEstudio", planEstudio);
        model.addAttribute("programaAcademico", academiaService.findAllProgramas());
        return "planes/form_plan";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlanEstudio(@PathVariable Long id, RedirectAttributes flash) {
        try {
            academiaService.deletePlanEstudioById(id);
            flash.addFlashAttribute("success", "Plan de Estudio eliminado con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el plan de estudio");
        }
        return "redirect:/planestudio/listar";
    }

    @GetMapping("/por-programa/{programaId}")
    public String listarPlanesPorPrograma(@PathVariable Long programaId, Model model) {
        model.addAttribute("titulo", "Planes de Estudio del Programa Académico");
        model.addAttribute("planes", academiaService.findPlanesEstudioByProgramaAcademicoId(programaId));
        return "planes/listado_planes";
    }

    @GetMapping("/por-anio/{anio}")
    public String buscarPlanPorAnioVigencia(@PathVariable Integer anio, Model model, RedirectAttributes flash) {
        PlanEstudio planEstudio = academiaService.findPlanEstudioByAnioVigencia(anio);
        if (planEstudio == null) {
            flash.addFlashAttribute("error", "No se encontró ningún plan de estudio para el año " + anio);
            return "redirect:/planestudio/listar";
        }
        model.addAttribute("titulo", "Plan de Estudio del Año " + anio);
        model.addAttribute("planes", java.util.Collections.singletonList(planEstudio));
        return "planes/listado_planes";
    }
}
