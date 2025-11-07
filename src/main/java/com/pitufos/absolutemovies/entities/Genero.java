package com.pitufos.absolutemovies.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "genero")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genero implements Serializable{

  
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenero;

    @Column(nullable = false, unique = true)
    private String nome;

    /**
     * Lado inverso do relacionamento ManyToMany com Usuario.
     * A propriedade "preferencias" é a que declaramos como owning side em Usuario.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "preferencias", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    public Genero() {
    }

    public Genero(String nome) {
        this.nome = nome;
    }

    // ===========================
    // GETTERS / SETTERS
    // ===========================

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // ===========================
    // equals / hashCode / toString
    // ===========================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genero)) return false;
        Genero genero = (Genero) o;
        return Objects.equals(idGenero, genero.idGenero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGenero);
    }

    @Override
    public String toString() {
        return "Genero{" +
                "idGenero=" + idGenero +
                ", nome='" + nome + '\'' +
                '}';
    }
}
