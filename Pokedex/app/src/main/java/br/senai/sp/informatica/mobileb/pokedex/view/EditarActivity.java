package br.senai.sp.informatica.mobileb.pokedex.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.senai.sp.informatica.mobileb.pokedex.R;

/**
 * Created by WEB on 17/11/2017.
 */

public class EditarActivity extends AppCompatActivity {
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_layout);

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
            case R.id.exitIcon:
                // setResult(Activity.RESULT_CANCELED);
                finish();
                break;

            case R.id.saveIcon:

                break;
        }


        return true;
    }
}
