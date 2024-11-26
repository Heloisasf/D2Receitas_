package com.D2Receitas.service;

import com.D2Receitas.model.Livro;
import com.D2Receitas.model.Receita;
import com.D2Receitas.model.Degustacao;
import com.D2Receitas.repository.LivroRepository;
import com.D2Receitas.repository.ReceitaRepository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private ReceitaRepository receitaRepository;
    
    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }
    
    @Transactional
    public Livro criarLivro(String titulo, List<Long> receitasIds) {
        List<Receita> receitas = receitaRepository.findAllById(receitasIds);
        
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setReceitas(receitas);
        livro.setDataCriacao(LocalDateTime.now());
        livro.setPublicado(false);
        
        return livroRepository.save(livro);
    }
    
    public byte[] gerarPDF(Long livroId) {
        Livro livro = livroRepository.findById(livroId)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
            
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        try {
            // Título
            document.add(new Paragraph(livro.getTitulo())
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
            
            // Sumário
            document.add(new Paragraph("Sumário")
                .setFontSize(18)
                .setBold());
                
            for (int i = 0; i < livro.getReceitas().size(); i++) {
                Receita receita = livro.getReceitas().get(i);
                document.add(new Paragraph((i + 1) + ". " + receita.getNome()));
            }
            
            // Conteúdo das Receitas
            for (Receita receita : livro.getReceitas()) {
                document.add(new AreaBreak());
                
                // Título da Receita
                document.add(new Paragraph(receita.getNome())
                    .setFontSize(20)
                    .setBold());
                
                // Descrição
                document.add(new Paragraph("Descrição:")
                    .setBold());
                document.add(new Paragraph(receita.getDescricao()));
                
                // Ingredientes
                document.add(new Paragraph("Ingredientes:")
                    .setBold());
                for (int i = 0; i < receita.getIngredientes().size(); i++) {
                    document.add(new Paragraph("• " + receita.getQuantidades().get(i) + " " +
                        receita.getMedidas().get(i).getNome() + " de " +
                        receita.getIngredientes().get(i).getNome()));
                }
                
                // Modo de Preparo
                document.add(new Paragraph("Modo de Preparo:")
                    .setBold());
                document.add(new Paragraph(receita.getModoPreparo()));
                
                // Degustações
                document.add(new Paragraph("Avaliações:")
                    .setBold());
                for (Degustacao degustacao : receita.getDegustacoes()) {
                    document.add(new Paragraph("Degustador: " + degustacao.getDegustador().getNomeCompleto() +
                        " - Nota: " + degustacao.getNota()));
                }
            }
            
            document.close();
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
    
    @Transactional
    public void publicarLivro(Long id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
            
        // Gerar ISBN (exemplo simplificado)
        String isbn = gerarISBN();
        livro.setIsbn(isbn);
        
        livroRepository.save(livro);
    }
    
    private String gerarISBN() {
        // Implementação simplificada do ISBN
        // Na prática, você precisaria seguir as regras oficiais de geração de ISBN
        return "978-" + new Random().nextInt(10000000) + "-" + 
               new Random().nextInt(100) + "-" + new Random().nextInt(10);
    }
    
    public List<Livro> buscarLivrosNaoPublicados() {
        return livroRepository.findByPublicadoFalse();
    }
}