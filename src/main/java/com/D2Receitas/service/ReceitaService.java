package com.D2Receitas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.ReceitaRepository;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public Receita buscarPorId(Long idreceita) {
        return receitaRepository.findById(idreceita)
               .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }
}
