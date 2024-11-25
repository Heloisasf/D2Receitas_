package com.D2Receitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.D2Receitas.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByCozinheiroId(Long cozinheiroId);
    List<Receita> findAll();
} 