package br.senai.sp.informatica.mobileb.animes.view.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.informatica.mobileb.animes.R;
import br.senai.sp.informatica.mobileb.animes.dao.AnimeDao;
import br.senai.sp.informatica.mobileb.animes.model.Anime;

/**
 * Created by WEB on 11/01/2018.
 */

public class AnimeCardAdpter
        extends RecyclerView.Adapter<AnimeCardAdpter.AnimeCardViewolder>
        implements AdpterInterface{

    private AnimeDao dao = AnimeDao.manager;
    private SparseLongArray mapa;
    private Activity activity;
    private AdapterView.OnItemClickListener listener;
    private boolean editar = false;

    public AnimeCardAdpter(Activity activity, AdapterView.OnItemClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        criaMapa();
    }

    @Override
    public void setEditar(boolean value) {
        editar = value;
        notificaAtualizacao();
    }

    @Override
    public void notificaAtualizacao() {
        criaMapa();
        notifyDataSetChanged();
    }


    public void criaMapa() {

        String ordemPreference = "ORDEM_DA_LISTA";
        String ordemDefault = "Nome";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        String ordem = preferences.getString(ordemPreference, ordemDefault);

        mapa = new SparseLongArray();

        List<Anime> lista = dao.getLista();

        //associa o id com o numero da linha
        for (int linha = 0; linha < lista.size(); linha++ ){
            Anime anime = lista.get(linha);
            mapa.put(linha, anime.getId());
        }

    }

    @Override
    public int getItemCount() {
        return mapa.size();
    }
    @Override
    public AnimeCardViewolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater svc = LayoutInflater.from(parent.getContext());
        View layout = svc.inflate(R.layout.anime_item_card, parent, false);
        return new AnimeCardViewolder(layout);
    }

    @Override
    public void onBindViewHolder(AnimeCardViewolder holder, int position) {
        Anime obj = dao.getLista().get(position);
        holder.setView(obj);
    }



    public class AnimeCardViewolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nome;
        private TextView genero;
        private View view;


        public AnimeCardViewolder(View itemView) {
            super(itemView);
            this.view = itemView;
            nome = itemView.findViewById(R.id.tvNome);
            genero= itemView.findViewById(R.id.tvGenero);
        }

        public void setView (final Anime obj){
            nome.setText(obj.getNome());
            genero.setText(obj.getGenero());


            Log.d("AnimeCardViewHolder", "Anime" + nome);
            view.setTag(obj.getId());
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Long id = (Long)view.getTag();


        }
    }
}
