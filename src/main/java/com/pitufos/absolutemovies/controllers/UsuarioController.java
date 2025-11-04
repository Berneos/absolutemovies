package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Registrar novo usuário
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novo = usuarioService.register(usuario);
            return ResponseEntity.ok(novo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario autenticado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
        if (autenticado != null) {
            return ResponseEntity.ok(autenticado);
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
    }

    /**
     * Buscar usuário por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * Atualizar preferências de gêneros do usuário
     */
    @PutMapping("/{id}/preferencias")
    public ResponseEntity<?> atualizarPreferencias(
            @PathVariable Long id,
            @RequestBody List<Genero> preferencias) {
        try {
            Usuario atualizado = usuarioService.updatePreferences(id, preferencias);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * Registrar uma avaliação de filme pelo usuário
     */
    @PostMapping("/{id}/avaliar")
    public ResponseEntity<?> avaliarFilme(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            // Espera-se algo como { "filmeId": 3, "nota": 4 }
            Long filmeId = ((Number) payload.get("filmeId")).longValue();
            int nota = (int) payload.get("nota");

            Filme filme = new Filme();
            filme.setIdFilme(filmeId);

            usuarioService.avaliarFilme(id, filme, nota);
            return ResponseEntity.ok(Map.of("mensagem", "Avaliação registrada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
