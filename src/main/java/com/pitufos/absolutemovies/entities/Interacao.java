package com.pitufos.absolutemovies.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "interacao")
public class Interacao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @Column(nullable = false)
    private Integer avaliacao;

    @Column(name = "data_interacao", nullable = false)
    private LocalDateTime data;

    public Interacao() {
        this.data = LocalDateTime.now();
    }

    public Interacao(Usuario usuario, Filme filme, Integer avaliacao) {
        this.usuario = usuario;
        this.filme = filme;
        this.avaliacao = avaliacao;
        this.data = LocalDateTime.now();
    }

    // getters e setters
    public Long getIdInteracao() { return idInteracao; }
    public void setIdInteracao(Long idInteracao) { this.idInteracao = idInteracao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }

    public Integer getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interacao)) return false;
        Interacao that = (Interacao) o;
        return Objects.equals(idInteracao, that.idInteracao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInteracao);
    }

    @Override
    public String toString() {
        return "Interacao{" +
                "idInteracao=" + idInteracao +
                ", usuarioId=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", filmeId=" + (filme != null ? filme.getIdFilme() : null) +
                ", avaliacao=" + avaliacao +
                ", data=" + data +
                '}';
    }
}
