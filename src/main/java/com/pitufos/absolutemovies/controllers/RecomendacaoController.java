package com.pitufos.absolutemovies.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pitufos.absolutemovies.dto.RecomendacaoDTO;
import com.pitufos.absolutemovies.dto.UsuarioDTO;
import com.pitufos.absolutemovies.dto.FilmeDTO;
import com.pitufos.absolutemovies.entities.Recomendacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.repositories.UsuarioRepository;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.services.RecomendacaoService;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    public RecomendacaoController(RecomendacaoService recomendacaoService,
                                  UsuarioRepository usuarioRepository,
                                  FilmeRepository filmeRepository) {
        this.recomendacaoService = recomendacaoService;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
    }

    // ==============================
    // 1️⃣ - Criar nova recomendação
    // ==============================
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody RecomendacaoDTO dto) {
        try {
            Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            Filme filme = filmeRepository.findById(dto.getFilme().getIdFilme())
                    .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado"));

            Recomendacao recomendacao = new Recomendacao();
            recomendacao.setUsuario(usuario);
            recomendacao.setFilme(filme);
            recomendacao.setOrigemIA(dto.getOrigemIA());
            recomendacao.setScore(dto.getScore());

            Recomendacao salva = recomendacaoService.salvar(recomendacao);
            return ResponseEntity.ok(new RecomendacaoDTO(salva));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar recomendação: " + e.getMessage());
        }
    }

    // ==============================
    // 2️⃣ - Listar todas as recomendações
    // ==============================
    @GetMapping
    public ResponseEntity<List<RecomendacaoDTO>> listarTodas() {
        List<RecomendacaoDTO> lista = recomendacaoService.buscarTodas()
                .stream()
                .map(RecomendacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // ==============================
    // 3️⃣ - Buscar recomendação por ID
    // ==============================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return recomendacaoService.buscarPorId(id)
                .map(rec -> ResponseEntity.ok(new RecomendacaoDTO(rec)))
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

        List<RecomendacaoDTO> recomendacoes = recomendacaoService.buscarPorUsuario(usuario)
                .stream()
                .map(RecomendacaoDTO::new)
                .collect(Collectors.toList());

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
