package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import br.senai.sp.informatica.listadejogos.R;
import br.senai.sp.informatica.listadejogos.model.Jogo;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

/**
 * Created by Felipe on 03/11/2017.
 */

public class EditaActivity extends AppCompatActivity {

    private EditText nomeJogoView,generoView;
    private JogoDao dao = JogoDao.manager;
    private Jogo jogo;
    private Intent intent = null ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatelayout);
        nomeJogoView = (EditText) findViewById(R.id.edtNome) ;
        generoView = (EditText) findViewById(R.id.edtGenero) ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id == R.id.voltar){
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return true;
    }

    public void cadastrar(View view){
        jogo = new Jogo();
        jogo.setNome(nomeJogoView.getText().toString());
        jogo.setGenero(generoView.getText().toString());
        dao.salvar(jogo);

        Toast.makeText(this,"Jogo cadastrado abestado",Toast.LENGTH_LONG).show();
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
