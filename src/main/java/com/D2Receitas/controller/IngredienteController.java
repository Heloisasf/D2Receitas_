package com.D2Receitas.controller;

import com.D2Receitas.model.Ingrediente;
import com.D2Receitas.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/administrador/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String listarIngredientes(Model model) {
        model.addAttribute("ingredientes", ingredienteRepository.findAll());
        return "dashboard/administrador/ingredientes/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "dashboard/administrador/ingredientes/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarIngrediente(@ModelAttribute Ingrediente ingrediente) {
        ingredienteRepository.save(ingrediente);
        return "redirect:/dashboard/administrador/ingredientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de ingrediente inválido: " + id));
        model.addAttribute("ingrediente", ingrediente);
        return "dashboard/administrador/ingredientes/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarIngrediente(@PathVariable Long id, @ModelAttribute Ingrediente ingrediente) {
        Ingrediente ingredienteExistente = ingredienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de ingrediente inválido: " + id));
        ingredienteExistente.setNome(ingrediente.getNome());
        ingredienteExistente.setDescricao(ingrediente.getDescricao());
        ingredienteRepository.save(ingredienteExistente);
        
        return "redirect:/dashboard/administrador/ingredientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirIngrediente(@PathVariable Long id) {
        ingredienteRepository.deleteById(id);
        return "redirect:/dashboard/administrador/ingredientes";
    }
} 