package com.pitufos.absolutemovies.services;

import java.util.List;
import java.util.Optional;

import com.pitufos.absolutemovies.entities.Filme;

public interface FilmeService {

    Filme salvar(Filme filme);

    Optional<Filme> buscarPorId(Long id);

    List<Filme> listarTodos();

    List<Filme> buscarPorTitulo(String titulo);

    List<Filme> buscarPorGenero(String genero);

    List<Filme> buscarPorPalavraChave(String palavra);

    void deletar(Long id);
}
