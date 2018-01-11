package br.senai.sp.informatica.mobileb.animes.view.holder;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.senai.sp.informatica.mobileb.animes.R;
import br.senai.sp.informatica.mobileb.animes.model.Anime;
import br.senai.sp.informatica.mobileb.animes.view.adapter.AnimeAdpter;

/**
 * Created by WEB on 10/01/2018.
 */

public class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public final TextView nome;
    public final TextView genero;
    private Long animeId;
    public final AnimeAdpter adpter;

    public AnimeViewHolder(final View view, final AnimeAdpter adpter) {
        super(view);
        this.adpter = adpter;

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        nome = view.findViewById(R.id.tvNome);
        genero = view.findViewById(R.id.tvGenero);
    }

    public void preencher(Anime anime){
        animeId = anime.getId();
        nome.setText(anime.getNome());
        genero.setText(anime.getGenero());

    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), animeId.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.anime_options, popupMenu.getMenu());

        final Activity context = (Activity)v.getContext();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menuEditarAnime:
                        break;


                    case R.id.menuApagarAnime:
                        break;
                }

                return true;
            }
        });

        popupMenu.show();
        return false;
    }
}
