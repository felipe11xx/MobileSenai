package br.senai.sp.informatica.listadejogos.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.senai.sp.informatica.listadejogos.R;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private BaseAdapter itemLista;
    private Intent intentEditar;
    private MenuItem lixeira, cancelar, add;
    private final int EDITA_JOGO = 0;
    //array para controle de exclusão
    private List<Long> removeId= new ArrayList<>();
    private JogoDao dao = JogoDao.manager;

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

                //Se a lista de exclusão estiver vazia vai para a tela de alteração
                if (removeId.isEmpty()) {
                    //Passa parametros de alteração via bundle
                    intentEditar.putExtra("JogoID", itemLista.getItemId(position));

                    startActivityForResult(intentEditar, EDITA_JOGO);

                }else{

                    //verifica se o id do item existe na lista de exclusão
                    if(removeId.contains(itemLista.getItemId(position))){
                        //se ja existe então retira da lista e volta a cor do item para a original
                        view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));
                        removeId.remove(itemLista.getItemId(position));
                    }else{
                        //senão existir add ele na lista
                        view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
                        removeId.add(itemLista.getItemId(position));
                    }

                    //ao deselecionar todos os itens da lista de exclução recria a view com o menu inicial
                    if(removeId.isEmpty()){
                        recreate();
                    }

                }
            }
        });

        //clique longo abre as opções de exclusão e muda o resultado do metodo onItemClick
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //mudança nos botões do menu
                add.setVisible(false);
                lixeira.setVisible(true);
                cancelar.setVisible(true);

                //verifica se o id do item existe na lista de exclusão
                if(removeId.contains(itemLista.getItemId(position))){
                    //se ja existe então retira da lista e volta a cor do item para a original
                    view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));
                    removeId.remove(itemLista.getItemId(position));
                }else{
                    //senão existir add ele na lista
                    view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
                    removeId.add(itemLista.getItemId(position));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            itemLista.notifyDataSetChanged();
        }
    }

    //cria menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        lixeira = menu.findItem(R.id.apagaIcon);
        cancelar = menu.findItem(R.id.cancelaIcon);
        add = menu.findItem(R.id.addIcon);
        return true;
    }

    //Cliques do menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenuItem = item.getItemId();

        switch (idMenuItem) {
            //Abre a tela de Cadastro
            case R.id.addIcon:
                //Remove os extras do Bundle para não causar conflitos
                intentEditar.removeExtra("JogoID");

                //finish();
                startActivityForResult(intentEditar, EDITA_JOGO);
                break;

            //fecha Activity
            case R.id.sairIcon:
                this.finishAffinity();
                break;

            //ação de apagar registros
            case R.id.apagaIcon:
                //Cria o Builder do dialogo de exclusão
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.msgExclusao)
                        .setTitle(R.string.excluir);

                // Cria botões ok e cancelar
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //confere os ids na lista e os apaga ao terminar recria a ActivityMain
                        for (Long idJogo:removeId) {
                            dao.apagar(idJogo);

                        }
                        Toast.makeText(getBaseContext(), R.string.msgExclusaoSuccess, Toast.LENGTH_LONG).show();
                        recreate();
                    }
                });
                builder.setNegativeButton(R.string.cancela, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // se cancelar volta pra tela de exclusão
                    }
                });
                //Cria o  dialogo de exclusão
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            //cancela a ação de apagar e reseta a MainActivity
            case R.id.cancelaIcon:

                recreate();
                break;

        }
        return true;
    }

}