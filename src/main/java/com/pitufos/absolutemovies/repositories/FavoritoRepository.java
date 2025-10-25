package com.pitufos.absolutemovies.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pitufos.absolutemovies.entities.Favorito;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Usuario;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Retorna todos os favoritos de um usuário
    List<Favorito> findByUsuario(Usuario usuario);

    // Verifica se um filme já é favorito de um usuário
    Optional<Favorito> findByUsuarioAndFilme(Usuario usuario, Filme filme);

    // Remove um favorito específico
    void deleteByUsuarioAndFilme(Usuario usuario, Filme filme);

    // Retorna todos os filmes favoritos de um usuário
    @Query("SELECT f.filme FROM Favorito f WHERE f.usuario = :usuario")
    List<Filme> listarFilmesFavoritos(Usuario usuario);
}
