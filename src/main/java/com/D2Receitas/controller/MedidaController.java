package com.D2Receitas.controller;

import com.D2Receitas.model.Medida;
import com.D2Receitas.repository.MedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/administrador/medidas")
public class MedidaController {

    @Autowired
    private MedidaRepository medidaRepository;

    @GetMapping
    public String listarMedidas(Model model) {
        model.addAttribute("medidas", medidaRepository.findAll());
        return "dashboard/administrador/medidas/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("medida", new Medida());
        return "dashboard/administrador/medidas/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarMedida(@ModelAttribute Medida medida) {
        medidaRepository.save(medida);
        return "redirect:/dashboard/administrador/medidas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Medida medida = medidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de medida inválido: " + id));
        model.addAttribute("medida", medida);
        return "dashboard/administrador/medidas/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarMedida(@PathVariable Long id, @ModelAttribute Medida medida) {
        Medida medidaExistente = medidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de medida inválido: " + id));
        medidaExistente.setNome(medida.getNome());
        medidaRepository.save(medidaExistente);
        return "redirect:/dashboard/administrador/medidas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMedida(@PathVariable Long id) {
        medidaRepository.deleteById(id);
        return "redirect:/dashboard/administrador/medidas";
    }
}