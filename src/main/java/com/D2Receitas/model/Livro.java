package com.D2Receitas.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "isbn", unique = true)
    private String isbn;
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false)
    private Boolean publicado = false;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "livros_receitas",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "receita_id")
    )
    private List<Receita> receitas = new ArrayList<>();
    
    // Getters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas != null ? receitas : new ArrayList<>();
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    // Métodos auxiliares para gerenciar a relação bidirecional
    public void addReceita(Receita receita) {
        if (receita != null) {
            this.receitas.add(receita);
        }
    }

    public void removeReceita(Receita receita) {
        if (receita != null) {
            this.receitas.remove(receita);
        }
    }

    // Construtor vazio necessário para JPA
    public Livro() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Construtor com parâmetros
    public Livro(String titulo, List<Receita> receitas) {
        this.titulo = titulo;
        setReceitas(receitas);
        this.dataCriacao = LocalDateTime.now();
    }

    // Equals e HashCode baseados no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return id != null && id.equals(livro.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
