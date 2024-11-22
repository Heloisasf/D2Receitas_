package com.D2Receitas.controller;

import com.D2Receitas.model.Funcionario;
import com.D2Receitas.model.Role;
import com.D2Receitas.repository.FuncionarioRepository;
import com.D2Receitas.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model, 
                              @RequestParam(required = false) String error,
                              @RequestParam(required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos!");
        }
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso!");
        }
        return "login";
    }

    @GetMapping("/dashboard/administrador/funcionarios")
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "dashboard/administrador/funcionarios/listar";
    }

    @GetMapping("/dashboard/administrador/funcionarios/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("roles", roleRepository.findAll());
        return "dashboard/administrador/funcionarios/adicionar";
    }

    @PostMapping("/dashboard/administrador/funcionarios/adicionar")
    public String adicionarFuncionario(@ModelAttribute Funcionario funcionario) {
        Role role = roleRepository.findById(funcionario.getRole().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cargo inválido"));
        
        funcionario.setRole(role);
        funcionario.setPassword(passwordEncoder.encode(funcionario.getPassword()));
        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/administrador/funcionarios";
    }

    @GetMapping("/dashboard/administrador/funcionarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("funcionario", funcionario);
        return "dashboard/administrador/funcionarios/editar";
    }

    @PostMapping("/dashboard/administrador/funcionarios/editar/{id}")
    public String atualizarFuncionario(@PathVariable Long id, @ModelAttribute Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
            
        if (funcionario.getPassword() != null && !funcionario.getPassword().isEmpty()) {
            funcionario.setPassword(passwordEncoder.encode(funcionario.getPassword()));
        } else {
            funcionario.setPassword(funcionarioExistente.getPassword());
        }
        
        funcionarioRepository.save(funcionario);
        return "redirect:/dashboard/administrador/funcionarios";
    }

    @GetMapping("/dashboard/administrador/funcionarios/excluir/{id}")
    public String excluirFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/dashboard/administrador/funcionarios";
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "register";
    }

    @PostMapping("/registro")
    public String registerFuncionario(@ModelAttribute("funcionario") Funcionario funcionario) {
        funcionario.setCreatedAt(LocalDateTime.now());
        try {
            Role adminRole = roleRepository.findByNome("ADMINISTRADOR");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setNome("ADMINISTRADOR");
                adminRole.setDescricao("Administrador do sistema");
                adminRole = roleRepository.save(adminRole);
            }
            
            funcionario.setRole(adminRole);
            funcionario.setEnabled(true);
            funcionario.setPassword(passwordEncoder.encode(funcionario.getPassword()));
            
            funcionarioRepository.save(funcionario);
            return "redirect:/login?registrosucesso=true";
        } catch (Exception e) {
            return "register";
        }
    }
} 