package com.D2Receitas.controller;
    
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import java.util.List;
import com.D2Receitas.model.Livro;
import com.D2Receitas.repository.LivroRepository;
import com.D2Receitas.repository.FuncionarioRepository;

@Controller
@RequestMapping("/dashboard/administrador/livros")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminLivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public String listarLivros(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String editor,
            Model model) {
            
        List<Livro> livros;
        
        if (status != null && !status.isEmpty()) {
            boolean publicado = status.equals("publicado");
            livros = livroRepository.findByPublicado(publicado);
        } else {
            livros = livroRepository.findAll();
        }
        
        model.addAttribute("livros", livros);
        return "dashboard/administrador/livros";
    }
}