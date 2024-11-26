package com.D2Receitas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "degustacoes")
public class Degustacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;
    
    @ManyToOne
    @JoinColumn(name = "degustador_id")
    private Funcionario degustador;
    
    private Integer nota;
    
    private LocalDateTime dataDegustacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Funcionario getDegustador() {
        return degustador;
    }

    public void setDegustador(Funcionario degustador) {
        this.degustador = degustador;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataDegustacao() {
        return dataDegustacao;
    }

    public void setDataDegustacao(LocalDateTime dataDegustacao) {
        this.dataDegustacao = dataDegustacao;
    }
} 