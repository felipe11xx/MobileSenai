package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import br.senai.sp.informatica.listadejogos.R;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private BaseAdapter itemLista;
    private Intent intentEditar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentEditar = new Intent(this, EditaActivity.class);
        itemLista = new JogoAdapter();

        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);

        //Chama tela de editar ao clicar em um item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(intentEditar);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

   public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.addIcon:

                startActivity(intentEditar);
                break;

            case R.id.sairIcon:

                this.finishAffinity();
                break;

        }
        return true;
   }


}
