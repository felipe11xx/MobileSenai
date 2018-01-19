package br.senai.sp.informatica.mobileb.pokedex.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;
import br.senai.sp.informatica.mobileb.pokedex.util.Utilitarios;


/**
 * Created by WEB on 31/10/2017.
 */

public class PokemonAdapter extends BaseAdapter {

    private final Activity activity;
    private PokemonDao dao = PokemonDao.manager;
    private Map<Integer,Long> mapa;
    private static DateFormat dtfmt = DateFormat.getDateInstance(DateFormat.LONG);
    //private boolean statusApaga;

    public PokemonAdapter(Activity activity) {

        this.activity = activity;
        criarMapa();
    }


    @Override
    public void notifyDataSetChanged() {

        criarMapa() ;
        super.notifyDataSetChanged();
    }

    private void criarMapa(){

        String ordemPreference = activity.getResources().getString(R.string.ordem_key);

        String ordemDefault = activity.getResources().getString(R.string.ordem_default);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        String ordem = preferences.getString(ordemPreference, ordemDefault);

        //Cira mapa com associação de linha e ID
        mapa = new HashMap<>();
        //Recebe as lista de obj DAO
        List<Long> ids = dao.listarIds(ordem);
        //associa o id com o numero da linha
        for (int linha = 0; linha < ids.size(); linha++ ){

            mapa.put(linha, ids.get(linha));
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
        TextView tvPokemon = layout.findViewById(R.id.cardNome);
        TextView tvTipo1 = layout.findViewById(R.id.cardTipo1);
        TextView tvTipo2 = layout.findViewById(R.id.cardTipo2);
        TextView tvDxNum = layout.findViewById(R.id.dexNumTxt);
        TextView tvDtCap = layout.findViewById(R.id.dtCaptTxt);
        ImageView ivFoto = layout.findViewById(R.id.imagePoke);

        Pokemon pokemon = dao.getPokemon(mapa.get(linha));
        tvPokemon.setText(pokemon.getNome());
        tvTipo1.setText(pokemon.getTipo1());
        tvTipo2.setText(pokemon.getTipo2());
        tvDtCap.setText(dtfmt.format(pokemon.getDtCaptura()));
        tvDxNum.setText("Numero Dex #"+String.valueOf(pokemon.getDexNum()));
        if(pokemon.getFotoPoke() != null) {
            ivFoto.setImageBitmap(Utilitarios.bitmapFromBase64(pokemon.getFotoPoke()));
        }else{
            Drawable draw = ContextCompat.getDrawable(viewGroup.getContext(), R.mipmap.img_pokeball);
            ivFoto.setImageDrawable(draw);
        }

        if(pokemon.isApagar()){
            layout.setBackgroundColor(viewGroup.getResources().getColor(R.color.ItemSelecionado));
        }else{
            layout.setBackgroundColor(viewGroup.getResources().getColor(R.color.fundoDoListView));
        }
        return layout;
    }


}
