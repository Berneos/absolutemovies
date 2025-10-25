package com.pitufos.absolutemovies.services;

import java.util.List;

import com.pitufos.absolutemovies.entities.Favorito;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;

public interface FavoritoService {

    Favorito adicionarFavorito(Usuario usuario, Filme filme);

    void removerFavorito(Usuario usuario, Filme filme);

    List<Favorito> listarFavoritos(Usuario usuario);

    boolean isFavorito(Usuario usuario, Filme filme);
}
