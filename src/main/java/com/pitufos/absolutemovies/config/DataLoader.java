package com.pitufos.absolutemovies.config;

import java.util.*;
import java.util.stream.Collectors;

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

            // =========================
            // GÊNEROS
            // =========================
            if (generoRepo.count() == 0) {
                List<Genero> generos = List.of(
                        new Genero("Ação"),
                        new Genero("Aventura"),
                        new Genero("Comédia"),
                        new Genero("Drama"),
                        new Genero("Ficção Científica"),
                        new Genero("Suspense"),
                        new Genero("Terror"),
                        new Genero("Romance"),
                        new Genero("Fantasia"),
                        new Genero("Animação"),
                        new Genero("Documentário"),
                        new Genero("Crime"),
                        new Genero("Mistério"),
                        new Genero("Biografia"),
                        new Genero("Família")
                );
                generoRepo.saveAll(generos);
                System.out.println("✅ Gêneros inseridos: " + generos.stream().map(Genero::getNome).collect(Collectors.joining(", ")));
            }

            // busca todos os gêneros salvos (ordem não garantida)
            List<Genero> savedGeneros = generoRepo.findAll();
            Map<String, Genero> gByName = savedGeneros.stream()
                    .collect(Collectors.toMap(gen -> gen.getNome().toLowerCase(), gen -> gen));

            // helper para obter genero (com fallback)
            java.util.function.Function<String, Genero> G = name -> Optional.ofNullable(gByName.get(name.toLowerCase()))
                    .orElse(null);

            // =========================
            // FILMES (diversos)
            // =========================
            if (filmeRepo.count() == 0) {
                List<Filme> filmes = new ArrayList<>();

                filmes.add(make("Matrix", "Um hacker descobre a verdade sobre a realidade.", 1999, List.of(G.apply("Ficção Científica"), G.apply("Ação")), "Lana e Lilly Wachowski", 136));
                filmes.add(make("Blade Runner 2049", "Um caçador de replicantes descobre um segredo que pode alterar o destino da humanidade.", 2017, List.of(G.apply("Ficção Científica"), G.apply("Drama")), "Denis Villeneuve", 164));
                filmes.add(make("Inception", "Um ladrão que invade sonhos precisa plantar uma ideia na mente de alguém.", 2010, List.of(G.apply("Ficção Científica"), G.apply("Suspense")), "Christopher Nolan", 148));
                filmes.add(make("Mad Max: Estrada da Fúria", "Em um mundo pós-apocalíptico, uma mulher e um exército salvam prisioneiros.", 2015, List.of(G.apply("Ação"), G.apply("Aventura")), "George Miller", 120));
                filmes.add(make("The Dark Knight", "Batman enfrenta o Coringa em Gotham.", 2008, List.of(G.apply("Ação"), G.apply("Crime"), G.apply("Drama")), "Christopher Nolan", 152));
                filmes.add(make("Forrest Gump", "A vida extraordinária de um homem simples que testemunha grandes acontecimentos.", 1994, List.of(G.apply("Drama"), G.apply("Romance")), "Robert Zemeckis", 142));
                filmes.add(make("The Shawshank Redemption", "Amizade e esperança dentro de uma prisão.", 1994, List.of(G.apply("Drama"), G.apply("Crime")), "Frank Darabont", 142));
                filmes.add(make("Parasite", "Conflitos de classes levam a uma situação imprevisível.", 2019, List.of(G.apply("Drama"), G.apply("Mistério")), "Bong Joon-ho", 132));
                filmes.add(make("The Godfather", "Saga da família Corleone.", 1972, List.of(G.apply("Crime"), G.apply("Drama"), G.apply("Biografia")), "Francis Ford Coppola", 175));
                filmes.add(make("The Godfather Part II", "Continuação épica da família Corleone.", 1974, List.of(G.apply("Crime"), G.apply("Drama")), "Francis Ford Coppola", 202));
                filmes.add(make("Toy Story", "Aventura dos brinquedos que ganham vida.", 1995, List.of(G.apply("Animação"), G.apply("Família"), G.apply("Comédia")), "John Lasseter", 81));
                filmes.add(make("Spider-Man: Into the Spider-Verse", "Miles Morales descobre o multiverso dos Spider-heroes.", 2018, List.of(G.apply("Animação"), G.apply("Ação"), G.apply("Aventura")), "Bob Persichetti", 117));
                filmes.add(make("The Conjuring", "Investigação de fenômenos paranormais pela família Warren.", 2013, List.of(G.apply("Terror"), G.apply("Mistério")), "James Wan", 112));
                filmes.add(make("Get Out", "Suspense social com pitadas de terror.", 2017, List.of(G.apply("Terror"), G.apply("Suspense")), "Jordan Peele", 104));
                filmes.add(make("La La Land", "Romance musical entre um pianista e uma atriz em Los Angeles.", 2016, List.of(G.apply("Romance"), G.apply("Drama")), "Damien Chazelle", 128));
                filmes.add(make("The Grand Budapest Hotel", "Comédia dramática de um concierge e suas aventuras.", 2014, List.of(G.apply("Comédia"), G.apply("Drama")), "Wes Anderson", 99));
                filmes.add(make("The Social Network", "A criação do Facebook e seus conflitos.", 2010, List.of(G.apply("Drama"), G.apply("Biografia")), "David Fincher", 120));
                filmes.add(make("Interstellar", "Viagem espacial em busca de um novo lar para a humanidade.", 2014, List.of(G.apply("Ficção Científica"), G.apply("Aventura")), "Christopher Nolan", 169));
                filmes.add(make("The Prestige", "Rivais mágicos dispostos a tudo.", 2006, List.of(G.apply("Mistério"), G.apply("Drama")), "Christopher Nolan", 130));
                filmes.add(make("Amélie", "Comédia romântica sobre a vida de uma jovem em Paris.", 2001, List.of(G.apply("Comédia"), G.apply("Romance")), "Jean-Pierre Jeunet", 122));
                filmes.add(make("Whiplash", "Professor exigente e um jovem baterista em busca da perfeição.", 2014, List.of(G.apply("Drama")), "Damien Chazelle", 106));
                filmes.add(make("Coco", "Um menino entra no Mundo dos Mortos para descobrir sua história.", 2017, List.of(G.apply("Animação"), G.apply("Família")), "Lee Unkrich", 105));
                filmes.add(make("Joker", "Origem sombria de um dos maiores vilões.", 2019, List.of(G.apply("Drama"), G.apply("Crime")), "Todd Phillips", 122));
                filmes.add(make("Citizenfour", "Documentário sobre vigilância e Snowden.", 2014, List.of(G.apply("Documentário")), "Laura Poitras", 114));
                filmes.add(make("Pan's Labyrinth", "Fantasia sombria na Espanha pós-guerra.", 2006, List.of(G.apply("Fantasia"), G.apply("Drama")), "Guillermo del Toro", 118));

                // remove possíveis nulos (caso algum gênero não tenha sido encontrado)
                filmes = filmes.stream().filter(Objects::nonNull).collect(Collectors.toList());

                filmeRepo.saveAll(filmes);
                System.out.println("🎬 " + filmes.size() + " filmes inseridos!");
            }

            // =========================
            // USUÁRIOS
            // =========================
            Usuario user1;
            Usuario user2;
            if (usuarioRepo.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                user1 = new Usuario("Breno Martins", "breno@gmail.com", encoder.encode("123456"));
                user1.setPreferencias(List.of(G.apply("Ação"), G.apply("Ficção Científica")));

                user2 = new Usuario("Mariana Silva", "mariana@gmail.com", encoder.encode("senha123"));
                user2.setPreferencias(List.of(G.apply("Drama"), G.apply("Romance")));

                usuarioRepo.saveAll(List.of(user1, user2));
                System.out.println("👤 Usuários de teste criados: " + user1.getEmail() + ", " + user2.getEmail());
            } else {
                List<Usuario> u = usuarioRepo.findAll();
                user1 = u.get(0);
                user2 = u.size() > 1 ? u.get(1) : u.get(0);
            }

            // =========================
            // FAVORITOS (por usuário)
            // =========================
            if (favoritoRepo.count() == 0) {
                List<Filme> filmes = filmeRepo.findAll();

                // favoritos do user1 (Breno) — gosta de ação / sci-fi
                List<Favorito> favsUser1 = List.of(
                        new Favorito(user1, findByTitle(filmes, "Matrix")),
                        new Favorito(user1, findByTitle(filmes, "Inception")),
                        new Favorito(user1, findByTitle(filmes, "Interstellar"))
                );

                // favoritos do user2 (Mariana) — gosta de drama / romance
                List<Favorito> favsUser2 = List.of(
                        new Favorito(user2, findByTitle(filmes, "Forrest Gump")),
                        new Favorito(user2, findByTitle(filmes, "La La Land")),
                        new Favorito(user2, findByTitle(filmes, "Amélie"))
                );

                List<Favorito> allFavs = new ArrayList<>();
                allFavs.addAll(favsUser1.stream().filter(Objects::nonNull).collect(Collectors.toList()));
                allFavs.addAll(favsUser2.stream().filter(Objects::nonNull).collect(Collectors.toList()));

                favoritoRepo.saveAll(allFavs);
                System.out.println("❤️ Favoritos criados para usuários de teste");
            }

            // =========================
            // INTERAÇÕES (avaliações)
            // =========================
            if (interacaoRepo.count() == 0) {
                List<Filme> filmes = filmeRepo.findAll();

                Interacao i1 = new Interacao(user1, findByTitle(filmes, "Matrix"), 5);
                Interacao i2 = new Interacao(user1, findByTitle(filmes, "Inception"), 4);
                Interacao i3 = new Interacao(user1, findByTitle(filmes, "Interstellar"), 5);

                Interacao i4 = new Interacao(user2, findByTitle(filmes, "Forrest Gump"), 5);
                Interacao i5 = new Interacao(user2, findByTitle(filmes, "La La Land"), 4);
                Interacao i6 = new Interacao(user2, findByTitle(filmes, "Amélie"), 4);

                List<Interacao> inters = List.of(i1, i2, i3, i4, i5, i6).stream()
                        .filter(Objects::nonNull).collect(Collectors.toList());

                interacaoRepo.saveAll(inters);

                // atualizar histórico bidirecional nos usuários (se quiser)
                user1.getHistorico().addAll(inters.stream().filter(ix -> ix.getUsuario().equals(user1)).collect(Collectors.toList()));
                user2.getHistorico().addAll(inters.stream().filter(ix -> ix.getUsuario().equals(user2)).collect(Collectors.toList()));
                usuarioRepo.saveAll(List.of(user1, user2));

                System.out.println("💬 Interações (avaliações) criadas para usuários de teste");
            }

            System.out.println("=== DATA LOADER: Concluído ===");
        };
    }

    // helper para criar Filme com alguns campos
    private static Filme make(String titulo, String descricao, Integer ano, List<Genero> generos, String diretor, Integer duracao) {
        if (titulo == null || descricao == null) return null;
        Filme f = new Filme(titulo, descricao, ano);
        f.setDiretor(diretor);
        f.setDuracaoMinutos(duracao);
        if (generos != null) f.setGeneros(generos.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        return f;
    }

    // helper para achar filme por título (busca exata ignorando case)
    private static Filme findByTitle(List<Filme> filmes, String title) {
        if (filmes == null || title == null) return null;
        return filmes.stream()
                .filter(Objects::nonNull)
                .filter(f -> f.getTitulo() != null && f.getTitulo().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
