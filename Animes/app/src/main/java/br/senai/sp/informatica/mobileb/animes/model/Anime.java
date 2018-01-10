package br.senai.sp.informatica.mobileb.animes.model;

import android.support.annotation.NonNull;

/**
 * Created by WEB on 09/01/2018.
 */

public class Anime implements  Comparable<Anime>{

    private Long id;
    private String nome;
    private String genero;

    public Anime(Long id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

    public Anime (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public int compareTo(@NonNull Anime anime) {

        return nome.toLowerCase().compareTo(anime.nome.toLowerCase());
    }

    @Override
    public String toString() {
        return "Anime {"+
                "id=" + id +
                ", Nome=' " + nome + '\'' +
                "' genero='" +genero + '\'' +
                "}";
    }
}
