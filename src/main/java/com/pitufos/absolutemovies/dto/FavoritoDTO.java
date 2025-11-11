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

	public Long getIdFavorito() {
		return idFavorito;
	}

	public void setIdFavorito(Long idFavorito) {
		this.idFavorito = idFavorito;
	}

	public FilmeDTO getFilme() {
		return filme;
	}

	public void setFilme(FilmeDTO filme) {
		this.filme = filme;
	}

	public LocalDateTime getDataFavorito() {
		return dataFavorito;
	}

	public void setDataFavorito(LocalDateTime dataFavorito) {
		this.dataFavorito = dataFavorito;
	}

    // Getters e Setters
    // ...
}

