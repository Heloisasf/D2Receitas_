package com.D2Receitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.D2Receitas.model.ReceitaIngrediente;
import com.D2Receitas.model.ReceitaIngredienteId;

public interface ReceitaIngredienteRepository extends JpaRepository<ReceitaIngrediente, ReceitaIngredienteId> {
    @Query("SELECT ri FROM ReceitaIngrediente ri WHERE ri.id.receitaId = :receitaId")
    List<ReceitaIngrediente> findByReceitaId(@Param("receitaId") long receitaId);
}
