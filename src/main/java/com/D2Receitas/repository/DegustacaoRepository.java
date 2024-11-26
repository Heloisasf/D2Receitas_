package com.D2Receitas.repository;

import com.D2Receitas.model.Degustacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DegustacaoRepository extends JpaRepository<Degustacao, Long> {
    List<Degustacao> findByDegustadorId(Long degustadorId);
} 