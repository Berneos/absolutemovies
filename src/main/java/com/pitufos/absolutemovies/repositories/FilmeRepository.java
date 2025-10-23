package com.pitufos.absolutemovies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pitufos.absolutemovies.entities.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // Busca filmes por parte do título (ignorando maiúsculas/minúsculas)
    List<Filme> findByTituloContainingIgnoreCase(String titulo);

    // Busca por gênero (usando relacionamento ManyToMany)
    @Query("SELECT f FROM Filme f JOIN f.generos g WHERE LOWER(g.nome) = LOWER(:nomeGenero)")
    List<Filme> buscarPorGenero(String nomeGenero);

    // Busca por palavras-chave na descrição
    @Query("SELECT f FROM Filme f WHERE LOWER(f.descricao) LIKE LOWER(CONCAT('%', :palavra, '%'))")
    List<Filme> buscarPorPalavraChave(String palavra);

}
