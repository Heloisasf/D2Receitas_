package com.D2Receitas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idreceita;  // idreceita como identificador único (auto-increment)

    @ManyToOne
    private Funcionario cozinheiro;  // Relacionamento com a entidade Funcionario (id_cozinheiro)

    private String nome;  // Nome da receita

    @Column(name = "data_criacao")
    private LocalDate data_criacao;  // Data de criação da receita

    @ManyToOne
    private Categoria categoria;  // Relacionamento com a entidade Categoria (id_categoria)

    private String modoPreparo;  // Modo de preparo da receita

    private int qtdePorcao;  // Quantidade de porções

    private boolean indReceitaInedita;  // Indicador de receita inédita (tinyint(1) convertido para boolean)

    // Getters e setters
    public int getIdreceita() {
        return idreceita;
    }

    public void setIdreceita(int idreceita) {
        this.idreceita = idreceita;
    }

    public Funcionario getCozinheiro() {
        return cozinheiro;
    }

    public void setCozinheiro(Funcionario cozinheiro) {
        this.cozinheiro = cozinheiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCriacao() {
        return data_criacao;
    }

    public void setDataCriacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public int getQtdePorcao() {
        return qtdePorcao;
    }

    public void setQtdePorcao(int qtdePorcao) {
        this.qtdePorcao = qtdePorcao;
    }

    public boolean isIndReceitaInedita() {
        return indReceitaInedita;
    }

    public void setIndReceitaInedita(boolean indReceitaInedita) {
        this.indReceitaInedita = indReceitaInedita;
    }
}
