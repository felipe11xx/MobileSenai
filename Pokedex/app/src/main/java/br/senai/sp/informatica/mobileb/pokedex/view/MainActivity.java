package br.senai.sp.informatica.mobileb.pokedex.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private BaseAdapter itemLista;
    private Intent i;
    private final int EDITAR_POKE = 0;
    private MenuItem lixoItem,voltaItem,addItem;
    private List<Long> removeId;
    private PokemonDao dao = PokemonDao.manager;
    private Pokemon poke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemLista = new PokemonAdapter();
        i =  new Intent(this, EditarActivity.class);
        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);
        removeId =  new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                poke = dao.getPokemon(id);
                //Se a lista de exclusão estiver vazia vai para a tela de alteração
                if (removeId.isEmpty()) {
                    //Passa parametros de alteração via bundle
                    i.putExtra("PokeID",itemLista.getItemId(position));
                    startActivityForResult(i,EDITAR_POKE);

                }else{

                    //verifica se o id do item existe na lista de exclusão
                    if(removeId.contains(itemLista.getItemId(position))){
                    //  if(poke.isApagar()) {
                          //se ja existe então retira da lista e volta a cor do item para a original
                          view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));

                          removeId.remove(itemLista.getItemId(position));
                          poke.setApagar(false);
                    //  }
                    }else{
                       //if(!poke.isApagar()) {
                           //senão existir add ele na lista
                           view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
                           removeId.add(itemLista.getItemId(position));
                           poke.setApagar(true);
                      // }
                    }

                    //ao deselecionar todos os itens da lista de exclução recria a view com o menu inicial
                    if(removeId.isEmpty()){
                        recreate();
                    }

                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                lixoItem.setVisible(true);
                voltaItem.setVisible(true);
                addItem.setVisible(false);

                poke = dao.getPokemon(id);

                //verifica se o id do item existe na lista de exclusão
                if(removeId.contains(itemLista.getItem(position))){
                   // if (poke.isApagar()) {
                        //se ja existe então retira da lista e volta a cor do item para a original
                        view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));
                        removeId.remove(itemLista.getItemId(position));
                        poke.setApagar(false);
                   // }
                }else{
                    //senão existir add ele na lista
                   // if (!poke.isApagar()) {
                        view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
                        removeId.add(itemLista.getItemId(position));
                        poke.setApagar(true);
                   // }
                }

                //ao deselecionar todos os itens da lista de exclução recria a view com o menu inicial
                if(removeId.isEmpty()){
                    recreate();
                }
                return true;

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
        lixoItem = menu.findItem(R.id.apagaIcon);
        voltaItem = menu.findItem(R.id.desfazIcon);
        addItem = menu.findItem(R.id.addIcon);

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
            case R.id.apagaIcon:


                //Cria o Builder do dialogo de exclusão
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja mesmo Excluir ?")
                        .setTitle("Apagar");

                // Cria botões ok e cancelar
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //confere os ids na lista e os apaga ao terminar recria a ActivityMain
                        for (Long idPoke:removeId) {
                            dao.apagar(idPoke);

                        }
                        Toast.makeText(getBaseContext(), "Pokemon Apagado !", Toast.LENGTH_LONG).show();
                        recreate();
                       // removeId.removeAll();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // se cancelar volta pra tela de exclusão
                    }
                });
                //Cria o  dialogo de exclusão
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.desfazIcon:
                for (Long idPoke:removeId) {
                    poke = dao.getPokemon(idPoke);
                    if(poke.getId() == idPoke) {
                        poke.setApagar(false);
                    }
                }
                recreate();
                break;
        }

        return true;
    }
}
