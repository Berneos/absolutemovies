package com.pitufos.absolutemovies.dto;

import com.pitufos.absolutemovies.entities.Genero;

public class GeneroDTO {

    private Long idGenero;
    private String nome;

    public GeneroDTO() {}

    public GeneroDTO(Genero genero) {
        this.idGenero = genero.getIdGenero();
        this.nome = genero.getNome();
    }
    
    public Genero toEntity() {
        Genero genero = new Genero();
        genero.setIdGenero(this.idGenero);
        genero.setNome(this.nome);
        return genero;
    }

    // Getters e Setters
    // ...
    
    public Long getId() { return idGenero;}
    public void setNome(String nome) {this.nome = nome;}
    public String getNome() {return nome;}
}
