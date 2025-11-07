package com.pitufos.absolutemovies.dto;

import java.time.LocalDateTime;
import com.pitufos.absolutemovies.entities.Interacao;

public class InteracaoDTO {

    private Long idInteracao;
    private FilmeDTO filme;
    private Integer avaliacao;
    private LocalDateTime data;

    public InteracaoDTO() {}

    public InteracaoDTO(Interacao interacao) {
        this.idInteracao = interacao.getIdInteracao();
        this.filme = new FilmeDTO(interacao.getFilme());
        this.avaliacao = interacao.getAvaliacao();
        this.data = interacao.getData();
    }

    // Getters e Setters
    // ...
}
