package com.pitufos.absolutemovies.services;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.entities.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario register(Usuario usuario); // cadastra (com hash de senha)
    Usuario updatePreferences(Long usuarioId, List<Genero> novasPreferencias);
    void avaliarFilme(Long usuarioId, Filme filme, int nota);
    Usuario findById(Long id);
    Usuario autenticar(String email, String senha);
}
