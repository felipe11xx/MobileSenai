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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
