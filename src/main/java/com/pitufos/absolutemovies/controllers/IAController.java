package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.services.IAService;
import com.pitufos.absolutemovies.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ia")
@CrossOrigin(origins = "*")
public class IAController {

    private final IAService iaService;
    private final UsuarioService usuarioService;

    public IAController(IAService iaService, UsuarioService usuarioService) {
        this.iaService = iaService;
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para gerar recomendação de filme via GPT
     * Exemplo de payload: { "usuarioId": 3, "humor": "animado" }
     */
    @PostMapping("/recomendar")
    public ResponseEntity<?> recomendarFilme(@RequestBody Map<String, Object> payload) {
        try {
            Long usuarioId = ((Number) payload.get("usuarioId")).longValue();
            String humor = (String) payload.get("humor");

            Usuario usuario = usuarioService.findById(usuarioId);
            Optional<String> tituloFilme = iaService.gerarRecomendacaoFilme(usuario, humor);

            if (tituloFilme.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensagem", "Nenhum filme recomendado no momento"));
            }

            Filme filme = iaService.buscarFilmeRecomendado(tituloFilme.get());
            if (filme == null) {
                return ResponseEntity.ok(Map.of("mensagem", "Filme recomendado não encontrado no banco de dados"));
            }

            return ResponseEntity.ok(filme);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
