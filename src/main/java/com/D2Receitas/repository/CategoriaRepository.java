package com.D2Receitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.D2Receitas.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
} 