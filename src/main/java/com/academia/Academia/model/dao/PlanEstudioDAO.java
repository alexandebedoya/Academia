package com.academia.Academia.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.PlanEstudio;

@Repository
public interface PlanEstudioDAO extends JpaRepository<PlanEstudio, Long> {
}