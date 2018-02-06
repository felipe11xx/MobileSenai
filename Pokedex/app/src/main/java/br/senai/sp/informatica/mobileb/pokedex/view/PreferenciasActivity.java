package br.senai.sp.informatica.mobileb.pokedex.view;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import br.senai.sp.informatica.mobileb.pokedex.R;

/**
 * Created by WEB on 12/01/2018.
 */

public class PreferenciasActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        ActionBar bar = getActionBar();
        if(bar != null){
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            setResult(RESULT_OK);

        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        super.onBackPressed();
    }
}
