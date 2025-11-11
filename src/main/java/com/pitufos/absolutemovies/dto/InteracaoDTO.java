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

	public Long getIdInteracao() {
		return idInteracao;
	}

	public void setIdInteracao(Long idInteracao) {
		this.idInteracao = idInteracao;
	}

	public FilmeDTO getFilme() {
		return filme;
	}

	public void setFilme(FilmeDTO filme) {
		this.filme = filme;
	}

	public Integer getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

    // Getters e Setters
    // ...
}
