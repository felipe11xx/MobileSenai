package informatica.sp.senai.br.pokedex.model;

import android.support.annotation.NonNull;

/**
 * Created by Felipe on 16/11/2017.
 */

public class Pokemon implements Comparable<Pokemon>{

    private Integer id;
    private String nome;
    private String tipo1;
    private String tipo2;
    private int dexNum;
    private String dtCaptura;

    public Pokemon(Integer id, String nome, String tipo1, String tipo2, int dexNum, String dtCaptura) {
        this.id = id;
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.dexNum = dexNum;
        this.dtCaptura = dtCaptura;
    }

    public Pokemon (){}

    public Pokemon(Integer id) {

        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public int getDexNum() {
        return dexNum;
    }

    public void setDexNum(int dexNum) {
        this.dexNum = dexNum;
    }

    public String getDtCaptura() {
        return dtCaptura;
    }

    public void setDtCaptura(String dtCaptura) {
        this.dtCaptura = dtCaptura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return id == pokemon.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(@NonNull Pokemon outroPokemon) {
        if(dexNum < outroPokemon.getDexNum()){
            return dexNum;
        }else{
            return outroPokemon.getDexNum();
        }


    }
}