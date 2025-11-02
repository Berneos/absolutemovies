package com.pitufos.absolutemovies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pitufos.absolutemovies.entities.Favorito;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.repositories.UsuarioRepository;
import com.pitufos.absolutemovies.services.FavoritoService;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    public FavoritoController(FavoritoService favoritoService,
                              UsuarioRepository usuarioRepository,
                              FilmeRepository filmeRepository) {
        this.favoritoService = favoritoService;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
    }

    // Adicionar favorito
    @PostMapping("/{idUsuario}/{idFilme}")
    public ResponseEntity<Favorito> adicionarFavorito(@PathVariable Long idUsuario, @PathVariable Long idFilme) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Filme filme = filmeRepository.findById(idFilme)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        Favorito favorito = favoritoService.adicionarFavorito(usuario, filme);
        return ResponseEntity.ok(favorito);
    }

    // Remover favorito
    @DeleteMapping("/{idUsuario}/{idFilme}")
    public ResponseEntity<Void> removerFavorito(@PathVariable Long idUsuario, @PathVariable Long idFilme) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Filme filme = filmeRepository.findById(idFilme)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        favoritoService.removerFavorito(usuario, filme);
        return ResponseEntity.noContent().build();
    }

    // Listar favoritos de um usuário
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Favorito>> listarFavoritos(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Favorito> favoritos = favoritoService.listarFavoritos(usuario);
        return ResponseEntity.ok(favoritos);
    }
}
