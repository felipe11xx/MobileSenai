package br.senai.sp.informatica.mobileb.pokedex.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;

/**
 * Created by WEB on 31/10/2017.
 */

public class PokemonAdapter extends BaseAdapter {

    private PokemonDao dao = PokemonDao.manager;
    private Map<Integer,Long> mapa;
    //private boolean statusApaga;

    public PokemonAdapter() {
        criarMapa();
    }

    @Override
    public void notifyDataSetChanged() {

        criarMapa() ;
        super.notifyDataSetChanged();
    }

    private void criarMapa(){
        //Cira mapa com associação de linha e ID
        mapa = new HashMap<>();
        //Recebe as lista de obj DAO
        List<Pokemon> lista = dao.getList();

        //associa o id com o numero da linha
        for (int linha = 0; linha < lista.size(); linha++ ){
            Pokemon pokemon = lista.get(linha);
            mapa.put(linha, pokemon.getId());
        }
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getPokemon((long)id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {

        ConstraintLayout layout;
        if(view == null) {

            Context ctx = viewGroup.getContext();
            // localizar o serviço de construção do layout
            LayoutInflater inflater =
                    (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // informar o layout xml a ser carregado
            layout = new ConstraintLayout(ctx);

            inflater.inflate(R.layout.detalhe_layout,layout);
            // iniciliazar o ConstraintLayout a partir da carga
        } else {
            layout = (ConstraintLayout)view;
        }

        // o registro da posição solicitada e encontrar o objeto
        // atribuir o objeto ao layout
        TextView tvPokemon = layout.findViewById(R.id.nomeTxt);
        TextView tvTipo1 = layout.findViewById(R.id.tipo1txt);
        TextView tvTipo2 = layout.findViewById(R.id.tipo2Txt);
        TextView tvDxNum = layout.findViewById(R.id.dexNumTxt);
        TextView tvDtCap = layout.findViewById(R.id.dtCaptTxt);

        Pokemon pokemon = dao.getPokemon(mapa.get(linha));
        tvPokemon.setText(pokemon.getNome());
        tvTipo1.setText(pokemon.getTipo1());
        tvTipo2.setText(pokemon.getTipo2());
        tvDtCap.setText(pokemon.getDtCaptura());
        tvDxNum.setText("Numero Dex #"+String.valueOf(pokemon.getDexNum()));
        return layout;
    }


}
