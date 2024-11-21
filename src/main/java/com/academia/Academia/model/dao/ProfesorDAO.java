package com.academia.Academia.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.Profesor;

@Repository
public interface ProfesorDAO extends JpaRepository<Profesor, Long> {
}