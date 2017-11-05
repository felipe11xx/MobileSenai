package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import br.senai.sp.informatica.listadejogos.R;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private BaseAdapter itemLista;
    private Intent intentEditar ;
    private CheckBox ck;

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

                //Passa parametros de alteração via bundle
                intentEditar.putExtra("JogoID",itemLista.getItemId(position));
                startActivity(intentEditar);

            }
        });

        //cria checkBox para realizar exclusão


    }
    //cria menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

   //Cliques do menu
   public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        switch (id){
            //Abre a tela de Cadastro
            case R.id.addIcon:
                //Remove os extras do Bundle para não causar conflitos
                intentEditar.removeExtra("JogoID");
                startActivity(intentEditar);
                break;

            //fecha Activity
            case R.id.sairIcon:
                this.finishAffinity();
                break;

            //ação de apagar registros
            case R.id.apagaIcon:

                break;
        }
        return true;
   }


}
