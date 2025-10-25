package com.pitufos.absolutemovies.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitufos.absolutemovies.entities.Favorito;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.FavoritoRepository;
import com.pitufos.absolutemovies.services.FavoritoService;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoServiceImpl(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    @Override
    @Transactional
    public Favorito adicionarFavorito(Usuario usuario, Filme filme) {
        return favoritoRepository.findByUsuarioAndFilme(usuario, filme)
                .orElseGet(() -> favoritoRepository.save(new Favorito(usuario, filme)));
    }

    @Override
    @Transactional
    public void removerFavorito(Usuario usuario, Filme filme) {
        favoritoRepository.deleteByUsuarioAndFilme(usuario, filme);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Favorito> listarFavoritos(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorito(Usuario usuario, Filme filme) {
        return favoritoRepository.findByUsuarioAndFilme(usuario, filme).isPresent();
    }
}
