package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.D2Receitas.model.Role;
import com.D2Receitas.repository.RoleRepository;

@Controller
@RequestMapping("/dashboard/administrador/cargos")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listarCargos(Model model) {
        model.addAttribute("cargos", roleRepository.findAll());
        return "dashboard/administrador/cargos/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("cargo", new Role());
        return "dashboard/administrador/cargos/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarCargo(@ModelAttribute Role cargo) {
        roleRepository.save(cargo);
        return "redirect:/dashboard/administrador/cargos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Role cargo = roleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cargo", cargo);
        return "dashboard/administrador/cargos/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCargo(@PathVariable Long id, @ModelAttribute Role cargo) {
        cargo.setId(id);
        roleRepository.save(cargo);
        return "redirect:/dashboard/administrador/cargos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCargo(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return "redirect:/dashboard/administrador/cargos";
    }
} 