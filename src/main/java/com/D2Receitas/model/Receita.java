package com.D2Receitas.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinTable;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreceita;
    
    private String nome;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "ind_receita_inedita")
    private boolean indReceitaInedita;
    
    @ManyToOne
    private Categoria categoria;  // Relacionamento com a entidade Categoria (id_categoria)

    private String modoPreparo;  // Modo de preparo da receita

    private String descricao;  // Modo de preparo da receita

    private int qtdePorcao;  // Quantidade de porções

    private boolean indReceitaInedita;  // Indicador de receita inédita (tinyint(1) convertido para boolean)

    // Getters e setters
    public int getIdreceita() {
        return idreceita;
    }

	public void setIdreceita(Long idreceita) {
		this.idreceita = idreceita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isIndReceitaInedita() {
		return indReceitaInedita;
	}

	public void setIndReceitaInedita(boolean indReceitaInedita) {
		this.indReceitaInedita = indReceitaInedita;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<Double> getQuantidades() {
		return quantidades;
	}

	public void setQuantidades(List<Double> quantidades) {
		this.quantidades = quantidades;
	}

	public List<Medida> getMedidas() {
		return medidas;
	}

	public void setMedidas(List<Medida> medidas) {
		this.medidas = medidas;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public Funcionario getCozinheiro() {
		return cozinheiro;
	}

	public void setCozinheiro(Funcionario cozinheiro) {
		this.cozinheiro = cozinheiro;
	}

	public Integer getQtdePorcao() {
		return qtdePorcao;
	}

	public void setQtdePorcao(Integer qtdePorcao) {
		this.qtdePorcao = qtdePorcao;
	}
} 