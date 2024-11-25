package com.D2Receitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.D2Receitas.model.Receita;

//import jakarta.persistence.Embeddable;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}

