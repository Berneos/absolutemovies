package com.pitufos.absolutemovies.services;

import com.pitufos.absolutemovies.entities.Interacao;
import java.util.List;

public interface InteracaoService {
    Interacao salvarInteracao(Long usuarioId, Long filmeId, int avaliacao);
    List<Interacao> obterHistorico(Long usuarioId);
}
