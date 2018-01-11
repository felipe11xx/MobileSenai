package br.senai.sp.informatica.mobileb.animes.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.senai.sp.informatica.mobileb.animes.model.Anime;

/**
 * Created by WEB on 09/01/2018.
 */

public class AnimeDao {

    public static AnimeDao manager = new AnimeDao();

    //lista onde sera armazenado os animes
    private List<Anime> lista;

    //Geração do id para cada novo anime
    private long id = 0;

    private AnimeDao() {
        lista = new ArrayList<>();
        lista.add(new Anime(id++, "Urusei Yatsura","Comedia"));
        lista.add(new Anime(id++, "One Piece", "Aventura"));
        lista.add(new Anime(id++, "Another",  "Suspense"));
        lista.add(new Anime(id++, "Fullmetal Alchemist Brotherhood", "Ação"));
        lista.add(new Anime(id++, "Soul Eater", "ficção"));
        lista.add(new Anime(id++, "One Punch Man", "Luta"));
        lista.add(new Anime(id++, "Trigun", "faroeste-espacial"));

    }

    public List<Anime> getLista() {

        Collections.sort(lista);
        return Collections.unmodifiableList(lista);
    }

}
