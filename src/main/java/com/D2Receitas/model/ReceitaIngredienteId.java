package com.D2Receitas.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReceitaIngredienteId implements Serializable {
    private int receitaId;
    private int ingredienteId;
    private int medidaId;

    // Getters e Setters
    public int getReceitaId() {
        return receitaId;
    }

    public void setReceitaId(int receitaId) {
        this.receitaId = receitaId;
    }

    public int getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(int ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public int getMedidaId() {
        return medidaId;
    }

    public void setMedidaId(int medidaId) {
        this.medidaId = medidaId;
    }

    // hashCode e equals (necessários para chave composta)
    @Override
    public int hashCode() {
        return Objects.hash(receitaId, ingredienteId, medidaId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReceitaIngredienteId that = (ReceitaIngredienteId) obj;
        return Objects.equals(receitaId, that.receitaId) &&
               Objects.equals(ingredienteId, that.ingredienteId) &&
               Objects.equals(medidaId, that.medidaId);
    }
}
