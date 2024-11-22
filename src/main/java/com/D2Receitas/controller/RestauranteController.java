package com.D2Receitas.controller;

import com.D2Receitas.model.Restaurante;
import com.D2Receitas.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/administrador/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public String listarRestaurantes(Model model) {
        model.addAttribute("restaurante", restauranteRepository.findAll());
        return "dashboard/administrador/restaurantes/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        return "dashboard/administrador/restaurantes/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarRestaurante(@ModelAttribute Restaurante restaurante) {
        try {
            restauranteRepository.save(restaurante);
            return "redirect:/dashboard/administrador/restaurantes";
        } catch (Exception ex) {
            return "redirect:/dashboard/administrador/restaurantes/adicionar?error";
        }
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        model.addAttribute("restaurante", restaurante);
        return "dashboard/administrador/restaurantes/editar";
    }

    @PostMapping("/editar/{id}")
    public String updateRestaurante(@PathVariable Long id, @ModelAttribute Restaurante restaurante) {
        restaurante.setId(id);
        
        restauranteRepository.save(restaurante);
        
        return "redirect:/dashboard/administrador/restaurantes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirRestaurante(@PathVariable Long id) {
        try {
            restauranteRepository.deleteById(id);
            return "redirect:/dashboard/administrador/restaurantes";
        } catch (Exception ex) {
            return "redirect:/dashboard/administrador/restaurantes?error";
        }
    }
} 