package com.pitufos.absolutemovies.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recomendacoes")
public class Recomendacao implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecomendacao;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idFilme", nullable = false)
    private Filme filme;

    private String origemIA;
    private Float score;

    public Recomendacao() {
    }

    public Recomendacao(Usuario usuario, Filme filme, String origemIA, Float score) {
        this.usuario = usuario;
        this.filme = filme;
        this.origemIA = origemIA;
        this.score = score;
    }

    // Getters e Setters
    public Long getIdRecomendacao() {
        return idRecomendacao;
    }

    public void setIdRecomendacao(Long idRecomendacao) {
        this.idRecomendacao = idRecomendacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
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
}
