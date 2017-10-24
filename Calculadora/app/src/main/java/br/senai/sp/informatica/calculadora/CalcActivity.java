package br.senai.sp.informatica.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import javax.script.*;

public class CalcActivity extends AppCompatActivity {
    private EditText campo_calc;
    private static String numero;
    private static String operação;
    private double valor;
    private Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        campo_calc = (EditText) findViewById(R.id.edt_resu);
    }

    public void limpar(View v){

        campo_calc.setText(null);

    }

    public void numeros(View v) {
         btn = (Button) v;
            numero = btn.getText().toString();

        if(numero.equals(",")){

            if(!campo_calc.getText().toString().contains(",")&&!campo_calc.getText().toString().isEmpty()) {
                campo_calc.setText(campo_calc.getText() +(String.valueOf(numero)));

            }
        }else{

            if(campo_calc.getText().toString().startsWith("0") && !campo_calc.getText().toString().startsWith("0,") ){
                campo_calc.setText(String.valueOf(numero));

            }else{
                campo_calc.setText(campo_calc.getText()+(String.valueOf(numero)));
            }

        }


<<<<<<< HEAD
        if(numero.equals("=")){
            campo_calc.setText(String.valueOf(calcula(campo_calc.getText().toString())));
=======
        if(campo_calc.getText().toString().isEmpty()){

        }else{

>>>>>>> 3944e915295623a6b07dbe147255cc3a7285e930
        }

    }

    public static Double calcula( String expressao ) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName( "JavaScript" );
        Object obj = null;

        try {
            obj = engine.eval( expressao );
        } catch ( ScriptException exc ) {
        }
        Double val = Double.parseDouble(obj.toString());
        return val ;
    }


}
