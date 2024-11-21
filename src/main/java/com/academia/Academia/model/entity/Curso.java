package com.academia.Academia.model.entity;

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
@Table(name = "cursos")
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
    
    @Column(nullable = false)
    private String periodo;
    
    @Column(nullable = false)
    private String horario;
    
    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;
    
    @Column(nullable = false)
    private String aula;
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<CursoMatriculado> estudiantesMatriculados;
    
    
    public Curso() {
    }

    public Curso(Long id, Asignatura asignatura, Profesor profesor, String periodo, String horario, Integer cupoMaximo,
            String aula, List<CursoMatriculado> estudiantesMatriculados) {
        this.id = id;
        this.asignatura = asignatura;
        this.profesor = profesor;
        this.periodo = periodo;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.aula = aula;
        this.estudiantesMatriculados = estudiantesMatriculados;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public List<CursoMatriculado> getEstudiantesMatriculados() {
        return estudiantesMatriculados;
    }

    public void setEstudiantesMatriculados(List<CursoMatriculado> estudiantesMatriculados) {
        this.estudiantesMatriculados = estudiantesMatriculados;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Asignatura getAsignatura() {
        return asignatura;
    }
    
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
    
    public Profesor getProfesor() {
        return profesor;
    }
    
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    public Integer getCupoMaximo() {
        return cupoMaximo;
    }
    
    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
    
    public String getAula() {
        return aula;
    }
    
    public void setAula(String aula) {
        this.aula = aula;
    }
    
    // public List<CursoMatriculado> getMatriculas() {
    //     return matriculas;
    // }
    
    // public void setMatriculas(List<CursoMatriculado> matriculas) {
    //     this.matriculas = matriculas;
    // }
}