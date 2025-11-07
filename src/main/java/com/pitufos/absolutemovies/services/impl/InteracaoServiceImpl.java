package com.pitufos.absolutemovies.services.impl;

import java.util.List;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Interacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.repositories.InteracaoRepository;
import com.pitufos.absolutemovies.repositories.UsuarioRepository;
import com.pitufos.absolutemovies.services.InteracaoService;

@Service
public class InteracaoServiceImpl implements InteracaoService {

    private final InteracaoRepository interacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    public InteracaoServiceImpl(InteracaoRepository interacaoRepository,
                                UsuarioRepository usuarioRepository,
                                FilmeRepository filmeRepository) {
        this.interacaoRepository = interacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
    }

    @Override
    @Transactional
    public Interacao salvarInteracao(Long usuarioId, Long filmeId, int avaliacao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado"));

        Interacao inter = new Interacao(usuario, filme, avaliacao);
        interacaoRepository.save(inter);

        // opcional: manter consistência bidirecional
        usuario.getHistorico().add(inter);
        usuarioRepository.save(usuario);

        return inter;
    }

    @Override
    public List<Interacao> obterHistorico(Long usuarioId) {
        return interacaoRepository.findByUsuario_IdUsuarioOrderByDataDesc(usuarioId);
    }

    // ✅ Novo método para calcular média das avaliações de um filme
    @Override
    public Double calcularMediaAvaliacao(Long filmeId) {
        List<Interacao> interacoes = interacaoRepository.findByFilme_IdFilme(filmeId);

        if (interacoes.isEmpty()) {
            return 0.0; // ou null, se quiser indicar ausência de avaliações
        }

        OptionalDouble media = interacoes.stream()
                .mapToInt(Interacao::getAvaliacao)
                .average();

        return media.orElse(0.0);
    }
}
