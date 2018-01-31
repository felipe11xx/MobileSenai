package br.senai.sp.informatica.mobileb.pokedex.view.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDaoOld;
import br.senai.sp.informatica.mobileb.pokedex.util.Utilitarios;

/**
 * Created by WEB on 19/01/2018.
 */

public class PokemonAdpterRecycle extends RecyclerView.Adapter<PokemonAdpterRecycle.PokemonViewHolder>
            implements  AdapterInterface{

    private PokemonDaoOld dao = PokemonDaoOld.manager;
    private SparseLongArray mapa;
    private Activity activity;
    private OnItemClickListener listener;

    public PokemonAdpterRecycle(Activity activity, OnItemClickListener listener){
        this.activity = activity;
        this.listener = listener;
        criarMapa();
    }

    @Override
    public void setEditar(boolean value) {

    }

    @Override
    public void notificaAtualizacao() {
        criarMapa();
        notifyDataSetChanged();
    }

    public void criarMapa(){
        // Obtém a identificação da preferência para Ordenação
        String ordemPreference = activity.getResources().getString(R.string.ordem_key);
        // Obtém o valor padrão para a Ordenação
        String ordemDefault = activity.getResources().getString(R.string.ordem_default);
        // Obtém o recurso de leitura de preferências
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        // Localiza a configuração selecionada para Ordenação de Albuns
        String ordem = preferences.getString(ordemPreference, ordemDefault);

        mapa = new SparseLongArray();
        List<Long> ids = dao.listarIds(ordem);
        for (int linha = 0; linha < ids.size(); linha++) {
            mapa.put(linha, ids.get(linha));
        }
    }

    @Override
    public int getItemCount() {
        return mapa.size();
    }


    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater svc = LayoutInflater.from(parent.getContext());
        View layout = svc.inflate(R.layout.adapter_card, parent, false);
        return new PokemonViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        Pokemon obj = dao.getPokemon(mapa.get(position));
        holder.setView(obj);
    }



    public class PokemonViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{
        private ImageView imgPoke;
        private TextView nome;
        private TextView tipo1;
        private TextView tipo2;
        private View view;


        public PokemonViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            nome = itemView.findViewById(R.id.cardNome);
            tipo1 = itemView.findViewById(R.id.cardTipo1);
            tipo2 = itemView.findViewById(R.id.cardTipo2);
            imgPoke =itemView.findViewById(R.id.cardImgPoke);
        }


        public void setView(final Pokemon obj) {
           nome.setText(obj.getNome());
           tipo1.setText(obj.getTipo1());
           tipo2.setText(obj.getTipo2());

           byte[] foto = obj.getFotoPoke();
           if(foto != null){
               //Transforma o vetor de bytes em base64 para bitmap
               Bitmap bitmap = Utilitarios.bitmapFromBase64(foto);
               //Cria uma foto circular e atribui
               imgPoke.setImageBitmap(bitmap);
           }else {
               // Obtem a 1ª letra do nome da pessoa e converte para Maiuscula
               String letra = obj.getNome().substring(0, 1).toUpperCase();
               // Cria um bitmap contendo a letra
               Bitmap bitmap = Utilitarios.circularBitmapAndText(
                       Color.parseColor("#936A4D"), 200, 200, letra);
               // atribui à foto
               imgPoke.setBackgroundColor(Color.TRANSPARENT);
               imgPoke.setImageBitmap(bitmap);
           }

            view.setTag(obj.getId());
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
