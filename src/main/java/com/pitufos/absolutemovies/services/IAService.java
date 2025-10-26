package com.pitufos.absolutemovies.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitufos.absolutemovies.components.GPTClient;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.FilmeRepository;

@Service
public class IAService {

    private static final String MODELO = "gpt-4o";
    private static final String PROMPT_BASE = """
        Recomende um filme para o usuário com base nos seguintes filmes favoritos: {{favoritos}}. 
        O usuário está se sentindo: {{humor}}. Liste apenas um filme que ele provavelmente irá gostar.
    """;

    @Autowired
    private FilmeRepository filmeRepository;
    @Autowired
    private GPTClient gptClient;
    
    public String getModelo() {
        return MODELO;
    }

    public String gerarPromptPersonalizado(Usuario usuario, String humor) {
        // 1. Obter filmes favoritos do usuário
        List<String> filmesFavoritos = usuario.getHistorico().stream()
            .filter(interacao -> interacao.getAvaliacao() != null && interacao.getAvaliacao() >= 4) // opcional: só filmes bem avaliados
            .map(interacao -> interacao.getFilme().getTitulo())
            .toList();

        String favoritos = String.join(", ", filmesFavoritos);

        

     

        // 3. Substituir placeholders
        String promptPersonalizado = PROMPT_BASE
            .replace("{{favoritos}}", favoritos.isEmpty() ? "nenhum filme" : favoritos)
            .replace("{{humor}}", humor);

        return promptPersonalizado;
    }
    
    public Optional<String> gerarRecomendacaoFilme(Usuario usuario, String humor) {
        String prompt = gerarPromptPersonalizado(usuario, humor);

        // Chamada para GPT
        Optional<String> filmeRecomendado = gptClient.chamarIA(prompt); // retorna apenas o nome do filme

        return filmeRecomendado;
    }



    public Filme buscarFilmeRecomendado(String titulo) {
        List<Filme> filmes = filmeRepository.findByTituloContainingIgnoreCase(titulo);
        if (filmes.isEmpty()) return null; // ou lançar exceção
        return filmes.get(0); // pega o primeiro
    }


    
}

