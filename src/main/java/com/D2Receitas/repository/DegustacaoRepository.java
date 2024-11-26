package com.D2Receitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.D2Receitas.model.Degustacao;
import com.D2Receitas.model.Funcionario;

@Repository
public interface DegustacaoRepository extends JpaRepository<Degustacao, Long> {
    List<Degustacao> findByDegustadorId(Long degustadorId);
    List<Degustacao> findByDegustadorOrderByDataDegustacaoDesc(Funcionario degustador);
    List<Degustacao> findByDegustador(Funcionario degustador);
} 