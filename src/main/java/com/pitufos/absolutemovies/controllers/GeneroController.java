package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.services.GeneroService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    // ==============================
    // LISTAR TODOS
    // ==============================
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        List<Genero> generos = generoService.listarTodos();
        return ResponseEntity.ok(generos);
    }

    // ==============================
    // BUSCAR POR ID
    // ==============================
    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
        return generoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==============================
    // BUSCAR POR NOME
    // ==============================
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Genero> buscarPorNome(@PathVariable String nome) {
        return generoService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==============================
    // CRIAR NOVO GÊNERO
    // ==============================
    @PostMapping
    public ResponseEntity<?> criarGenero(@RequestBody Genero genero) {
        try {
            Genero novoGenero = generoService.salvar(genero);
            return ResponseEntity.ok(novoGenero);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==============================
    // ATUALIZAR GÊNERO
    // ==============================
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarGenero(@PathVariable Long id, @RequestBody Genero generoAtualizado) {
        return generoService.buscarPorId(id)
                .map(generoExistente -> {
                    generoExistente.setNome(generoAtualizado.getNome());
                    try {
                        Genero generoSalvo = generoService.salvar(generoExistente);
                        return ResponseEntity.ok(generoSalvo);
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ==============================
    // DELETAR GÊNERO
    // ==============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            generoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
