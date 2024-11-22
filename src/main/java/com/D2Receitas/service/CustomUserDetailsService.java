package com.D2Receitas.service;

import com.D2Receitas.model.Funcionario;
import com.D2Receitas.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        if (funcionario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return org.springframework.security.core.userdetails.User
            .withUsername(funcionario.getUsername())
            .password(funcionario.getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + funcionario.getRole().getNome())))
            .build();
    }
} 