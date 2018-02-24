package br.senai.sp.informatica.mobileb.exemplo_firebase.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import br.senai.sp.informatica.mobileb.exemplo_firebase.R;
import br.senai.sp.informatica.mobileb.exemplo_firebase.dao.UsuarioDao;
import br.senai.sp.informatica.mobileb.exemplo_firebase.lib.DataCallback;
import br.senai.sp.informatica.mobileb.exemplo_firebase.lib.MessageCallBack;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Usuario;

/**
 * Created by WEB on 23/02/2018.
 */

public class LoginActivity extends BaseActivity {
    private UsuarioDao dao = UsuarioDao.dao;

    private EditText edEmail, edSenha;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            carregaMensagens(currentUser);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void carregaMensagens(FirebaseUser user) {
        hideProgressDialog();

        dao.salvar(new Usuario(user.getUid(), user.getEmail()),
                new MessageCallBack(this, "Falha na atualização de dados do usuário"));

        // redireciona para MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void novoLoginClick(View view){
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e("Novo Login","uid: ".concat(task.getResult().getUser().getUid()));
                            carregaMensagens(task.getResult().getUser());
                        }else{
                            Toast.makeText(LoginActivity.this,"Falha na Autenticação. ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });

    }

    public void loginClick(View view){
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.e("login", "uid: " + task.getResult().getUser().getUid());
                            dao.jaLogado(task.getResult().getUser().getUid(), new DataCallback(
                                    new DataCallback.OnDataChange() {
                                        @Override
                                        public void dataChange(DataSnapshot dataSnapshot) {
                                            boolean logado = dataSnapshot.getValue(Boolean.class);
                                            if (!logado) {
                                                carregaMensagens(task.getResult().getUser());
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Conta ja logada em outro Aparelho",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                            ));
                        }else {
                            Toast.makeText(LoginActivity.this, "A Autenticação Falhou.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });

    }

}
