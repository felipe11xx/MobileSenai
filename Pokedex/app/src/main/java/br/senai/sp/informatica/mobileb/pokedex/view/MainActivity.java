package br.senai.sp.informatica.mobileb.pokedex.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.mobileb.pokedex.R;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private BaseAdapter itemLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemLista = new PokemonAdapter();

        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);

    }

    @Override
    protected void onActivityResult(int requestCode, int resutlCode, Intent data){
        if(resutlCode == RESULT_OK){
            itemLista.notifyDataSetChanged();
        }
    }

}
