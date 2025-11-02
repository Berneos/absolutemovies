package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.services.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // ✅ Criar um novo filme
    @PostMapping
    public ResponseEntity<Filme> salvar(@RequestBody Filme filme) {
        Filme salvo = filmeService.salvar(filme);
        return ResponseEntity.ok(salvo);
    }

    // ✅ Buscar filme por ID
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Listar todos os filmes
    @GetMapping
    public ResponseEntity<List<Filme>> listarTodos() {
        List<Filme> filmes = filmeService.listarTodos();
        return ResponseEntity.ok(filmes);
    }

    // ✅ Buscar por título (ex: /filmes/titulo?valor=Matrix)
    @GetMapping("/titulo")
    public ResponseEntity<List<Filme>> buscarPorTitulo(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorTitulo(valor);
        return ResponseEntity.ok(filmes);
    }

    // ✅ Buscar por gênero (ex: /filmes/genero?valor=Ação)
    @GetMapping("/genero")
    public ResponseEntity<List<Filme>> buscarPorGenero(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorGenero(valor);
        return ResponseEntity.ok(filmes);
    }

    // ✅ Buscar por palavra-chave (ex: /filmes/palavra?valor=futuro)
    @GetMapping("/palavra")
    public ResponseEntity<List<Filme>> buscarPorPalavra(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorPalavraChave(valor);
        return ResponseEntity.ok(filmes);
    }

    // ✅ Deletar filme por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (filmeService.buscarPorId(id).isPresent()) {
            filmeService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
