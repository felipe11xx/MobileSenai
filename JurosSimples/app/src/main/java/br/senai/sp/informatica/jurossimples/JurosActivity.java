package br.senai.sp.informatica.jurossimples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class JurosActivity extends AppCompatActivity {

    private EditText edCapIni,edTxJurus,edNumMeses;
    private TextView edCapRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_juros);
        edCapIni = (EditText) findViewById(R.id.edt_cap_ini);
        edTxJurus = (EditText) findViewById(R.id.edt_tx_juros);
        edNumMeses = (EditText) findViewById(R.id.edt_meses);
        edCapRes =(TextView) findViewById(R.id.cap_res);
    }

    public void calcJurusSimples(View v){
        double capIni = Double.parseDouble(edCapIni.getText().toString());
        double txJurus = Double.parseDouble(edTxJurus.getText().toString());
        double numMeses = Double.parseDouble(edNumMeses.getText().toString());
        double capRes = capIni * txJurus /100 * numMeses + capIni;

        //String c = String.valueOf(capRes);
        edCapRes.setText(String.valueOf(capRes));
    }


    public void limpar(View v){

        edCapRes.setText(null);
        edCapIni.setText(null);
        edTxJurus.setText(null);
        edNumMeses.setText(null);
    }



}
