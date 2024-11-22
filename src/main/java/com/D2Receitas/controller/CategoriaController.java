package com.D2Receitas.controller;

import com.D2Receitas.model.Categoria;
import com.D2Receitas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/administrador/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "dashboard/administrador/categorias/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "dashboard/administrador/categorias/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarCategoria(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/dashboard/administrador/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de categoria inválido: " + id));
        model.addAttribute("categoria", categoria);
        return "dashboard/administrador/categorias/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id, @ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/dashboard/administrador/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/dashboard/administrador/categorias";
    }
} 