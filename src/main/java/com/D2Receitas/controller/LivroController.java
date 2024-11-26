package com.D2Receitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.D2Receitas.model.Livro;
import com.D2Receitas.model.Receita;
import com.D2Receitas.model.Funcionario;
import com.D2Receitas.repository.LivroRepository;
import com.D2Receitas.repository.ReceitaRepository;
import com.D2Receitas.repository.FuncionarioRepository;
import com.D2Receitas.service.PDFService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/editor")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private ReceitaRepository receitaRepository;
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private PDFService pdfService;

    @GetMapping("/criarLivro")
    public String mostrarFormularioCriarLivro(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("receitas", receitaRepository.findAll());
        return "dashboard/editor/criarLivro";
    }

    @PostMapping("/criarLivro")
    public ResponseEntity<byte[]> criarLivro(@ModelAttribute Livro livro,
                            @RequestParam("receitasIds") List<Long> receitasIds,
                            Authentication authentication) {
        try {
            String username = authentication.getName();
            Funcionario editor = funcionarioRepository.findByUsername(username);
            
            List<Receita> receitas = receitasIds.stream()
                .map(id -> receitaRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
                
            livro.setReceitas(receitas);
            livro.setEditor(editor);
            livro.setDataCriacao(LocalDateTime.now());
            livro.setPublicado(false);
            
            Livro livroSalvo = livroRepository.save(livro);
            
            // Gerar PDF
            byte[] pdfBytes = pdfService.gerarLivroPDF(livroSalvo);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", livro.getNome() + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/livrosCriados")
    public String listarLivrosCriados(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario editor = funcionarioRepository.findByUsername(username);
        
        List<Livro> livros = livroRepository.findByEditorAndPublicado(editor, false);
        model.addAttribute("livros", livros);
        
        return "dashboard/editor/livrosCriados";
    }

    @GetMapping("/publicarLivro/{id}")
    public String publicarLivro(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Livro inválido: " + id));
            
        // Gerar ISBN (formato simplificado para exemplo)
        String isbn = "978-" + String.format("%010d", System.currentTimeMillis() % 10000000000L);
        livro.setIsbn(isbn);
        livro.setPublicado(true);
        livroRepository.save(livro);
        
        return "redirect:/dashboard/editor/livrosPublicados";
    }

    @GetMapping("/livrosPublicados")
    public String listarLivrosPublicados(Model model, Authentication authentication) {
        String username = authentication.getName();
        Funcionario editor = funcionarioRepository.findByUsername(username);
        
        List<Livro> livros = livroRepository.findByEditorAndPublicado(editor, true);
        model.addAttribute("livros", livros);
        
        return "dashboard/editor/livrosPublicados";
    }

    @GetMapping("/baixarLivro/{id}")
    public ResponseEntity<byte[]> baixarLivro(@PathVariable Long id) {
        try {
            Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro inválido: " + id));
            
            byte[] pdfBytes = pdfService.gerarLivroPDF(livro);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", livro.getNome() + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 