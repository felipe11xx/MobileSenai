package br.senai.sp.informatica.mobileb.pokedex.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import br.senai.sp.informatica.mobileb.pokedex.R;
import br.senai.sp.informatica.mobileb.pokedex.model.Pokemon;
import br.senai.sp.informatica.mobileb.pokedex.model.PokemonDao;
import br.senai.sp.informatica.mobileb.pokedex.util.DateDialog;
import br.senai.sp.informatica.mobileb.pokedex.util.Utilitarios;

/**
 * Created by WEB on 17/11/2017.
 */

public class EditarActivity extends AppCompatActivity
        implements View.OnClickListener{
    private static final int REQUEST_IMAGE_GALERY = 0;
    private static final int REQUEST_GALLERY_PERMISSION = 1;
    private EditText nomeEdt,dexNumEdt,tipo1Edt,tipo2Edt,dtCapEdt;
    private MenuItem menuItem;
    private Intent i;
    private Pokemon poke;
    private PokemonDao dao = PokemonDao.manager;
    private Long id;
    private DateFormat dtfmt = DateFormat.getDateInstance(DateFormat.LONG);
    private Calendar calendar = Calendar.getInstance();
    private ImageView ivFoto;

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
        ivFoto = (ImageView) findViewById(R.id.imgPoke);
        ivFoto.setOnClickListener(this);


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

        // Demostra como obter um Bitmap de um ImageView
        Bitmap bitmap = Utilitarios.bitmapFromImageView(ivFoto);
        if(bitmap != null) {
            // Demonstra como converter um Bitmap para Base64
            byte[] bytes = Utilitarios.bitmapToBase64(bitmap);
            poke.setFotoPoke(bytes);
        }

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


    @Override
    public void onClick(View v) {
        abriGallery();
    }

    private void abriGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);

        if(i.resolveActivity(getPackageManager()) != null){
            if((ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_GALLERY_PERMISSION);
            }
        }else{
            startActivityForResult(Intent.createChooser(i, "Selecione a Foto"),REQUEST_IMAGE_GALERY);
        }
    }

   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean autorizado = true;

        for(int resultado: grantResults){
           if(resultado == PackageManager.PERMISSION_DENIED) {
               autorizado = false;
               break;
           }
        }

        switch (requestCode){

            case REQUEST_GALLERY_PERMISSION: //Solicitou permissão para acesso à galeria de fotos
                if(autorizado) // Foi autorizado
                    abriGallery();
                else  // Não foi autorizado
                    Toast.makeText(this,"Acesso a galeria de fotos foi negado! ", Toast.LENGTH_LONG).show();
                break;
            default: // Solicitou a abertura da Galeria de Fotos
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_GALERY) {
            if(data != null){

                try {
                    Uri imageURI = data.getData();
                    Bitmap bitmap = Utilitarios.setPic(ivFoto.getWidth(), ivFoto.getHeight(), imageURI, this);
                    ivFoto.setImageBitmap(bitmap);
                    ivFoto.invalidate();

                } catch (IOException e) {
                    Toast.makeText(this, "Falha ao abrir a imagem !", Toast.LENGTH_LONG).show();
                }

            }
        }
    }



}
