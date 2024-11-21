package com.academia.Academia.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Asignatura;

@Repository
public interface AsignaturaDAO extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByDepartamento(String departamento);

}