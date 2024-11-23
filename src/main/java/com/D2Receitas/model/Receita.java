package com.D2Receitas.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private Categoria categoria;

    private String descricao;

    @ElementCollection
    private List<Ingrediente> ingredientes;

    private String medida;

    private String modoPreparo;

    @Lob
    private byte[] midia;

    private int numeroPorcoes;

    private boolean inedita;
}
