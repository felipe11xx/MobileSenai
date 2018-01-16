package br.senai.sp.informatica.mobileb.pokedex.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
        lista.add(new Pokemon(id++,"Bulbasaur","Grama","Veneno",1,new GregorianCalendar(2017, Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Ivysaur","Grama","Veneno",2,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Venusaur","Grama","Veneno",3,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Charmander","Fogo","",4,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Charmeleon","Fogo","",5,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Charizard","Fogo","Voador",6,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Squirtle","Água","",7,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Wartortle","Água","",8,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
        lista.add(new Pokemon(id++,"Blastoise","Água","",9,new GregorianCalendar(2017,Calendar.NOVEMBER,10).getTime()));
    }

    public List<Pokemon> getList(){
        Collections.sort(lista);
        return Collections.synchronizedList(lista);
    }

    public List<Long> listarIds(String ordem){

        if(ordem.equals("Num Dex")){
            Collections.sort(lista);
        }else if(ordem.equals("Nome")){
            Collections.sort(lista,new OrdenaPorNome());
        }else{
            Collections.sort(lista,new OrdenaDtCaptura());
        }

        List<Long> ids = new ArrayList<>();
        for(Pokemon obj: lista){
            ids.add(obj.getId());
        }

        return ids;
    }

    public Pokemon getPokemon(final Long id){
        Pokemon poke = null;
        for (Pokemon pokemon: lista) {
            if(pokemon.getId() == id){
                poke = pokemon;
                break;
            }
        }

//        Pokemon pokeLocalizado = lista.get(lista.indexOf(new Pokemon(id)));

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

    class OrdenaPorNome implements Comparator<Pokemon> {

        @Override
        public int compare(Pokemon poke1, Pokemon poke2) {
            return poke1.getNome().compareToIgnoreCase(poke2.getNome());
        }
    }

    class OrdenaDtCaptura implements Comparator<Pokemon> {

        @Override
        public int compare(Pokemon poke1, Pokemon poke2) {
            return poke1.getDtCaptura().compareTo(poke2.getDtCaptura());
        }
    }
}
