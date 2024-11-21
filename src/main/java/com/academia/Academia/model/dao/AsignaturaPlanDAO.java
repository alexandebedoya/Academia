package com.academia.Academia.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.academia.Academia.model.entity.AsignaturaPlan;

@Repository
public interface AsignaturaPlanDAO extends JpaRepository<AsignaturaPlan, Long> {
    List<AsignaturaPlan> findBySemestreNivel(Integer semestreNivel);
    
    @Query("SELECT ap FROM AsignaturaPlan ap WHERE ap.prerrequisito.id = ?1")
    List<AsignaturaPlan> findByprerrequisitoId(Long prerequisitoId);
    
    @Query("SELECT ap FROM AsignaturaPlan ap WHERE ap.plan.id = ?1 ORDER BY ap.semestreNivel, ap.asignatura.nombre")
    List<AsignaturaPlan> findByPlanEstudioIdOrdered(Long planId);
    
    List<AsignaturaPlan> findByPlanId(Long planId); // Correct method

    List<AsignaturaPlan> findByPrerrequisitoId(Long prerequisitoId);

}