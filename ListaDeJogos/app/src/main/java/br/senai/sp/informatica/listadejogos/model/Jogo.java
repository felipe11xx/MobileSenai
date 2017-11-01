package br.senai.sp.informatica.listadejogos.model;

/**
 * Created by WEB on 31/10/2017.
 */

public class Jogo {

    private Long id;
    private String nome;
    private String genero;

    public Jogo(){}

    public Jogo(Long id){
        this.id = id;
    }

    public Jogo(Long id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jogo jogo = (Jogo) o;

        return id.equals(jogo.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
