package br.senai.sp.informatica.mobileb.pokedex.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 16/11/2017.
 */

public class PokemonDao {
    public static PokemonDao manager = new PokemonDao();
    private List<Pokemon> lista;
    private long id = 0;

    private PokemonDao (){
        lista = new ArrayList<>();
        lista.add(new Pokemon(id++,"Bulbasaur","Grama","Veneno",1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Ivysaur","Grama","Veneno",2,"16/11/2017"));
        lista.add(new Pokemon(id++,"Venusaur","Grama","Veneno",3,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charmander","Fogo","",4,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charmeleon","Fogo","",5,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charizard","Fogo","Voador",6,"16/11/2017"));
        lista.add(new Pokemon(id++,"Squirtle","Água","",7,"16/11/2017"));
        lista.add(new Pokemon(id++,"Wartortle","Água","",8,"16/11/2017"));
        lista.add(new Pokemon(id++,"Blastoise","Água","",9,"16/11/2017"));
    }

    public List<Pokemon> getList(){
        Collections.sort(lista);
        return Collections.synchronizedList(lista);
    }

    public Pokemon getPokemon(final Long id){
        Pokemon poke = null;
        for (Pokemon pokemon: lista) {
            if(pokemon.getId() == id){
                poke = pokemon;
                break;
            }
        }

        Pokemon pokeLocalizado = lista.get(lista.indexOf(new Pokemon(id)));

        return poke;
    }

    public void salvar(Pokemon poke){
        if(poke.getId() == null){
            poke.setId(id++);
            lista.add(poke);
        }else{
            int posicao = lista.indexOf(new Pokemon(poke.getId()));
            lista.set(posicao, poke);
        }
    }
    public void apagar(long id){
        lista.remove(new Pokemon(id));
    }

}
