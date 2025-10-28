package com.pitufos.absolutemovies.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "filme")
public class Filme implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFilme;

    @Column(nullable = false)
    private String titulo;

	 // antes
	 // @Lob
	 // @Column(name = "descricao", columnDefinition = "TEXT")
	 // private String descricao;

	 // depois (remover @Lob)
	 @Column(name = "descricao", columnDefinition = "TEXT")
	 private String descricao;


    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column
    private String diretor;

    @Column(length = 1000)
    private String elenco; // pode armazenar como CSV ou usar entidade separada se preferir

    @Column
    private String idioma;

    @Column(name = "url_poster")
    private String urlPoster;

    @Column(name = "url_trailer")
    private String urlTrailer;

    // Campos para armazenar estatísticas (opcional — atualize via service)
    @Column(name = "nota_media")
    private Double notaMedia;

    @Column(name = "num_avaliacoes")
    private Integer numAvaliacoes;

    // Relacionamento com Genero
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "filme_genero",
        joinColumns = @JoinColumn(name = "filme_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();

    // Relacionamento com Interacao (historico de avaliações)
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interacao> interacoes = new ArrayList<>();

    // ===========================
    // CONSTRUTORES
    // ===========================
    public Filme() {
    }

    public Filme(String titulo, String descricao, Integer anoLancamento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.anoLancamento = anoLancamento;
    }

    // ===========================
    // GETTERS / SETTERS
    // ===========================
    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
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

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Interacao> getInteracoes() {
        return interacoes;
    }

    public void setInteracoes(List<Interacao> interacoes) {
        this.interacoes = interacoes;
    }

    // ===========================
    // EQUALS / HASHCODE / TOSTRING
    // ===========================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filme)) return false;
        Filme filme = (Filme) o;
        return Objects.equals(idFilme, filme.idFilme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFilme);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "idFilme=" + idFilme +
                ", titulo='" + titulo + '\'' +
                ", anoLancamento=" + anoLancamento +
                '}';
    }
}
