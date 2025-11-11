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

        	 // -------------------------
            // 1) GÊNEROS (cria ou reutiliza)
            // -------------------------
            // lista inicial de nomes de gêneros desejados
            List<String> nomesGeneros = List.of(
                "Ação","Aventura","Comédia","Drama","Ficção Científica","Suspense","Terror",
                "Romance","Fantasia","Animação","Documentário","Crime","Mistério","Biografia","Família"
            );

            // Map para cache local: chave = nomeLower -> Genero persistido
            Map<String, Genero> gByName = new HashMap<>();

            // garante que cada gênero exista no banco e no mapa
            for (String nome : nomesGeneros) {
                String key = nome.toLowerCase();
                if (gByName.containsKey(key)) continue;

                // tenta achar no banco (case-insensitive)
                Genero genero = generoRepo.findByNomeIgnoreCase(nome).orElseGet(() -> {
                    // se não existe, salva novo
                    Genero novo = new Genero(nome);
                    return generoRepo.save(novo);
                });

                gByName.put(key, genero);
            }

            // helper para obter ou criar (e garantir persistência)
            java.util.function.Function<String, Genero> G = name -> {
                if (name == null) return null;
                String key = name.toLowerCase();
                return gByName.computeIfAbsent(key, k -> {
                    // dupla verificação no banco (caso alguém não esteja no mapa)
                    return generoRepo.findByNomeIgnoreCase(name)
                            .orElseGet(() -> generoRepo.save(new Genero(name)));
                });
            };


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

                /* ---- lista extensa continua ---- */

                filmes.add(make("Fight Club", "Um homem desiludido cria um clube de luta que sai do controle.", 1999, List.of(G.apply("Drama"), G.apply("Suspense")), "David Fincher", 139));
                filmes.add(make("The Matrix Reloaded", "Continuação da jornada de Neo contra o sistema.", 2003, List.of(G.apply("Ficção Científica"), G.apply("Ação")), "Lana e Lilly Wachowski", 138));
                filmes.add(make("The Matrix Revolutions", "Conclusão da trilogia Matrix.", 2003, List.of(G.apply("Ficção Científica"), G.apply("Ação")), "Lana e Lilly Wachowski", 129));
                filmes.add(make("Alien", "Tripulação enfrenta um predador alienígena numa nave.", 1979, List.of(G.apply("Terror"), G.apply("Ficção Científica")), "Ridley Scott", 117));
                filmes.add(make("Aliens", "Retorno aos Xenomorfos com ação intensa.", 1986, List.of(G.apply("Ação"), G.apply("Ficção Científica")), "James Cameron", 137));
                filmes.add(make("The Terminator", "Máquina do futuro vem matar a líder da resistência.", 1984, List.of(G.apply("Ação"), G.apply("Ficção Científica")), "James Cameron", 107));
                filmes.add(make("Terminator 2: Judgment Day", "Ação e efeitos na proteção do futuro líder.", 1991, List.of(G.apply("Ação"), G.apply("Ficção Científica")), "James Cameron", 137));
                filmes.add(make("Back to the Future", "Viagem no tempo muda destinos de uma família.", 1985, List.of(G.apply("Aventura"), G.apply("Comédia"), G.apply("Ficção Científica")), "Robert Zemeckis", 116));
                filmes.add(make("The Lord of the Rings: The Fellowship of the Ring", "Início da jornada para destruir o Anel.", 2001, List.of(G.apply("Fantasia"), G.apply("Aventura")), "Peter Jackson", 178));
                filmes.add(make("The Lord of the Rings: The Two Towers", "A batalha pela Terra-média se intensifica.", 2002, List.of(G.apply("Fantasia"), G.apply("Aventura")), "Peter Jackson", 179));
                filmes.add(make("The Lord of the Rings: The Return of the King", "Conlusão épica da trilogia do Anel.", 2003, List.of(G.apply("Fantasia"), G.apply("Aventura")), "Peter Jackson", 201));
                filmes.add(make("The Hobbit: An Unexpected Journey", "Aventura de Bilbo com anões e dragões.", 2012, List.of(G.apply("Fantasia"), G.apply("Aventura")), "Peter Jackson", 169));
                filmes.add(make("The Avengers", "Heróis se unem para enfrentar ameaça global.", 2012, List.of(G.apply("Ação"), G.apply("Aventura")), "Joss Whedon", 143));
                filmes.add(make("Avengers: Endgame", "Heróis tentam consertar o tempo e salvar o universo.", 2019, List.of(G.apply("Ação"), G.apply("Aventura")), "Anthony e Joe Russo", 181));
                filmes.add(make("Guardians of the Galaxy", "Equipe improvável viaja pelo espaço em aventuras engraçadas.", 2014, List.of(G.apply("Ação"), G.apply("Comédia"), G.apply("Aventura")), "James Gunn", 121));
                filmes.add(make("Logan", "Última jornada de um herói envelhecido.", 2017, List.of(G.apply("Ação"), G.apply("Drama")), "James Mangold", 137));
                filmes.add(make("Black Panther", "Rei de Wakanda enfrenta desafios e alianças.", 2018, List.of(G.apply("Ação"), G.apply("Aventura")), "Ryan Coogler", 134));
                filmes.add(make("Oldboy", "Trama de vingança e mistério intenso.", 2003, List.of(G.apply("Mistério"), G.apply("Suspense")), "Park Chan-wook", 120));
                filmes.add(make("Memories of Murder", "Detetives investigam serial killer em cidade pequena.", 2003, List.of(G.apply("Crime"), G.apply("Drama"), G.apply("Mistério")), "Bong Joon-ho", 132));
                filmes.add(make("Train to Busan", "Surto zumbi em trem lotado causa caos e sacrifício.", 2016, List.of(G.apply("Terror"), G.apply("Ação")), "Yeon Sang-ho", 118));
                filmes.add(make("The Handmaiden", "Drama erótico com reviravoltas em trama de engano.", 2016, List.of(G.apply("Drama"), G.apply("Mistério")), "Park Chan-wook", 145));
                filmes.add(make("Seven", "Dois detetives perseguem serial killer com sete pecados.", 1995, List.of(G.apply("Crime"), G.apply("Suspense")), "David Fincher", 127));
                filmes.add(make("Gone Girl", "Mistério em torno do desaparecimento de uma esposa.", 2014, List.of(G.apply("Mistério"), G.apply("Drama")), "David Fincher", 149));
                filmes.add(make("Zodiac", "Caçada verdadeira ao assassino do Zodíaco.", 2007, List.of(G.apply("Crime"), G.apply("Drama")), "David Fincher", 157));
                filmes.add(make("No Country for Old Men", "Violência e destino colidem na América profunda.", 2007, List.of(G.apply("Crime"), G.apply("Drama")), "Coen Brothers", 122));
                filmes.add(make("Fargo", "Crime e humor negro em pequenas cidades do norte.", 1996, List.of(G.apply("Crime"), G.apply("Comédia"), G.apply("Drama")), "Coen Brothers", 98));
                filmes.add(make("The Big Lebowski", "Comédia cult envolvendo um caso de identidade trocada.", 1998, List.of(G.apply("Comédia"), G.apply("Crime")), "Coen Brothers", 117));
                filmes.add(make("The Departed", "Guerra entre policiais e mafiosos com agentes infiltrados.", 2006, List.of(G.apply("Crime"), G.apply("Drama")), "Martin Scorsese", 151));
                filmes.add(make("Goodfellas", "Ascensão e queda na máfia vista de dentro.", 1990, List.of(G.apply("Crime"), G.apply("Drama")), "Martin Scorsese", 146));
                filmes.add(make("Taxi Driver", "Homem solitário enlouquece nas ruas de Nova York.", 1976, List.of(G.apply("Drama")), "Martin Scorsese", 114));
                filmes.add(make("Schindler's List", "Empresário salva judeus durante a Segunda Guerra.", 1993, List.of(G.apply("Drama"), G.apply("Biografia")), "Steven Spielberg", 195));
                filmes.add(make("Saving Private Ryan", "Missão para resgatar soldado durante a guerra.", 1998, List.of(G.apply("Guerra"), G.apply("Drama")), "Steven Spielberg", 169));
                filmes.add(make("E.T. the Extra-Terrestrial", "Amizade entre garoto e alienígena perdido na Terra.", 1982, List.of(G.apply("Ficção Científica"), G.apply("Família")), "Steven Spielberg", 115));
                filmes.add(make("Jurassic Park", "Dinossauros ressuscitados causam caos em parque temático.", 1993, List.of(G.apply("Aventura"), G.apply("Ficção Científica")), "Steven Spielberg", 127));
                filmes.add(make("Pulp Fiction", "Histórias interligadas de criminosos em Los Angeles.", 1994, List.of(G.apply("Crime"), G.apply("Comédia")), "Quentin Tarantino", 154));
                filmes.add(make("Reservoir Dogs", "Roubo que dá errado examinado por suspeitos.", 1992, List.of(G.apply("Crime"), G.apply("Suspense")), "Quentin Tarantino", 99));
                filmes.add(make("Kill Bill: Vol. 1", "Uma noiva busca vingança contra seu antigo clã.", 2003, List.of(G.apply("Ação"), G.apply("Crime")), "Quentin Tarantino", 111));
                filmes.add(make("Kill Bill: Vol. 2", "Conclusão violenta da busca por vingança.", 2004, List.of(G.apply("Ação"), G.apply("Crime")), "Quentin Tarantino", 136));
                filmes.add(make("Django Unchained", "Caçador de recompensas liberta um homem escravizado.", 2012, List.of(G.apply("Western"), G.apply("Drama"), G.apply("Crime")), "Quentin Tarantino", 165));
                filmes.add(make("The Hateful Eight", "Reunião tensa em cabana durante nevasca.", 2015, List.of(G.apply("Crime"), G.apply("Mistério")), "Quentin Tarantino", 187));
                filmes.add(make("Moonlight", "Jornada de autodescoberta de um jovem em Miami.", 2016, List.of(G.apply("Drama")), "Barry Jenkins", 111));
                filmes.add(make("La Haine", "Três jovens enfrentam tensão social e violência urbana.", 1995, List.of(G.apply("Drama")), "Mathieu Kassovitz", 98));
                filmes.add(make("The Pianist", "Sobrevivência de músico judeu na Segunda Guerra.", 2002, List.of(G.apply("Drama"), G.apply("Biografia")), "Roman Polanski", 150));
                filmes.add(make("A Clockwork Orange", "Sociedade, violência e reabilitação forçada.", 1971, List.of(G.apply("Drama"), G.apply("Ficção Científica")), "Stanley Kubrick", 136));
                filmes.add(make("2001: A Space Odyssey", "Viagem filosófica e visual pelo espaço.", 1968, List.of(G.apply("Ficção Científica")), "Stanley Kubrick", 149));
                filmes.add(make("The Shining", "Isolamento e loucura em hotel assombrado.", 1980, List.of(G.apply("Terror"), G.apply("Drama")), "Stanley Kubrick", 146));
                filmes.add(make("Full Metal Jacket", "Treinamento e combate na Guerra do Vietnã.", 1987, List.of(G.apply("Guerra"), G.apply("Drama")), "Stanley Kubrick", 116));
                filmes.add(make("Cinema Paradiso", "Amor pelo cinema em vila italiana.", 1988, List.of(G.apply("Drama"), G.apply("Romance")), "Giuseppe Tornatore", 155));
                filmes.add(make("Slumdog Millionaire", "Jovem relembrando vida ao participar de game show.", 2008, List.of(G.apply("Drama"), G.apply("Romance")), "Danny Boyle", 120));
                filmes.add(make("Trainspotting", "Jovens britânicos e os excessos da vida urbana.", 1996, List.of(G.apply("Drama")), "Danny Boyle", 94));
                filmes.add(make("The Revenant", "Sobrevivência brutal e vingança no front pioneiro.", 2015, List.of(G.apply("Aventura"), G.apply("Drama")), "Alejandro G. Iñárritu", 156));
                filmes.add(make("Birdman", "Ator tenta recuperar carreira com peça teatral.", 2014, List.of(G.apply("Drama"), G.apply("Comédia")), "Alejandro G. Iñárritu", 119));
                filmes.add(make("Brokeback Mountain", "Amor proibido entre dois cowboys.", 2005, List.of(G.apply("Romance"), G.apply("Drama")), "Ang Lee", 134));
                filmes.add(make("Life of Pi", "Sobrevivência fantástica de garoto em barco com tigre.", 2012, List.of(G.apply("Aventura"), G.apply("Fantasia")), "Ang Lee", 127));
                filmes.add(make("The King's Speech", "Rei supera gagueira com ajuda de terapeuta.", 2010, List.of(G.apply("Drama"), G.apply("Biografia")), "Tom Hooper", 118));
                filmes.add(make("Slacker", "Retrato em mosaico da cena jovem alternativa.", 1990, List.of(G.apply("Comédia"), G.apply("Drama")), "Richard Linklater", 99));
                filmes.add(make("Boyhood", "Filmado por 12 anos, acompanho amadurecimento de garoto.", 2014, List.of(G.apply("Drama")), "Richard Linklater", 165));
                filmes.add(make("Her", "Homem se apaixona por sistema operacional inteligente.", 2013, List.of(G.apply("Romance"), G.apply("Ficção Científica")), "Spike Jonze", 126));
                filmes.add(make("Eternal Sunshine of the Spotless Mind", "Casal apaga memórias de relacionamento conturbado.", 2004, List.of(G.apply("Romance"), G.apply("Ficção Científica")), "Michel Gondry", 108));
                filmes.add(make("Moon", "Homem em base lunar enfrenta crises de identidade.", 2009, List.of(G.apply("Ficção Científica"), G.apply("Drama")), "Duncan Jones", 97));
                filmes.add(make("Ex Machina", "Teste de consciência em IA leva a tensão ética.", 2014, List.of(G.apply("Ficção Científica"), G.apply("Suspense")), "Alex Garland", 108));
                filmes.add(make("The Lobster", "Sociedade bizarra que força encontrar parceiro.", 2015, List.of(G.apply("Comédia"), G.apply("Drama"), G.apply("Ficção Científica")), "Yorgos Lanthimos", 118));
                filmes.add(make("Drive", "Piloto realiza trabalhos perigosos e vive dilema moral.", 2011, List.of(G.apply("Crime"), G.apply("Drama")), "Nicolas Winding Refn", 100));
                filmes.add(make("Blue Velvet", "Segredos sombrios em cidade pacata.", 1986, List.of(G.apply("Mistério"), G.apply("Drama")), "David Lynch", 120));
                filmes.add(make("Mulholland Drive", "Sonhos e identidade se misturam em Los Angeles.", 2001, List.of(G.apply("Mistério"), G.apply("Drama")), "David Lynch", 147));
                filmes.add(make("The Truman Show", "Vida de homem revelada como reality show sem seu conhecimento.", 1998, List.of(G.apply("Comédia"), G.apply("Drama")), "Peter Weir", 103));
                filmes.add(make("The Sixth Sense", "Criança que vê pessoas mortas pede ajuda.", 1999, List.of(G.apply("Suspense"), G.apply("Mistério")), "M. Night Shyamalan", 107));
                filmes.add(make("The Others", "Casa antiga, regras e segredos sobrenaturais.", 2001, List.of(G.apply("Terror"), G.apply("Mistério")), "Alejandro Amenábar", 104));
                filmes.add(make("The Pianist", "Músico sobrevive ao horror da guerra.", 2002, List.of(G.apply("Drama"), G.apply("Biografia")), "Roman Polanski", 150));
                filmes.add(make("A Separation", "Conflitos íntimos e sociais em família iraniana.", 2011, List.of(G.apply("Drama")), "Asghar Farhadi", 123));
                filmes.add(make("The Intouchables", "Amizade entre tetraplégico e cuidador transforma vidas.", 2011, List.of(G.apply("Drama"), G.apply("Comédia")), "Olivier Nakache & Éric Toledano", 112));
                filmes.add(make("The Wolf of Wall Street", "Ascensão e queda de corretor envolto em excessos.", 2013, List.of(G.apply("Drama"), G.apply("Comédia")), "Martin Scorsese", 180));
                filmes.add(make("Spotlight", "Jornalistas investigam abuso sistemático em instituição.", 2015, List.of(G.apply("Drama")), "Tom McCarthy", 129));
                filmes.add(make("The Imitation Game", "Vida e trabalho de Alan Turing durante a guerra.", 2014, List.of(G.apply("Drama"), G.apply("Biografia")), "Morten Tyldum", 114));
                filmes.add(make("Argo", "Missão audaciosa para resgatar reféns no Irã.", 2012, List.of(G.apply("Drama"), G.apply("Thriller")), "Ben Affleck", 120));
                filmes.add(make("A Beautiful Mind", "Gênio luta contra doença mental e busca redenção.", 2001, List.of(G.apply("Drama"), G.apply("Biografia")), "Ron Howard", 135));
                filmes.add(make("The Great Dictator", "Sátira de Chaplin sobre tirania e esperança.", 1940, List.of(G.apply("Comédia"), G.apply("Drama")), "Charlie Chaplin", 125));
                filmes.add(make("Panther", "Documentário/biografia sobre temas sociais e políticos.", 2019, List.of(G.apply("Documentário")), "Vários", 90));
                filmes.add(make("Inside Out", "Emoções lutam para guiar infância de uma garota.", 2015, List.of(G.apply("Animação"), G.apply("Família"), G.apply("Comédia")), "Pete Docter", 95));
                filmes.add(make("The Lion King", "Jornada de um jovem leão para aceitar seu destino.", 1994, List.of(G.apply("Animação"), G.apply("Família")), "Roger Allers & Rob Minkoff", 88));
                filmes.add(make("Finding Nemo", "Peixe perdido em grande jornada para casa.", 2003, List.of(G.apply("Animação"), G.apply("Família")), "Andrew Stanton", 100));
                filmes.add(make("The Incredibles", "Família de super-heróis volta a ação em segredo.", 2004, List.of(G.apply("Animação"), G.apply("Ação")), "Brad Bird", 115));
                filmes.add(make("Monsters, Inc.", "Monstros, sustos e amizade num mundo paralelo.", 2001, List.of(G.apply("Animação"), G.apply("Comédia")), "Pete Docter", 92));
                filmes.add(make("Shutter Island", "Misterioso hospital psiquiátrico guarda segredos.", 2010, List.of(G.apply("Mistério"), G.apply("Suspense")), "Martin Scorsese", 138));
                filmes.add(make("The Silence of the Lambs", "Investigação com auxílio de um prisioneiro brilhante.", 1991, List.of(G.apply("Crime"), G.apply("Suspense")), "Jonathan Demme", 118));
                filmes.add(make("The Exorcist", "Confronto aterrorizante com possessão demoníaca.", 1973, List.of(G.apply("Terror")), "William Friedkin", 122));
                filmes.add(make("The Farewell", "Mentira familiar para proteger idosa do diagnóstico.", 2019, List.of(G.apply("Drama"), G.apply("Comédia")), "Lulu Wang", 100));
                filmes.add(make("Moonrise Kingdom", "Fuga romântica infantil e caos em ilha pequena.", 2012, List.of(G.apply("Comédia"), G.apply("Drama")), "Wes Anderson", 94));
                filmes.add(make("The Shape of Water", "Amor improvável entre mulher e criatura anfíbia.", 2017, List.of(G.apply("Fantasia"), G.apply("Romance")), "Guillermo del Toro", 123));
                filmes.add(make("The Florida Project", "Infância precária perto dos parques temáticos.", 2017, List.of(G.apply("Drama")), "Sean Baker", 111));
                filmes.add(make("Spotlight", "Equipe jornalística expõe escândalo institucional.", 2015, List.of(G.apply("Drama")), "Tom McCarthy", 129));
                filmes.add(make("Room", "Mãe e filho tentam reconstruir liberdade após cativeiro.", 2015, List.of(G.apply("Drama")), "Lenny Abrahamson", 118));
                filmes.add(make("The Hunt", "Acusação falsa leva a consequências trágicas.", 2012, List.of(G.apply("Drama")), "Thomas Vinterberg", 115));
                filmes.add(make("The Sea Inside", "Luta por dignidade e direito de morrer com autonomia.", 2004, List.of(G.apply("Drama"), G.apply("Biografia")), "Alejandro Amenábar", 125));
                filmes.add(make("The Bicycle Thief", "Homem busca bicicleta essencial para seu trabalho.", 1948, List.of(G.apply("Drama")), "Vittorio De Sica", 89));
                filmes.add(make("Rashomon", "Versões conflitantes de um mesmo crime revelam subjetividade.", 1950, List.of(G.apply("Drama"), G.apply("Mistério")), "Akira Kurosawa", 88));
                filmes.add(make("Seven Samurai", "Camponeses contratam samurais para defender aldeia.", 1954, List.of(G.apply("Ação"), G.apply("Drama")), "Akira Kurosawa", 207));
                filmes.add(make("Ikiru", "Homem busca sentido para vida antes de morrer.", 1952, List.of(G.apply("Drama")), "Akira Kurosawa", 143));
                filmes.add(make("Spirited Away", "Jovem menina entra em mundo mágico cheio de espíritos.", 2001, List.of(G.apply("Animação"), G.apply("Fantasia")), "Hayao Miyazaki", 125));
                filmes.add(make("My Neighbor Totoro", "Encontro com espíritos gentis na infância.", 1988, List.of(G.apply("Animação"), G.apply("Fantasia"), G.apply("Família")), "Hayao Miyazaki", 86));
                filmes.add(make("Princess Mononoke", "Conflito entre industrialização e espíritos da floresta.", 1997, List.of(G.apply("Fantasia"), G.apply("Aventura")), "Hayao Miyazaki", 134));
                filmes.add(make("Grave of the Fireflies", "Irmãos lutam para sobreviver na guerra.", 1988, List.of(G.apply("Drama"), G.apply("Animação")), "Isao Takahata", 89));
                filmes.add(make("Kubo and the Two Strings", "Aventura fantástica com elementos de conto e perda.", 2016, List.of(G.apply("Animação"), G.apply("Fantasia")), "Travis Knight", 101));
                filmes.add(make("The Grandmaster", "História de mestre de artes marciais e sua época.", 2013, List.of(G.apply("Drama"), G.apply("Ação")), "Wong Kar-wai", 130));
                filmes.add(make("In the Mood for Love", "Dois vizinhos constroem laços num amor contido.", 2000, List.of(G.apply("Romance"), G.apply("Drama")), "Wong Kar-wai", 98));
                filmes.add(make("The Hand That Rocks the Cradle", "Intriga e perigo vieram na forma de uma babá.", 1992, List.of(G.apply("Suspense"), G.apply("Terror")), "Curtis Hanson", 110));
                filmes.add(make("The Wicker Man", "Mistério e rituais em ilha isolada.", 1973, List.of(G.apply("Mistério"), G.apply("Terror")), "Robin Hardy", 88));
                filmes.add(make("Rebecca", "Mistério e tensão em mansão gótica.", 1940, List.of(G.apply("Mistério"), G.apply("Romance")), "Alfred Hitchcock", 130));
                filmes.add(make("Psycho", "Clássico do suspense com reviravolta icônica.", 1960, List.of(G.apply("Suspense"), G.apply("Terror")), "Alfred Hitchcock", 109));
                filmes.add(make("Vertigo", "Obssessão, identidade e queda emocional.", 1958, List.of(G.apply("Mistério"), G.apply("Drama")), "Alfred Hitchcock", 128));
                filmes.add(make("North by Northwest", "Conspiração e perseguição em ritmo de aventura.", 1959, List.of(G.apply("Ação"), G.apply("Suspense")), "Alfred Hitchcock", 136));
                filmes.add(make("The Artist", "Filme mudo conta ascensão e queda de ator.", 2011, List.of(G.apply("Drama"), G.apply("Comédia")), "Michel Hazanavicius", 100));
                filmes.add(make("Singin' in the Rain", "Musical clássico sobre transição para filmes falados.", 1952, List.of(G.apply("Musical"), G.apply("Comédia")), "Gene Kelly", 103));
                filmes.add(make("Moulin Rouge!", "Musical extravagante de amor e paixão.", 2001, List.of(G.apply("Musical"), G.apply("Romance")), "Baz Luhrmann", 127));
                filmes.add(make("Chicago", "Musical de fama, crime e intriga nos bastidores.", 2002, List.of(G.apply("Musical"), G.apply("Crime")), "Rob Marshall", 113));
                filmes.add(make("Once", "Música e conexão humana transformam vidas.", 2007, List.of(G.apply("Romance"), G.apply("Musical")), "John Carney", 85));
                filmes.add(make("The Last King of Scotland", "Ditador e médico em relação de poder e corrupção.", 2006, List.of(G.apply("Drama"), G.apply("Biografia")), "Kevin Macdonald", 123));
                filmes.add(make("Hotel Rwanda", "Homem abriga centenas durante genocídio.", 2004, List.of(G.apply("Drama"), G.apply("Biografia")), "Terry George", 121));
                filmes.add(make("12 Years a Slave", "Homem livre é sequestrado e escravizado por anos.", 2013, List.of(G.apply("Drama"), G.apply("Biografia")), "Steve McQueen", 134));
                filmes.add(make("The Lives of Others", "Vigilância e consciência na Alemanha Oriental.", 2006, List.of(G.apply("Drama")), "Florian Henckel von Donnersmarck", 137));
                filmes.add(make("The Diving Bell and the Butterfly", "Jornalista enfrenta mundo preso ao corpo.", 2007, List.of(G.apply("Drama"), G.apply("Biografia")), "Julian Schnabel", 112));
                filmes.add(make("The Third Man", "Mistério noir nas ruas de Viena pós-guerra.", 1949, List.of(G.apply("Noir"), G.apply("Mistério")), "Carol Reed", 104));
                filmes.add(make("Metropolis", "Clássico expressionista sobre sociedade e tecnologia.", 1927, List.of(G.apply("Ficção Científica"), G.apply("Drama")), "Fritz Lang", 153));
                filmes.add(make("The General", "Comédia física e perseguição durante a guerra civil.", 1926, List.of(G.apply("Comédia"), G.apply("Aventura")), "Buster Keaton", 75));
                filmes.add(make("Sunset Boulevard", "Drama ácido sobre fama e decadência em Hollywood.", 1950, List.of(G.apply("Drama")), "Billy Wilder", 110));
                filmes.add(make("Some Like It Hot", "Comédia clássica de disfarces e romance.", 1959, List.of(G.apply("Comédia")), "Billy Wilder", 121));
                filmes.add(make("The Apartment", "Trama de intrigas e segundas chances no trabalho.", 1960, List.of(G.apply("Comédia"), G.apply("Drama")), "Billy Wilder", 125));
                filmes.add(make("The 400 Blows", "Infância difícil e busca por liberdade.", 1959, List.of(G.apply("Drama")), "François Truffaut", 99));
                filmes.add(make("Breathless", "Nova onda francesa que redefine narrativa e estilo.", 1960, List.of(G.apply("Drama")), "Jean-Luc Godard", 90));
                filmes.add(make("Amadeus", "Gênio musical e rivalidade no século 18.", 1984, List.of(G.apply("Drama"), G.apply("Biografia")), "Milos Forman", 160));
                filmes.add(make("The Last Emperor", "Biografia épica de imperador da China.", 1987, List.of(G.apply("Drama"), G.apply("Biografia")), "Bernardo Bertolucci", 163));
                filmes.add(make("The Bicycle Thief", "Drama humano sobre desemprego e dignidade.", 1948, List.of(G.apply("Drama")), "Vittorio De Sica", 89));
                filmes.add(make("Persona", "Exploração psicológica da identidade feminina.", 1966, List.of(G.apply("Drama")), "Ingmar Bergman", 83));
                filmes.add(make("Wild Strawberries", "Reflexões de um homem sobre sua vida e memórias.", 1957, List.of(G.apply("Drama")), "Ingmar Bergman", 91));
                filmes.add(make("The Seventh Seal", "Cavaleiro joga xadrez com a Morte.", 1957, List.of(G.apply("Drama"), G.apply("Fantasia")), "Ingmar Bergman", 96));
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
