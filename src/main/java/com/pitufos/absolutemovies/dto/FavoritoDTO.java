package com.pitufos.absolutemovies.dto;

import java.time.LocalDateTime;
import com.pitufos.absolutemovies.entities.Favorito;

public class FavoritoDTO {

    private Long idFavorito;
    private FilmeDTO filme;
    private LocalDateTime dataFavorito;

    public FavoritoDTO() {}

    public FavoritoDTO(Favorito favorito) {
        this.idFavorito = favorito.getIdFavorito();
        this.filme = new FilmeDTO(favorito.getFilme());
        this.dataFavorito = favorito.getDataFavorito();
    }

    // Getters e Setters
    // ...
}

