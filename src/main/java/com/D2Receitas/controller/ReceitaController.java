package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.D2Receitas.model.Funcionario;
import com.D2Receitas.model.Ingrediente;
import com.D2Receitas.model.Medida;
import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.CategoriaRepository;
import com.D2Receitas.repository.IngredienteRepository;
import com.D2Receitas.repository.MedidaRepository;
import com.D2Receitas.repository.ReceitaRepository;
import com.D2Receitas.repository.FuncionarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/cozinheiro/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaRepository receitaRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    @Autowired
    private MedidaRepository medidaRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public String listarReceitas(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        
        if (funcionario.getRole().getNome().equals("COZINHEIRO")) {
            model.addAttribute("receitas", receitaRepository.findByCozinheiroId(funcionario.getId()));
        } else {
            model.addAttribute("receitas", receitaRepository.findAll());
        }
        
        return "dashboard/cozinheiro/receitas/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("receita", new Receita());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("ingredientes", ingredienteRepository.findAll());
        model.addAttribute("medidas", medidaRepository.findAll());
        return "dashboard/cozinheiro/receitas/adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarReceita(@ModelAttribute Receita receita, 
                                 @RequestParam("ingredientesIds[]") List<Long> ingredientesIds,
                                 @RequestParam("quantidades[]") List<Double> quantidades,
                                 @RequestParam("medidasIds[]") List<Long> medidasIds,
                                 Authentication authentication) {
        
        String username = authentication.getName();
        Funcionario cozinheiro = funcionarioRepository.findByUsername(username);
        
        receita.setCozinheiro(cozinheiro);
        receita.setDataCriacao(LocalDateTime.now());
        
        List<Ingrediente> ingredientes = ingredientesIds.stream()
            .map(id -> ingredienteRepository.findById(id).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        List<Medida> medidas = medidasIds.stream()
            .map(id -> medidaRepository.findById(id).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        receita.setIngredientes(ingredientes);
        receita.setQuantidades(quantidades);
        receita.setMedidas(medidas);
        
        receitaRepository.save(receita);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesReceita(@PathVariable Long id, Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + id));
        
        if (!receita.getCozinheiro().getId().equals(funcionario.getId()) && 
            funcionario.getRole().getNome().equals("COZINHEIRO")) {
            return "redirect:/dashboard/cozinheiro/receitas?error=unauthorized";
        }
        
        model.addAttribute("receita", receita);
        return "dashboard/cozinheiro/receitas/detalhes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + id));
        
        if (!receita.getCozinheiro().getId().equals(funcionario.getId())) {
            return "redirect:/dashboard/cozinheiro/receitas?error=unauthorized";
        }
        
        model.addAttribute("receita", receita);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("ingredientes", ingredienteRepository.findAll());
        model.addAttribute("medidas", medidaRepository.findAll());
        return "dashboard/cozinheiro/receitas/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarReceita(@PathVariable Long id,
                             @ModelAttribute Receita receita,
                             @RequestParam("ingredientesIds[]") List<Long> ingredientesIds,
                             @RequestParam("quantidades[]") List<Double> quantidades,
                             @RequestParam("medidasIds[]") List<Long> medidasIds,
                             Authentication authentication) {
        
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        
        Receita receitaExistente = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + id));
        
        if (!receitaExistente.getCozinheiro().getId().equals(funcionario.getId())) {
            return "redirect:/dashboard/cozinheiro/receitas?error=unauthorized";
        }
        
        receitaExistente.setNome(receita.getNome());
        receitaExistente.setDescricao(receita.getDescricao());
        receitaExistente.setQtdePorcao(receita.getQtdePorcao());
        receitaExistente.setModoPreparo(receita.getModoPreparo());
        receitaExistente.setCategoria(receita.getCategoria());
        
        List<Ingrediente> ingredientes = ingredientesIds.stream()
            .map(ingredienteId -> ingredienteRepository.findById(ingredienteId).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        List<Medida> medidas = medidasIds.stream()
            .map(medidaId -> medidaRepository.findById(medidaId).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        receitaExistente.setIngredientes(ingredientes);
        receitaExistente.setQuantidades(quantidades);
        receitaExistente.setMedidas(medidas);
        
        receitaRepository.save(receitaExistente);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirReceita(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Receita inválida: " + id));
        
        if (!receita.getCozinheiro().getId().equals(funcionario.getId())) {
            return "redirect:/dashboard/cozinheiro/receitas?error=unauthorized";
        }
        
        receitaRepository.deleteById(id);
        return "redirect:/dashboard/cozinheiro/receitas";
    }
} 