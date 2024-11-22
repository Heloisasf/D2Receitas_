package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.D2Receitas.model.Referencia;
import com.D2Receitas.repository.ReferenciaRepository;
import com.D2Receitas.repository.FuncionarioRepository;
import com.D2Receitas.repository.RestauranteRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/dashboard/administrador/referencias")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ReferenciaController {

    @Autowired
    private ReferenciaRepository referenciaRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public String listarReferencias(Model model) {
        model.addAttribute("referencias", referenciaRepository.findAll());
        return "dashboard/administrador/referencias/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("referencia", new Referencia());
        model.addAttribute("cozinheiros", funcionarioRepository.findByRoleNome("COZINHEIRO"));
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "dashboard/administrador/referencias/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarReferencia(@ModelAttribute Referencia referencia) {
        referenciaRepository.save(referencia);
        return "redirect:/dashboard/administrador/referencias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Referencia referencia = referenciaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("referencia", referencia);
        model.addAttribute("cozinheiros", funcionarioRepository.findByRoleNome("COZINHEIRO"));
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "dashboard/administrador/referencias/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarReferencia(@PathVariable Long id, @ModelAttribute Referencia referencia) {
        referenciaRepository.save(referencia);
        return "redirect:/dashboard/administrador/referencias";
    }

    @GetMapping("/excluir/{id}")
    public String excluirReferencia(@PathVariable Long id) {
        referenciaRepository.deleteById(id);
        return "redirect:/dashboard/administrador/referencias";
    }
} 