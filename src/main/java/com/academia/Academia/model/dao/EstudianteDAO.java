package com.academia.Academia.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Estudiante;

@Repository
public interface EstudianteDAO extends JpaRepository<Estudiante, Long> {
}