package com.pitufos.absolutemovies.controllers;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Recomendacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.services.IAService;
import com.pitufos.absolutemovies.services.RecomendacaoService;
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
    private final RecomendacaoService recomendacaoService;

    public IAController(IAService iaService,
                        UsuarioService usuarioService,
                        RecomendacaoService recomendacaoService) {
        this.iaService = iaService;
        this.usuarioService = usuarioService;
        this.recomendacaoService = recomendacaoService;
    }

    /**
     * Gera recomendação de filme via GPT e já salva no banco.
     * Payload: { "usuarioId": 3, "humor": "animado" }
     */
    @PostMapping("/recomendar")
    public ResponseEntity<?> recomendarFilme(@RequestBody Map<String, Object> payload) {
        try {
            Long usuarioId = ((Number) payload.get("usuarioId")).longValue();
            String humor = (String) payload.get("humor");

            Usuario usuario = usuarioService.findById(usuarioId);

            // Gera recomendação da IA
            Optional<String> respostaIA = iaService.gerarRecomendacaoFilme(usuario, humor);
            if (respostaIA.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensagem", "Nenhum filme recomendado no momento"));
            }

            // Retorna resposta textual simples
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Recomendação da IA gerada com sucesso",
                    "respostaIA", respostaIA.get(),
                    "modelo", iaService.getModelo()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

 }

