package br.senai.sp.informatica.mobileb.pokedex.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idIconMenu = item.getItemId();

        switch (idIconMenu){
            case R.id.addIcon:

                break;
            case R.id.exitIcon:

                this.finishAffinity();
                break;
        }

        return true;
    }
}
