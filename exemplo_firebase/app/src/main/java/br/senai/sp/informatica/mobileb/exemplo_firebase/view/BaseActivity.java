package br.senai.sp.informatica.mobileb.exemplo_firebase.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by WEB on 23/02/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    protected FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        mAuth = FirebaseAuth.getInstance();
    }

    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }


    protected  void hideProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
