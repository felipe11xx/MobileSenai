package informatica.sp.senai.br.pokedex.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 16/11/2017.
 */

public class PokemonDao {
    public static PokemonDao manager = new PokemonDao();
    private List<Pokemon> lista;
    private int id = 0;

    private PokemonDao (){
        lista.add(new Pokemon(id++,"Bulbassaur","Grama","Veneno",1,"16/11/2017"));
        lista.add(new Pokemon(id++,"yvessaur","Grama","Veneno",1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Venussar","Grama","Veneno",1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charmander","Fogo",null,1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charmileon","Fogo",null,1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Charizard","Fogo","Voador",1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Squirtle","Água",null,1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Wartotle","Água",null,1,"16/11/2017"));
        lista.add(new Pokemon(id++,"Blastoise","Água",null,1,"16/11/2017"));

    }

    public List<Pokemon> getList(){
        Collections.sort(lista);
        return Collections.synchronizedList(lista);
    }

    public Pokemon getPokemon(){
        Pokemon poke = null;
        for (Pokemon pokemon: lista) {
            if(pokemon.getId() == id){
                poke = pokemon;
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
    public void apagar(int id){
        lista.remove(new Pokemon(id));
    }

}
