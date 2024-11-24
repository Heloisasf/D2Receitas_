package com.D2Receitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.D2Receitas.model.ReceitaIngrediente;
import com.D2Receitas.model.ReceitaIngredienteId;

public interface ReceitaIngredienteRepository extends JpaRepository<ReceitaIngrediente, ReceitaIngredienteId> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário
}
