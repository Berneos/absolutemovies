package com.pitufos.absolutemovies.dto;

import com.pitufos.absolutemovies.entities.Recomendacao;

public class RecomendacaoDTO {

   


	private UsuarioDTO usuario;
	private FilmeDTO filme;
    private String origemIA;
    private Float score;

    public RecomendacaoDTO() {}

    public RecomendacaoDTO(Recomendacao recomendacao) {
        this.idRecomendacao = recomendacao.getIdRecomendacao();
        this.filme = new FilmeDTO(recomendacao.getFilme());
        this.origemIA = recomendacao.getOrigemIA();
        this.score = recomendacao.getScore();
    }

    // Getters e Setters
    // ...
    private Long idRecomendacao;
    public Long getIdRecomendacao() {
		return idRecomendacao;
	}

	public void setIdRecomendacao(Long idRecomendacao) {
		this.idRecomendacao = idRecomendacao;
	}

	public FilmeDTO getFilme() {
		return filme;
	}

	public void setFilme(FilmeDTO filme) {
		this.filme = filme;
	}

	public String getOrigemIA() {
		return origemIA;
	}

	public void setOrigemIA(String origemIA) {
		this.origemIA = origemIA;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
}
