package com.D2Receitas.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class ReceitaIngrediente {
    @EmbeddedId
    private ReceitaIngredienteId id;

    private Double quantidade;

    // Getters e Setters
    public ReceitaIngredienteId getId() {
        return id;
    }

    public void setId(ReceitaIngredienteId id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
