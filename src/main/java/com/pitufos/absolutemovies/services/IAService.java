package com.pitufos.absolutemovies.services;

import org.springframework.stereotype.Service;

@Service
public class IAService {

    private static final String MODELO = "gpt-4o";
    private static final String PROMPT_BASE = """
        Você é uma IA que recomenda filmes com base nos gostos e favoritos do usuário.
        Perfil: {{perfil_usuario}}
        Liste recomendações de forma amigável e curta.
    """;

    public String getModelo() {
        return MODELO;
    }

    public String gerarPromptComPerfil(String perfilUsuario) {
        return PROMPT_BASE.replace("{{perfil_usuario}}", perfilUsuario);
    }
}
