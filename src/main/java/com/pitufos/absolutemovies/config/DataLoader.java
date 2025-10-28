package com.pitufos.absolutemovies.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pitufos.absolutemovies.entities.Filme;
import com.pitufos.absolutemovies.entities.Genero;
import com.pitufos.absolutemovies.repositories.FilmeRepository;
import com.pitufos.absolutemovies.repositories.GeneroRepository;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(FilmeRepository filmeRepo, GeneroRepository generoRepo) {
        return args -> {
            if (generoRepo.count() == 0) {
                Genero acao = new Genero("Ação");
                Genero comedia = new Genero("Comédia");
                Genero drama = new Genero("Drama");
                generoRepo.saveAll(List.of(acao, comedia, drama));
                System.out.println("✅ Gêneros inseridos!");
            }

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
        };
    }
}
