package com.pitufos.absolutemovies.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "favorito",
    uniqueConstraints = @UniqueConstraint(name = "uk_usuario_filme", columnNames = {"usuario_id", "filme_id"})
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Favorito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavorito;

    @JsonBackReference // Evita loop com Usuario
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonManagedReference // Permite exibir o Filme normalmente
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @Column(name = "data_favorito", nullable = false)
    private LocalDateTime dataFavorito;

    public Favorito() {
        this.dataFavorito = LocalDateTime.now();
    }

    public Favorito(Usuario usuario, Filme filme) {
        this.usuario = usuario;
        this.filme = filme;
        this.dataFavorito = LocalDateTime.now();
    }

    // ===== Getters / Setters =====
    public Long getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Long idFavorito) {
        this.idFavorito = idFavorito;
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

    public LocalDateTime getDataFavorito() {
        return dataFavorito;
    }

    public void setDataFavorito(LocalDateTime dataFavorito) {
        this.dataFavorito = dataFavorito;
    }

    // ===== equals / hashCode / toString =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorito)) return false;
        Favorito favorito = (Favorito) o;
        return Objects.equals(idFavorito, favorito.idFavorito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFavorito);
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "idFavorito=" + idFavorito +
                ", usuarioId=" + (usuario != null ? usuario.getIdUsuario() : null) +
                ", filmeId=" + (filme != null ? filme.getIdFilme() : null) +
                ", dataFavorito=" + dataFavorito +
                '}';
    }
}
