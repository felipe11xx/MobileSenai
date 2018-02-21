package br.senai.sp.informatica.mobileb.pokedex.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDaoOld;
import br.senai.sp.informatica.mobileb.pokedex.util.Utilitarios;
import br.senai.sp.informatica.mobileb.pokedex.view.adapter.PokemonAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private BaseAdapter itemLista;
    private Intent i;
    private final int EDITAR_POKE = 0;
    private static int NEW_ACTION = 1;
    private static int PREF_ACTION = 2;
    private MenuItem lixoItem,voltaItem,addItem;
    private List<Long> removeId;
    private PokemonDao dao = PokemonDao.instance;
    private Pokemon poke;
    private DrawerLayout drawer;
    // Atributos utilizados no Navigation Drawer
    private TextView tvNome;
    private TextView tvEmail;
    private ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_menu);

        itemLista = new PokemonAdapter(this);
        i =  new Intent(this, EditarActivity.class);
        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);
        removeId =  new ArrayList<>();

//        // Configura o Float Action Button
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btAdd);
//        fab.setOnClickListener(this);

        // Configuração do ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuração no Menu de Navegação
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Registro dos menu para tratar as ações do menu de navegação
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Inicializa oa atributor do Navigation Drawer
        View cabecalho = navigationView.getHeaderView(0);
        tvNome = (TextView)cabecalho.findViewById(R.id.txtNomePessoa);
        tvEmail = (TextView)cabecalho.findViewById(R.id.txtEmail);
        ivFoto = (ImageView)cabecalho.findViewById(R.id.imgPessoa);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                poke = dao.localizar(id);
                //Se a lista de exclusão estiver vazia vai para a tela de alteração
                if (removeId.isEmpty()) {
                    //Passa parametros de alteração via bundle
                    i.putExtra("PokeID",itemLista.getItemId(position));
                    startActivityForResult(i,EDITAR_POKE);

                }else{

                    selecionaApaga(position,view);
                    
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                lixoItem.setVisible(true);
                voltaItem.setVisible(true);
                addItem.setVisible(false);
                poke = dao.localizar(id);

                selecionaApaga(position,view);

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        tvNome.setText(preferences.getString(UserActivity.NOME_USUARIO, ""));
        tvEmail.setText(preferences.getString(UserActivity.EMAIL_USUARIO, ""));

        String fotoString = preferences.getString(UserActivity.FOTO_USUARIO, null);
        if(fotoString != null){
            Bitmap bitmap = Utilitarios.bitmapFromBase64(fotoString.getBytes());
            ivFoto.setImageBitmap(Utilitarios.toCircularBitmap(bitmap));
        }
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
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else if(lixoItem.isVisible()){
            for (Long idPoke:removeId) {
                poke = dao.localizar(idPoke);
                if(poke.getId() == idPoke) {
                    poke.setApagar(false);
                    salvar();
                }
            }


        }
        recreate();
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
                        //remove todos os marcados com isapaga
                        dao.removerMarcados();
                        Toast.makeText(getBaseContext(), "Pokemon Apagado/s !", Toast.LENGTH_LONG).show();
                        recreate();

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
                    poke = dao.localizar(idPoke);
                    if(poke.getId() == idPoke) {
                        poke.setApagar(false);
                        salvar();
                    }
                }
                recreate();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_pref:

                Intent tela = new Intent(getBaseContext(), PreferenciasActivity.class);
                startActivityForResult(tela, PREF_ACTION);
                break;

            case R.id.nav_perfil:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void salvar(){
        try {
            dao.salvar(poke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selecionaApaga(int position, View view){
        //verifica se o id do item existe na lista de exclusão
        if(removeId.contains(itemLista.getItemId(position))){
            //  if(poke.isApagar()) {
            //se ja existe então retira da lista e volta a cor do item para a original
            view.setBackgroundColor(getResources().getColor(R.color.fundoDoListView));

            removeId.remove(itemLista.getItemId(position));
            poke.setApagar(false);
            salvar();
            //  }
        }else{
            //if(!poke.isApagar()) {
            //senão existir add ele na lista
            view.setBackgroundColor(getResources().getColor(R.color.ItemSelecionado));
            removeId.add(itemLista.getItemId(position));
            poke.setApagar(true);
            salvar();
            // }
        }

        //ao deselecionar todos os itens da lista de exclução recria a view com o menu inicial
        if(removeId.isEmpty()){
            recreate();
        }
    }
}
