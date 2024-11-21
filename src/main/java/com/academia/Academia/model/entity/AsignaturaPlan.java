package com.academia.Academia.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignaturas_planes")
public class AsignaturaPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanEstudio plan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;
    
    @Column(name = "semestre_nivel", nullable = false)
    private Integer semestreNivel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prerrequisito_id")
    private Asignatura prerrequisito;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public PlanEstudio getPlan() {
        return plan;
    }
    
    public void setPlan(PlanEstudio plan) {
        this.plan = plan;
    }
    
    public Asignatura getAsignatura() {
        return asignatura;
    }
    
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
    
    public Integer getSemestreNivel() {
        return semestreNivel;
    }
    
    public void setSemestreNivel(Integer semestreNivel) {
        this.semestreNivel = semestreNivel;
    }
    
    public Asignatura getPrerrequisito() {
        return prerrequisito;
    }
    
    public void setPrerrequisito(Asignatura prerrequisito) {
        this.prerrequisito = prerrequisito;
    }
}