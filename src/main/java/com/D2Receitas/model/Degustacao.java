package com.D2Receitas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Degustacao {
	
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

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public LocalDateTime getDataDegustacao() {
		return dataDegustacao;
	}

	public void setDataDegustacao(LocalDateTime dataDegustacao) {
		this.dataDegustacao = dataDegustacao;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Receita receita;

    @ManyToOne
    private Funcionario degustador;

    private int nota;

    private LocalDateTime dataDegustacao;
} 