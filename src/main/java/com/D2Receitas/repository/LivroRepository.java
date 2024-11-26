package com.D2Receitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.D2Receitas.model.Livro;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.receitas r " +
           "LEFT JOIN FETCH r.degustacoes d " +
           "LEFT JOIN FETCH d.degustador " +
           "ORDER BY l.dataCriacao DESC")
    List<Livro> findAllWithReceitas();
    
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    List<Livro> findByIsbnIsNotNull();
    
    List<Livro> findByIsbnIsNull();
    
    List<Livro> findByPublicado(boolean publicado);
    
    List<Livro> findByPublicadoFalse();
}