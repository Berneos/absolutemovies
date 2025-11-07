package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.dto.FilmeDTO;
import com.pitufos.absolutemovies.dto.GeneroDTO;
import com.pitufos.absolutemovies.dto.UsuarioDTO;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ===============================
    // 1️⃣ - Listar todos os usuários
    // ===============================
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    // ===============================
    // 2️⃣ - Registrar novo usuário
    // ===============================
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario novoUsuario = usuarioService.register(usuarioDTO.toEntity());
            return ResponseEntity.ok(new UsuarioDTO(novoUsuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // ===============================
    // 3️⃣ - Login
    // ===============================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario autenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        if (autenticado != null) {
            return ResponseEntity.ok(new UsuarioDTO(autenticado));
        } else {
            return ResponseEntity.status(401).body(Map.of("erro", "Usuário ou senha inválidos"));
        }
    }

    // ===============================
    // 4️⃣ - Buscar usuário por ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // ===============================
    // 5️⃣ - Atualizar preferências
    // ===============================
    @PutMapping("/{id}/preferencias")
    public ResponseEntity<?> atualizarPreferencias(
            @PathVariable Long id,
            @RequestBody List<GeneroDTO> preferenciasDTO) {
        try {
            List<Genero> preferencias = preferenciasDTO.stream()
                    .map(GeneroDTO::toEntity)
                    .collect(Collectors.toList());

            Usuario atualizado = usuarioService.updatePreferences(id, preferencias);
            return ResponseEntity.ok(new UsuarioDTO(atualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // ===============================
    // 6️⃣ - Avaliar filme
    // ===============================
    @PostMapping("/{id}/avaliar")
    public ResponseEntity<?> avaliarFilme(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        try {
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
