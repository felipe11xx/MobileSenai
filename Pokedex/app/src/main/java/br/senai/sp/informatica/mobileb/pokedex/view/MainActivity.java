package br.senai.sp.informatica.mobileb.pokedex.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.mobileb.pokedex.R;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private BaseAdapter itemLista;
    private Intent i;
    private final int EDITAR_POKE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemLista = new PokemonAdapter();
        i =  new Intent(this, EditarActivity.class);
        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MainActivity","pos: "+ position);
                i.putExtra("PokeID",itemLista.getItemId(position));
                startActivityForResult(i,EDITAR_POKE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
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
                i.removeExtra("PokeID");
                startActivityForResult(i,EDITAR_POKE);

                break;
            case R.id.exitIcon:

                this.finishAffinity();
                break;
        }

        return true;
    }
}
