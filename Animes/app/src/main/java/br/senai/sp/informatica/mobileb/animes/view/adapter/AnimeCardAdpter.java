package br.senai.sp.informatica.mobileb.animes.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WEB on 11/01/2018.
 */

public class AnimeCardAdpter
        extends RecyclerView.Adapter<AnimeCardAdpter.AnimeCardViewolder> {


    @Override
    public AnimeCardViewolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AnimeCardViewolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AnimeCardViewolder extends RecyclerView.ViewHolder {
        public AnimeCardViewolder(View itemView) {
            super(itemView);
        }
    }
}
