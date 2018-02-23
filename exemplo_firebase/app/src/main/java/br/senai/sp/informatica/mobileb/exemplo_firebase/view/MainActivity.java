package br.senai.sp.informatica.mobileb.exemplo_firebase.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.senai.sp.informatica.mobileb.exemplo_firebase.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edEmail,edSenha;
    private TextView tvUID;
    private MenuItem menuLogoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edtEmail);
        edSenha = findViewById(R.id.edtPassword);
        tvUID = findViewById(R.id.edtUID);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_botoes,menu);
        menuLogoff = menu.findItem(R.id.icon_logoff);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.icon_logoff){
            logoutClick();
        }
        return super.onOptionsItemSelected(item);
    }

    public void novaContaClick(View v){
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        //showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Toast.makeText(MainActivity.this, "Falha de autenticação.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        //hideProgresseDialog();
                    }
                });
    }

    public void loginClick(View v){
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        //showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Toast.makeText(MainActivity.this, "Falha de autenticação.", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        //hideProgressDialog();
                    }
                });
    }


    public void logoutClick(){
        mAuth.signOut();
        updateUI(null);

    }

    private void updateUI(FirebaseUser user) {

        if(user != null) {
            tvUID.setText(user.getUid());
        }else {
            tvUID.setText("");
        }
    }
}
