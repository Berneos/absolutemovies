package com.pitufos.absolutemovies.repositories;

import com.pitufos.absolutemovies.entities.Recomendacao;
import com.pitufos.absolutemovies.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {

    // Busca todas as recomendações de um usuário específico
    List<Recomendacao> findByUsuario(Usuario usuario);

    // Opcional: buscar recomendações por usuário ordenadas pelo score
    List<Recomendacao> findByUsuarioOrderByScoreDesc(Usuario usuario);
}
