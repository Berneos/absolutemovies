package com.pitufos.absolutemovies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitufos.absolutemovies.entities.Interacao;
import com.pitufos.absolutemovies.services.InteracaoService;

@RestController
@RequestMapping("/interacoes")
@CrossOrigin(origins = "*") // ✅ permite chamadas externas (ex: React)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // ✅ evita erro com proxies Hibernate
public class InteracaoController {

    private final InteracaoService interacaoService;

    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    // ==============================
    // CRIAR UMA NOVA INTERAÇÃO (avaliação, like, etc.)
    // ==============================
    @PostMapping
    public ResponseEntity<?> criarInteracao(
            @RequestParam Long usuarioId,
            @RequestParam Long filmeId,
            @RequestParam int avaliacao) {

        try {
            Interacao nova = interacaoService.salvarInteracao(usuarioId, filmeId, avaliacao);
            return ResponseEntity.ok(nova);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==============================
    // OBTER HISTÓRICO DE INTERAÇÕES DE UM USUÁRIO
    // ==============================
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Interacao>> obterHistorico(@PathVariable Long usuarioId) {
        List<Interacao> historico = interacaoService.obterHistorico(usuarioId);
        return ResponseEntity.ok(historico);
    }



    // ==============================
    // CALCULAR MÉDIA DE AVALIAÇÕES DE UM FILME
    // ==============================
    @GetMapping("/filme/{filmeId}/media")
    public ResponseEntity<Double> calcularMediaFilme(@PathVariable Long filmeId) {
        Double media = interacaoService.calcularMediaAvaliacao(filmeId);
        return ResponseEntity.ok(media);
    }
}
