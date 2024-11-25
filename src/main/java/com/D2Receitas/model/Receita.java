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
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    private String descricao;
    
    @ManyToMany
    @JoinTable(
        name = "receita_ingredientes",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes;
    
    @ElementCollection
    @CollectionTable(name = "receita_quantidades")
    private List<Double> quantidades;
    
    @ManyToMany
    @JoinTable(
        name = "receita_medidas",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "medida_id")
    )
    private List<Medida> medidas;
    
    private String modoPreparo;
    
    @ManyToOne
    @JoinColumn(name = "cozinheiro_id")
    private Funcionario cozinheiro;
    
    private Integer qtdePorcao;
    
    public Long getIdreceita() {
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