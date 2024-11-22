package com.D2Receitas.repository;

import com.D2Receitas.model.Funcionario;
import com.D2Receitas.model.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario findByUsername(String username);
    List<Funcionario> findByRole(Role role);
    List<Funcionario> findByRoleNome(String roleNome);
} 