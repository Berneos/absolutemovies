package com.pitufos.absolutemovies.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.pitufos.absolutemovies.entities.Filme;

public class FilmeDTO {

    private Long idFilme;
    private String titulo;
    private String descricao;
    private Integer anoLancamento;
    private Integer duracaoMinutos;
    private String diretor;
    private String elenco;
    private String idioma;
    private String urlPoster;
    private String urlTrailer;
    private Double notaMedia;
    private Integer numAvaliacoes;
    private List<GeneroDTO> generos;

    public FilmeDTO() {}

    public FilmeDTO(Filme filme) {
        this.idFilme = filme.getIdFilme();
        this.titulo = filme.getTitulo();
        this.descricao = filme.getDescricao();
        this.anoLancamento = filme.getAnoLancamento();
        this.duracaoMinutos = filme.getDuracaoMinutos();
        this.diretor = filme.getDiretor();
        this.elenco = filme.getElenco();
        this.idioma = filme.getIdioma();
        this.urlPoster = filme.getUrlPoster();
        this.urlTrailer = filme.getUrlTrailer();
        this.notaMedia = filme.getNotaMedia();
        this.numAvaliacoes = filme.getNumAvaliacoes();
        this.generos = filme.getGeneros()
            .stream().map(GeneroDTO::new).collect(Collectors.toList());
    }
    
    public Filme toEntity() {
        Filme filme = new Filme();
        filme.setIdFilme(this.idFilme);
        filme.setTitulo(this.titulo);
        filme.setDescricao(this.descricao);
        filme.setAnoLancamento(this.anoLancamento);
        filme.setDuracaoMinutos(this.duracaoMinutos);
        filme.setDiretor(this.diretor);
        filme.setElenco(this.elenco);
        filme.setIdioma(this.idioma);
        filme.setUrlPoster(this.urlPoster);
        filme.setUrlTrailer(this.urlTrailer);
        filme.setNotaMedia(this.notaMedia);
        filme.setNumAvaliacoes(this.numAvaliacoes);
        return filme;
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(Integer anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public Integer getDuracaoMinutos() {
		return duracaoMinutos;
	}

	public void setDuracaoMinutos(Integer duracaoMinutos) {
		this.duracaoMinutos = duracaoMinutos;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getElenco() {
		return elenco;
	}

	public void setElenco(String elenco) {
		this.elenco = elenco;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUrlPoster() {
		return urlPoster;
	}

	public void setUrlPoster(String urlPoster) {
		this.urlPoster = urlPoster;
	}

	public String getUrlTrailer() {
		return urlTrailer;
	}

	public void setUrlTrailer(String urlTrailer) {
		this.urlTrailer = urlTrailer;
	}

	public Double getNotaMedia() {
		return notaMedia;
	}

	public void setNotaMedia(Double notaMedia) {
		this.notaMedia = notaMedia;
	}

	public Integer getNumAvaliacoes() {
		return numAvaliacoes;
	}

	public void setNumAvaliacoes(Integer numAvaliacoes) {
		this.numAvaliacoes = numAvaliacoes;
	}

	public List<GeneroDTO> getGeneros() {
		return generos;
	}

	public void setGeneros(List<GeneroDTO> generos) {
		this.generos = generos;
	}

	public Long getIdFilme() {
		return idFilme;
	}

    // Getters e Setters
    // ...
}
