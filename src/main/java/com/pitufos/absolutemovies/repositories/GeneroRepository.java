package com.pitufos.absolutemovies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pitufos.absolutemovies.entities.Genero;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    // Para buscar um gênero pelo nome (útil em cadastros e validações)
    Optional<Genero> findByNomeIgnoreCase(String nome);

    // Verifica se um gênero com o nome já existe
    boolean existsByNomeIgnoreCase(String nome);
}
