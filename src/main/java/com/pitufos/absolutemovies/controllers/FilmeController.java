package com.pitufos.absolutemovies.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pitufos.absolutemovies.dto.FilmeDTO;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.services.FilmeService;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // ✅ Criar um novo filme
    @PostMapping
    public ResponseEntity<FilmeDTO> salvar(@RequestBody FilmeDTO filmeDTO) {
        Filme filme = filmeDTO.toEntity();
        Filme salvo = filmeService.salvar(filme);
        return ResponseEntity.ok(new FilmeDTO(salvo));
    }

    // ✅ Buscar filme por ID
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> buscarPorId(@PathVariable Long id) {
        Optional<Filme> filmeOpt = filmeService.buscarPorId(id);
        return filmeOpt
                .map(filme -> ResponseEntity.ok(new FilmeDTO(filme)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Listar todos os filmes
    @GetMapping
    public ResponseEntity<List<FilmeDTO>> listarTodos() {
        List<Filme> filmes = filmeService.listarTodos();
        List<FilmeDTO> filmesDTO = filmes.stream()
                .map(FilmeDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filmesDTO);
    }

    // ✅ Buscar por título (ex: /filmes/titulo?valor=Matrix)
    @GetMapping("/titulo")
    public ResponseEntity<List<FilmeDTO>> buscarPorTitulo(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorTitulo(valor);
        List<FilmeDTO> filmesDTO = filmes.stream()
                .map(FilmeDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filmesDTO);
    }

    // ✅ Buscar por gênero (ex: /filmes/genero?valor=Ação)
    @GetMapping("/genero")
    public ResponseEntity<List<FilmeDTO>> buscarPorGenero(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorGenero(valor);
        List<FilmeDTO> filmesDTO = filmes.stream()
                .map(FilmeDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filmesDTO);
    }

    // ✅ Buscar por palavra-chave (ex: /filmes/palavra?valor=futuro)
    @GetMapping("/palavra")
    public ResponseEntity<List<FilmeDTO>> buscarPorPalavra(@RequestParam String valor) {
        List<Filme> filmes = filmeService.buscarPorPalavraChave(valor);
        List<FilmeDTO> filmesDTO = filmes.stream()
                .map(FilmeDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filmesDTO);
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
