package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.D2Receitas.model.Funcionario;
import com.D2Receitas.repository.FuncionarioRepository;

import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class DashboardController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        
        return switch (role) {
            case "ROLE_ADMINISTRADOR" -> "redirect:/dashboard/administrador";
            case "ROLE_COZINHEIRO" -> "redirect:/dashboard/cozinheiro";
            case "ROLE_DEGUSTADOR" -> "redirect:/dashboard/degustador";
            case "ROLE_EDITOR" -> "redirect:/dashboard/editor";
            default -> "redirect:/login";
        };
    }

    @GetMapping("/dashboard/administrador")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String administradorDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        model.addAttribute("funcionario", funcionario);
        return "dashboard/administrador";
    }

    @GetMapping("/dashboard/cozinheiro")
    @PreAuthorize("hasRole('COZINHEIRO')")
    public String cozinheiroDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        model.addAttribute("funcionario", funcionario);
        return "dashboard/cozinheiro";
    }


    @GetMapping("/dashboard/degustador")
    @PreAuthorize("hasRole('DEGUSTADOR')")
    public String degustadorDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        model.addAttribute("funcionario", funcionario);
        return "dashboard/degustador";
    }

    @GetMapping("/dashboard/editor")
    @PreAuthorize("hasRole('EDITOR')")
    public String editorDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        model.addAttribute("funcionario", funcionario);
        return "dashboard/editor";
    }
}
