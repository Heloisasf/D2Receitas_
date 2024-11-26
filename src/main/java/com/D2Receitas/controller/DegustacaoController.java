package com.D2Receitas.controller;

import com.D2Receitas.model.Degustacao;
import com.D2Receitas.model.Funcionario;
import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.DegustacaoRepository;
import com.D2Receitas.repository.FuncionarioRepository;
import com.D2Receitas.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/degustador")
public class DegustacaoController {

    @Autowired
    private DegustacaoRepository degustacaoRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);

        List<Degustacao> degustacoes = degustacaoRepository.findByDegustadorId(degustador.getId());

        Set<Long> receitasDegustadasIds = degustacoes.stream()
            .map(degustacao -> degustacao.getReceita().getIdreceita())
            .collect(Collectors.toSet());

        List<Receita> receitasDisponiveis = receitaRepository.findAll().stream()
            .filter(receita -> !receitasDegustadasIds.contains(receita.getIdreceita()))
            .collect(Collectors.toList());

        model.addAttribute("receitas", receitasDisponiveis);
        model.addAttribute("degustacao", new Degustacao());
        return "dashboard/degustador/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarDegustacao(@ModelAttribute Degustacao degustacao, @RequestParam Long receitaId, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        Receita receita = receitaRepository.findById(receitaId).orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + receitaId));

        degustacao.setDegustador(degustador);
        degustacao.setReceita(receita);
        degustacao.setDataDegustacao(LocalDateTime.now());

        degustacaoRepository.save(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }

    @GetMapping("/listar")
    public String listarDegustacoes(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        List<Degustacao> degustacoes = degustacaoRepository.findByDegustadorId(degustador.getId());
        model.addAttribute("degustacoes", degustacoes);
        return "dashboard/degustador/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Degustacao degustacao = degustacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        model.addAttribute("degustacao", degustacao);
        return "dashboard/degustador/editar";
    }

    @PostMapping("/editar/{id}")
    public String editarDegustacao(@PathVariable Long id, @ModelAttribute Degustacao degustacaoAtualizada) {
        Degustacao degustacao = degustacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        degustacao.setNota(degustacaoAtualizada.getNota());
        degustacaoRepository.save(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirDegustacao(@PathVariable Long id) {
        Degustacao degustacao = degustacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        degustacaoRepository.delete(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }
} 