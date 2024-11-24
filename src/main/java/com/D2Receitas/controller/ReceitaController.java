package com.D2Receitas.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.D2Receitas.model.Categoria;
import com.D2Receitas.model.Ingrediente;
import com.D2Receitas.model.Medida;
import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.CategoriaRepository;
import com.D2Receitas.repository.IngredienteRepository;
import com.D2Receitas.repository.MedidaRepository;
import com.D2Receitas.repository.ReceitaRepository;

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

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionar(Model model) {
        // Carregar categorias, ingredientes e medidas
        List<Categoria> categorias = categoriaRepository.findAll();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        List<Medida> medidas = medidaRepository.findAll();

        model.addAttribute("receita", new Receita());
        model.addAttribute("categorias", categorias);
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("medidas", medidas);

        return "dashboard/cozinheiro/receitas/adicionar";
    }
    

    // Listar todas as receitas
    @GetMapping
    public String listarReceitas(Model model) {
        model.addAttribute("receitas", receitaRepository.findAll());
        return "dashboard/cozinheiro/receitas/listar";
    }

    // Adicionar a nova receita ao banco de dados
    @PostMapping("/adicionar")
    public String adicionarReceita(@ModelAttribute Receita receita) {
        receitaRepository.save(receita);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    // Mostrar o formulário para editar uma receita
    @GetMapping("/editar/{idreceita}")
    public String mostrarFormularioEditar(@PathVariable Long idreceita, Model model) {
        Receita receita = receitaRepository.findById(idreceita)
            .orElseThrow(() -> new IllegalArgumentException("ID de receita inválido: " + idreceita));
        model.addAttribute("receita", receita);
        return "dashboard/cozinheiro/receitas/editar";
    }

    // Atualizar a receita no banco de dados
    @PostMapping("/editar/{idreceita}")
    public String atualizarReceita(@PathVariable Long idreceita, @ModelAttribute Receita receita) {
        Receita receitaExistente = receitaRepository.findById(idreceita)
            .orElseThrow(() -> new IllegalArgumentException("ID de receita inválido: " + idreceita));

        // Atualizando os dados da receita
        receitaExistente.setNome(receita.getNome());
        receitaExistente.setCategoria(receita.getCategoria());
        receitaExistente.setModoPreparo(receita.getModoPreparo());
        receitaExistente.setQtdePorcao(receita.getQtdePorcao());
        receitaExistente.setIndReceitaInedita(receita.isIndReceitaInedita());

        receitaRepository.save(receitaExistente);
        return "redirect:/dashboard/cozinheiro/receitas";
    }

    // Excluir uma receita
    @GetMapping("/excluir/{idreceita}")
    public String excluirReceita(@PathVariable Long idreceita) {
        receitaRepository.deleteById(idreceita);
        return "redirect:/dashboard/cozinheiro/receitas";
    }
}
