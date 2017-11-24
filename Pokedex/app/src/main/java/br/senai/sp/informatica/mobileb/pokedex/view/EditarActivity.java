package br.senai.sp.informatica.mobileb.pokedex.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Calendar;
import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;
import br.senai.sp.informatica.mobileb.pokedex.util.DateDialog;

/**
 * Created by WEB on 17/11/2017.
 */

public class EditarActivity extends AppCompatActivity {
    private EditText nomeEdt,dexNumEdt,tipo1Edt,tipo2Edt,dtCapEdt;
    private MenuItem menuItem;
    private Intent i;
    private Pokemon poke;
    private PokemonDao dao = PokemonDao.manager;
    private Long id;
    private DateFormat dtfmt = DateFormat.getDateInstance(DateFormat.LONG);
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_layout);

        poke = new Pokemon();
        nomeEdt = (EditText) findViewById(R.id.edNome);
        dexNumEdt = (EditText) findViewById(R.id.edNumDex);
        tipo1Edt = (EditText) findViewById(R.id.edTp1);
        tipo2Edt = (EditText) findViewById(R.id.edTp2);
        dtCapEdt = (EditText) findViewById(R.id.edDtCap);


        i = new Intent(this, MainActivity.class);
        //Recebe ou não valores da Activity anterior via bundle
        Bundle bundlePoke = getIntent().getExtras();

        if(bundlePoke != null) {
            id = bundlePoke.getLong("PokeID");
            poke.setNome(dao.getPokemon(id).getNome());
            poke.setDexNum(dao.getPokemon(id).getDexNum());
            poke.setTipo1(dao.getPokemon(id).getTipo1());
            poke.setTipo2(dao.getPokemon(id).getTipo2());
            poke.setDtCaptura(dao.getPokemon(id).getDtCaptura());
            nomeEdt.setText(poke.getNome());
            dexNumEdt.setText(String.valueOf(poke.getDexNum()));
            tipo1Edt.setText(poke.getTipo1());
            tipo2Edt.setText(poke.getTipo2());
            dtCapEdt.setText(dtfmt.format(poke.getDtCaptura()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        menuItem = menu.findItem(R.id.saveIcon);
        menuItem.setVisible(true);
        menuItem = menu.findItem(R.id.voltarIcon);
        menuItem.setVisible(true);
        menuItem = menu.findItem(R.id.addIcon);
        menuItem.setVisible(false);
        menuItem = menu.findItem(R.id.exitIcon);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenu = item.getItemId();

        switch (idMenu){
            case R.id.voltarIcon:
                // setResult(Activity.RESULT_CANCELED);
                finish();
                break;

            case R.id.saveIcon:
                cadastrarEditar();
                break;
        }


        return true;
    }

    public void cadastrarEditar() {

        String msg;
        String dexNum = dexNumEdt.getText().toString();
        //A classe JogoDao faz o controle de cadastro ou inclusão pela nulidade do ID
        if (id != null) {
            poke.setId(id);
            msg = "Pokemon alterado com Sucesso !";//getResources().getString(R.string.jogoAlterado);
        } else {
            msg = "Pokemon cadastrado com Sucesso !";//getResources().getString(R.string.jogoCadastrado);
        }

        poke.setNome(nomeEdt.getText().toString());

        if(!dexNum.isEmpty()) {
            poke.setDexNum(Integer.parseInt(dexNum));
        }
        poke.setTipo1(tipo1Edt.getText().toString());
        poke.setTipo2(tipo2Edt.getText().toString());
        poke.setDtCaptura(calendar.getTime());

        if (nomeEdt.getText().toString().isEmpty() || dexNumEdt.getText().toString().isEmpty() ||
                tipo1Edt.getText().toString().isEmpty() // || dtCapEdt.getText().toString().isEmpty()
                ) {

            msg = "Cadastro Invalido !!";//getResources().getString(R.string.semConteudo);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else{
            dao.salvar(poke);

            setResult(Activity.RESULT_OK);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            finish();

            //startActivity(intent);
        }
        //finish();

    }

    public void chamaData(View view){
        DateDialog.makeDialog(calendar, dtCapEdt)
                .show(getSupportFragmentManager(), "Data de captura");
    }
}
