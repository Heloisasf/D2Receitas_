package com.D2Receitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.D2Receitas.model.Livro;
import com.D2Receitas.model.Role;
import com.D2Receitas.model.Funcionario;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByEditor(Funcionario editor);
    List<Livro> findByEditorAndPublicado(Funcionario editor, boolean publicado);
    List<Livro> findByPublicado(boolean publicado);
    List<Livro> findByEditorRole(Role role);
} 