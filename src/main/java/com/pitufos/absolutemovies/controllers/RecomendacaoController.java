package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Recomendacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.UsuarioRepository;
import com.pitufos.absolutemovies.services.RecomendacaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;
    private final UsuarioRepository usuarioRepository;

    public RecomendacaoController(RecomendacaoService recomendacaoService,
                                  UsuarioRepository usuarioRepository) {
        this.recomendacaoService = recomendacaoService;
        this.usuarioRepository = usuarioRepository;
    }

    // ==============================
    // 1️⃣ - Criar nova recomendação
    // ==============================
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Recomendacao recomendacao) {
        try {
            Recomendacao salva = recomendacaoService.salvar(recomendacao);
            return ResponseEntity.ok(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar recomendação: " + e.getMessage());
        }
    }

    // ==============================
    // 2️⃣ - Listar todas as recomendações
    // ==============================
    @GetMapping
    public ResponseEntity<List<Recomendacao>> listarTodas() {
        return ResponseEntity.ok(recomendacaoService.buscarTodas());
    }

    // ==============================
    // 3️⃣ - Buscar recomendação por ID
    // ==============================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return recomendacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==============================
    // 4️⃣ - Buscar recomendações por usuário (ordenadas por score)
    // ==============================
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

        List<Recomendacao> recomendacoes = recomendacaoService.buscarPorUsuario(usuario);
        return ResponseEntity.ok(recomendacoes);
    }

    // ==============================
    // 5️⃣ - Deletar recomendação
    // ==============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return recomendacaoService.buscarPorId(id)
                .map(rec -> {
                    recomendacaoService.deletar(rec);
                    return ResponseEntity.ok("Recomendação removida com sucesso!");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
