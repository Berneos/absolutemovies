package com.pitufos.absolutemovies.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pitufos.absolutemovies.entities.Favorito;
import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.entities.Interacao;
import com.pitufos.absolutemovies.entities.Usuario;
import com.pitufos.absolutemovies.repositories.FavoritoRepository;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.repositories.GeneroRepository;
import com.pitufos.absolutemovies.repositories.InteracaoRepository;
import com.pitufos.absolutemovies.repositories.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            FilmeRepository filmeRepo,
            GeneroRepository generoRepo,
            UsuarioRepository usuarioRepo,
            FavoritoRepository favoritoRepo,
            InteracaoRepository interacaoRepo
    ) {
        return args -> {
            // ==============================
            // GÊNEROS
            // ==============================
            if (generoRepo.count() == 0) {
                Genero acao = new Genero("Ação");
                Genero comedia = new Genero("Comédia");
                Genero drama = new Genero("Drama");
                generoRepo.saveAll(List.of(acao, comedia, drama));
                System.out.println("✅ Gêneros inseridos!");
            }

            // ==============================
            // FILMES
            // ==============================
            if (filmeRepo.count() == 0) {
                List<Genero> generos = generoRepo.findAll();

                Filme f1 = new Filme("Matrix", "Um hacker descobre a verdade sobre a realidade.", 1999);
                f1.setDuracaoMinutos(136);
                f1.setDiretor("Lana e Lilly Wachowski");
                f1.setGeneros(List.of(generos.get(0))); // Ação

                Filme f2 = new Filme("Forrest Gump", "A história de um homem simples com uma vida extraordinária.", 1994);
                f2.setDuracaoMinutos(142);
                f2.setDiretor("Robert Zemeckis");
                f2.setGeneros(List.of(generos.get(2))); // Drama

                Filme f3 = new Filme("Todo Mundo em Pânico", "Paródia de filmes de terror com muito humor.", 2000);
                f3.setDuracaoMinutos(88);
                f3.setDiretor("Keenen Ivory Wayans");
                f3.setGeneros(List.of(generos.get(1))); // Comédia

                filmeRepo.saveAll(List.of(f1, f2, f3));
                System.out.println("🎬 Filmes inseridos!");
            }

            // ==============================
            // USUÁRIO
            // ==============================
            Usuario user;
            if (usuarioRepo.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String senhaCriptografada = encoder.encode("123456");

                user = new Usuario("Breno Martins", "breno@gmail.com", senhaCriptografada);
                List<Genero> generos = generoRepo.findAll();
                user.setPreferencias(List.of(generos.get(0), generos.get(1))); // Ação e Comédia
                user = usuarioRepo.save(user);
                System.out.println("👤 Usuário criado!");
            } else {
                user = usuarioRepo.findAll().get(0);
            }

            // ==============================
            // FAVORITOS
            // ==============================
            if (favoritoRepo.count() == 0) {
                List<Filme> filmes = filmeRepo.findAll();

                Favorito fav1 = new Favorito(user, filmes.get(0)); // Matrix
                Favorito fav2 = new Favorito(user, filmes.get(1)); // Forrest Gump

                user.getFavoritos().addAll(List.of(fav1, fav2));
                favoritoRepo.saveAll(List.of(fav1, fav2));
                usuarioRepo.save(user);

                System.out.println("❤️ Favoritos criados para " + user.getNome());
            }

            // ==============================
            // INTERAÇÕES
            // ==============================
            if (interacaoRepo.count() == 0) {
                List<Filme> filmes = filmeRepo.findAll();

                Interacao inter1 = new Interacao(user, filmes.get(0), 5); // Avaliou Matrix com 5
                Interacao inter2 = new Interacao(user, filmes.get(2), 4); // Avaliou Todo Mundo em Pânico com 4

                interacaoRepo.saveAll(List.of(inter1, inter2));

                System.out.println("💬 Interações criadas para " + user.getNome());
            }
        };
    }
}
