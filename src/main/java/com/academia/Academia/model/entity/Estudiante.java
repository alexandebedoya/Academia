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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 20, nullable = false)
    private String identificacion;

    @NotEmpty
    @Column(length = 50, nullable = false)
    private String apellidos;

    @NotEmpty
    @Column(length = 50, nullable = false)
    private String nombres;

    @NotNull
    @Column(name = "semestre_actual", nullable = false)
    private Integer semestreActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id")
    private ProgramaAcademico programa;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<AsignaturaCursada> asignaturasCursadas = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<CursoMatriculado> cursosMatriculados;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(Integer semestreActual) {
        this.semestreActual = semestreActual;
    }

    public ProgramaAcademico getPrograma() {
        return programa;
    }

    public void setPrograma(ProgramaAcademico programa) {
        this.programa = programa;
    }

    public List<AsignaturaCursada> getAsignaturasCursadas() {
        return asignaturasCursadas;
    }

    public void setAsignaturasCursadas(List<AsignaturaCursada> asignaturasCursadas) {
        this.asignaturasCursadas = asignaturasCursadas;
    }

    public List<CursoMatriculado> getCursosMatriculados() {
        return cursosMatriculados;
    }

    public void setCursosMatriculados(List<CursoMatriculado> cursosMatriculados) {
        this.cursosMatriculados = cursosMatriculados;
    }

    public Estudiante() {
    }

    public Estudiante(Long id, @NotEmpty String identificacion, @NotEmpty String apellidos, @NotEmpty String nombres,
            @NotNull Integer semestreActual, ProgramaAcademico programa, List<AsignaturaCursada> asignaturasCursadas,
            List<CursoMatriculado> cursosMatriculados) {
        this.id = id;
        this.identificacion = identificacion;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.semestreActual = semestreActual;
        this.programa = programa;
        this.asignaturasCursadas = asignaturasCursadas;
        this.cursosMatriculados = cursosMatriculados;
    }

}