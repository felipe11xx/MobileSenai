package br.senai.sp.informatica.listadejogos.view;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView;


import br.senai.sp.informatica.listadejogos.R;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private BaseAdapter itemLista;
    private Intent intentEditar ;
    private MenuItem lixeira,cancelar,add ;
    private final int EDITA_JOGO = 0;

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

                if(!listView.isSelected()){
                 //Passa parametros de alteração via bundle
                 intentEditar.putExtra("JogoID", itemLista.getItemId(position));
                 startActivityForResult(intentEditar, EDITA_JOGO);
             }
            }
        });

        //Seleciona itens da lista que poderão ser deletados
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                lixeira.setVisible(true);
                cancelar.setVisible(true);
                add.setVisible(false);
                listView.setSelected(true);


                if(!listView.isSelected()){
                    view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
                    listView.setSelected(true);
                }else{
                    view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));
                    listView.setSelected(false);
                }

                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            itemLista.notifyDataSetChanged();
        }
    }


    //cria menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        lixeira = menu.findItem(R.id.apagaIcon);
        cancelar = menu.findItem(R.id.cancelaIcon);
        add = menu.findItem(R.id.addIcon);
        return true;
    }

   //Cliques do menu
   public boolean onOptionsItemSelected (MenuItem item){
        int idMenuItem = item.getItemId();

        switch (idMenuItem){
            //Abre a tela de Cadastro
            case R.id.addIcon:
                //Remove os extras do Bundle para não causar conflitos
                intentEditar.removeExtra("JogoID");
                //finish();
                startActivity(intentEditar);

                break;

            //fecha Activity
            case R.id.sairIcon:
                this.finishAffinity();
                break;

           //ação de apagar registros
            case R.id.apagaIcon:


                break;

            //cancela a ação de apagar e reseta a MainActivity
            case R.id.cancelaIcon:

                recreate();
                break;

        }
        return true;
   }


}
