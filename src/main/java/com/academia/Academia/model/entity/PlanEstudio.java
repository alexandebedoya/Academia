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
@Table(name = "planes_estudio")
public class PlanEstudio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id")
    private ProgramaAcademico programa;
    
    @Column(name = "anio_vigencia", nullable = false)
    private Integer anioVigencia;
    
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<AsignaturaPlan> asignaturasPlanes = new ArrayList<>();
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public ProgramaAcademico getPrograma() {
        return programa;
    }
    
    public void setPrograma(ProgramaAcademico programa) {
        this.programa = programa;
    }
    
    public Integer getAnioVigencia() {
        return anioVigencia;
    }
    
    public void setAnioVigencia(Integer anioVigencia) {
        this.anioVigencia = anioVigencia;
    }
    
    public List<AsignaturaPlan> getAsignaturasPlanes() {
        return asignaturasPlanes;
    }
    
    public void setAsignaturasPlanes(List<AsignaturaPlan> asignaturasPlanes) {
        this.asignaturasPlanes = asignaturasPlanes;
    }
}