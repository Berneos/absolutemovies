package com.pitufos.absolutemovies.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.repositories.GeneroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    /**
     * Retorna todos os gêneros cadastrados.
     */
    @Transactional(readOnly = true)
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    /**
     * Busca um gênero pelo ID.
     */
    @Transactional(readOnly = true)
    public Optional<Genero> buscarPorId(Long id) {
        return generoRepository.findById(id);
    }

    /**
     * Busca um gênero pelo nome (case-insensitive).
     */
    @Transactional(readOnly = true)
    public Optional<Genero> buscarPorNome(String nome) {
        return generoRepository.findByNomeIgnoreCase(nome);
    }

    /**
     * Cria ou atualiza um gênero.
     * Caso o nome já exista, lança exceção.
     */
    @Transactional
    public Genero salvar(Genero genero) {
        if (generoRepository.existsByNomeIgnoreCase(genero.getNome())) {
            throw new IllegalArgumentException("Gênero já cadastrado: " + genero.getNome());
        }
        return generoRepository.save(genero);
    }

    /**
     * Remove um gênero pelo ID.
     */
    @Transactional
    public void deletar(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new IllegalArgumentException("Gênero não encontrado para exclusão: " + id);
        }
        generoRepository.deleteById(id);
    }
}
