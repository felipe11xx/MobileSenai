package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.listadejogos.R;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private BaseAdapter itemLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemLista = new JogoAdapter();

        listView = (ListView) findViewById(R.id.lvLista);
        listView.setAdapter(itemLista);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

   public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.addIcon:
                Intent intent = new Intent(this, EditaActivity.class);
                startActivity(intent);
                break;
            case R.id.sairIcon:
                finish();
                break;

        }
        return true;

   }
}
