package com.D2Receitas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.D2Receitas.model.Degustacao;
import com.D2Receitas.model.Funcionario;
import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.DegustacaoRepository;
import com.D2Receitas.repository.FuncionarioRepository;
import com.D2Receitas.repository.ReceitaRepository;

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
    public String mostrarFormularioDegustacao(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        // Busca todas as receitas
        List<Receita> todasReceitas = receitaRepository.findAll();
        
        // Busca todas as degustações do degustador
        List<Degustacao> degustacoes = degustacaoRepository.findByDegustador(degustador);
        
        // Cria uma lista de IDs de receitas já avaliadas
        List<Long> receitasAvaliadas = degustacoes.stream()
            .map(d -> d.getReceita().getIdreceita())
            .collect(Collectors.toList());
        
        // Filtra apenas as receitas não avaliadas
        List<Receita> receitasNaoAvaliadas = todasReceitas.stream()
            .filter(receita -> !receitasAvaliadas.contains(receita.getIdreceita()))
            .collect(Collectors.toList());
        
        model.addAttribute("receitas", receitasNaoAvaliadas);
        return "dashboard/degustador/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarDegustacao(
            @RequestParam("receitaId") Long receitaId,
            @RequestParam("nota") Integer nota,
            Authentication authentication) {
        
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        Receita receita = receitaRepository.findById(receitaId)
            .orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + receitaId));
        
        Degustacao degustacao = new Degustacao();
        degustacao.setReceita(receita);
        degustacao.setDegustador(degustador);
        degustacao.setNota(nota);
        degustacao.setDataDegustacao(LocalDateTime.now());
        
        degustacaoRepository.save(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }

    @GetMapping("/listar")
    public String listarDegustacoes(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        List<Degustacao> degustacoes = degustacaoRepository.findByDegustadorOrderByDataDegustacaoDesc(degustador);
        model.addAttribute("degustacoes", degustacoes);
        
        return "dashboard/degustador/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        Degustacao degustacao = degustacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        
        if (!degustacao.getDegustador().getId().equals(degustador.getId())) {
            return "redirect:/dashboard/degustador/listar?error=unauthorized";
        }
        
        model.addAttribute("degustacao", degustacao);
        return "dashboard/degustador/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarDegustacao(@PathVariable Long id,
                                     @RequestParam("nota") Integer nota,
                                     Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        Degustacao degustacao = degustacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        
        if (!degustacao.getDegustador().getId().equals(degustador.getId())) {
            return "redirect:/dashboard/degustador/listar?error=unauthorized";
        }
        
        degustacao.setNota(nota);
        degustacao.setDataDegustacao(LocalDateTime.now());
        
        degustacaoRepository.save(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }

    @PostMapping("/excluir/{id}")
    public String excluirDegustacao(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Funcionario degustador = funcionarioRepository.findByUsername(username);
        
        Degustacao degustacao = degustacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Degustação inválida: " + id));
        
        if (!degustacao.getDegustador().getId().equals(degustador.getId())) {
            return "redirect:/dashboard/degustador/listar?error=unauthorized";
        }
        
        degustacaoRepository.delete(degustacao);
        return "redirect:/dashboard/degustador/listar";
    }
} 