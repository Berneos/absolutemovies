package com.pitufos.absolutemovies.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.services.FilmeService;

@Service
public class FilmeServiceImpl implements FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeServiceImpl(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Override
    @Transactional
    public Filme salvar(Filme filme) {
        return filmeRepository.save(filme);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Filme> buscarPorId(Long id) {
        return filmeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filme> listarTodos() {
        return filmeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filme> buscarPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filme> buscarPorGenero(String genero) {
        return filmeRepository.buscarPorGenero(genero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filme> buscarPorPalavraChave(String palavra) {
        return filmeRepository.buscarPorPalavraChave(palavra);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        filmeRepository.deleteById(id);
    }
}
