package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.D2Receitas.model.Livro;
import com.D2Receitas.model.Receita;
import com.D2Receitas.repository.ReceitaRepository;
import com.D2Receitas.service.LivroService;

import java.util.List;

@Controller
@RequestMapping("/dashboard/editor/livros")
public class EditorController {
    
    @Autowired
    private LivroService livroService;
    
    @Autowired
    private ReceitaRepository receitaRepository;
    
    @GetMapping("")
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroService.buscarTodos());
        return "dashboard/editor/livros/listar";
    }
    
    @GetMapping("/criar")
    public String mostrarFormularioCriarLivro(Model model) {
        List<Receita> receitas = receitaRepository.findAllWithDegustacoes();
        model.addAttribute("receitas", receitas);
        return "dashboard/editor/livros/criar";
    }
    
    @PostMapping("/criar")
    public String criarLivro(@RequestParam String titulo, 
                            @RequestParam List<Long> receitasIds) {
        livroService.criarLivro(titulo, receitasIds);
        return "redirect:/dashboard/editor/livros";
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadLivro(@PathVariable Long id) {
        byte[] pdf = livroService.gerarPDF(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("livro-" + id + ".pdf").build());
        
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
    
    @PostMapping("/publicar/{id}")
    public String publicarLivro(@PathVariable Long id) {
        livroService.publicarLivro(id);
        return "redirect:/dashboard/editor/livros";
    }
    
    @GetMapping("/publicar")
    public String listarLivrosParaPublicar(Model model) {
        List<Livro> livrosNaoPublicados = livroService.buscarLivrosNaoPublicados();
        model.addAttribute("livros", livrosNaoPublicados);
        return "dashboard/editor/livros/publicar";
    }
} 