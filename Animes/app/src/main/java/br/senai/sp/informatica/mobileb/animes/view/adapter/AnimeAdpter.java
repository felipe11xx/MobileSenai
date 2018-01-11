package br.senai.sp.informatica.mobileb.animes.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.senai.sp.informatica.mobileb.animes.R;
import br.senai.sp.informatica.mobileb.animes.model.Anime;
import br.senai.sp.informatica.mobileb.animes.view.holder.AnimeViewHolder;

/**
 * Created by WEB on 10/01/2018.
 */

public class AnimeAdpter extends RecyclerView.Adapter {

    private List<Anime> animes;
    private Context context;

    public AnimeAdpter(List<Anime> animes, Context context) {
        this.animes = animes;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.anime_item_lista, parent, false);

        AnimeViewHolder holder = new AnimeViewHolder(view, this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AnimeViewHolder viewHolder = (AnimeViewHolder) holder;

        Anime anime = animes.get(position);

        ((AnimeViewHolder) holder).preencher(anime);
    }

    @Override
    public int getItemCount() {

        return animes.size();
    }
}
