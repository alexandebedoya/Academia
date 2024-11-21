package com.academia.Academia.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(name = "numero_creditos", nullable = false)
    private Integer numeroCreditos;
    
    @Column(nullable = false)
    private String departamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanEstudio plan;
    
    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL)
    private List<AsignaturaCursada> asignaturasCursadas = new ArrayList<>();
    
    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL)
    private List<AsignaturaPlan> asignaturasPlanes = new ArrayList<>();
    
    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL)
    private List<Curso> cursos = new ArrayList<>();
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getNumeroCreditos() {
        return numeroCreditos;
    }
    
    public void setNumeroCreditos(Integer numeroCreditos) {
        this.numeroCreditos = numeroCreditos;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public PlanEstudio getPlan() {
        return plan;
    }
    
    public void setPlan(PlanEstudio plan) {
        this.plan = plan;
    }
    
    public List<AsignaturaCursada> getAsignaturasCursadas() {
        return asignaturasCursadas;
    }
    
    public void setAsignaturasCursadas(List<AsignaturaCursada> asignaturasCursadas) {
        this.asignaturasCursadas = asignaturasCursadas;
    }
    
    public List<AsignaturaPlan> getAsignaturasPlanes() {
        return asignaturasPlanes;
    }
    
    public void setAsignaturasPlanes(List<AsignaturaPlan> asignaturasPlanes) {
        this.asignaturasPlanes = asignaturasPlanes;
    }
    
    public List<Curso> getCursos() {
        return cursos;
    }
    
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}