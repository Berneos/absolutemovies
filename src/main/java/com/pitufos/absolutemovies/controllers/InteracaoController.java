package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Interacao;
import com.pitufos.absolutemovies.services.InteracaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes")
public class InteracaoController {

    private final InteracaoService interacaoService;

    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    // ==============================
    // CRIAR UMA NOVA INTERAÇÃO (avaliação)
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
}
