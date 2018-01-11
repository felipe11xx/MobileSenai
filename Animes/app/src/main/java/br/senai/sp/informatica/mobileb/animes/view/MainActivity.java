package br.senai.sp.informatica.mobileb.animes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.senai.sp.informatica.mobileb.animes.R;
import br.senai.sp.informatica.mobileb.animes.dao.AnimeDao;
import br.senai.sp.informatica.mobileb.animes.model.Anime;
import br.senai.sp.informatica.mobileb.animes.view.adapter.AnimeAdpter;

public class MainActivity extends AppCompatActivity {

    private AnimeDao dao = AnimeDao.manager;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Anime> animes = dao.getLista();

        recyclerView = findViewById(R.id.rvAnimes);

        recyclerView.setAdapter(new AnimeAdpter(animes,this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
    }
}
