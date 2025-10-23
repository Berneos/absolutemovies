package com.pitufos.absolutemovies.repositories;

import com.pitufos.absolutemovies.entities.Interacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InteracaoRepository extends JpaRepository<Interacao, Long> {
    // retorna histórico de um usuário, do mais recente para o mais antigo
    List<Interacao> findByUsuario_IdUsuarioOrderByDataDesc(Long usuarioId);

    // alternativa: buscar por filme, etc.
    List<Interacao> findByFilme_IdFilme(Long filmeId);
}
