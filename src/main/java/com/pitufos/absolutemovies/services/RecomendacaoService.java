package com.pitufos.absolutemovies.services;

import com.pitufos.absolutemovies.entities.Recomendacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.RecomendacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository repository;

    // Salvar ou atualizar recomendação
    public Recomendacao salvar(Recomendacao recomendacao) {
        return repository.save(recomendacao);
    }

    // Buscar todas recomendações
    public List<Recomendacao> buscarTodas() {
        return repository.findAll();
    }

    // Buscar recomendação por ID
    public Optional<Recomendacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Buscar recomendações de um usuário específico
    public List<Recomendacao> buscarPorUsuario(Usuario usuario) {
        return repository.findByUsuarioOrderByScoreDesc(usuario);
    }

    // Deletar recomendação
    public void deletar(Recomendacao recomendacao) {
        repository.delete(recomendacao);
    }
}
