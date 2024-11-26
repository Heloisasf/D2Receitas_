package com.D2Receitas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String nome;
    
    @ManyToMany
    @JoinTable(
        name = "livro_receitas",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "receita_id")
    )
    private List<Receita> receitas;
    
    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Funcionario editor;
    
    private LocalDateTime dataCriacao;
    
    private boolean publicado;
    
    @Column(name = "isbn")
    private String isbn;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public Funcionario getEditor() {
        return editor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public void setEditor(Funcionario editor) {
        this.editor = editor;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
} 