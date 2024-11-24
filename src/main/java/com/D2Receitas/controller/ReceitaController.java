/*package com.D2Receitas.controller;

import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/cozinheiro/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping
    public String listarReceitas(Model model) {
        model.addAttribute("receitas", receitaRepository.findAll());
        return "dashboard/cozinheiro/receitas/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("receita", new Receita());
        return "dashboard/cozinheiro/receitas/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarReceita(@ModelAttribute Receita receita) {
        receitaRepository.save(receita);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de receita inválido: " + id));
        model.addAttribute("receita", receita);
        return "dashboard/cozinheiro/receitas/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarReceita(@PathVariable Long id, @ModelAttribute Receita receita) {
        Receita receitaExistente = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de receita inválido: " + id));
        
        receitaExistente.setNome(receita.getNome());
        receitaExistente.setCategoria(receita.getCategoria());
        receitaExistente.setDescricao(receita.getDescricao());
        receitaExistente.setIngredientes(receita.getIngredientes());
        receitaExistente.setMedidas(receita.getMedidas());
        receitaExistente.setModoPreparo(receita.getModoPreparo());
        receitaExistente.setMidia(receita.getMidia());
        receitaExistente.setNumeroPorcoes(receita.getNumeroPorcoes());
        receitaExistente.setInedita(receita.isInedita());

        receitaRepository.save(receitaExistente);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirReceita(@PathVariable Long id) {
        receitaRepository.deleteById(id);
        return "redirect:/dashboard/cozinheiro/receitas";
    }
}*/
