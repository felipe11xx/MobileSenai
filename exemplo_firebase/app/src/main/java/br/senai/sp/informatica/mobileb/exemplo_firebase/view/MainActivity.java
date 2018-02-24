package br.senai.sp.informatica.mobileb.exemplo_firebase.view;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

import br.senai.sp.informatica.mobileb.exemplo_firebase.R;
import br.senai.sp.informatica.mobileb.exemplo_firebase.dao.UsuarioDao;
import br.senai.sp.informatica.mobileb.exemplo_firebase.lib.UsuarioChatArray;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Usuario;

public class MainActivity extends BaseActivity
            implements AdapterView.OnItemClickListener{
    private UsuarioDao dao = UsuarioDao.dao;

    private ListView listView;
    private UsuarioAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(adapter == null){
            FirebaseListOptions<Usuario> options = new FirebaseListOptions.Builder<Usuario>()
                    .setSnapshotArray(new UsuarioChatArray())
                    .setLayout(R.layout.layout_usuario)
                    .setLifecycleOwner(this)
                    .build();

            adapter = new UsuarioAdapter(options);

            listView.setAdapter(adapter);
        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("destinatarioId", adapter.getItem(pos).getId());

        startActivity(intent);
    }

    private class UsuarioAdapter extends FirebaseListAdapter<Usuario> {

        public UsuarioAdapter(@NonNull FirebaseListOptions<Usuario> options) {
            super(options);
            dao.verificaMensagens();
        }

        @Override
        protected void populateView(View v, Usuario model, int position) {
            hideProgressDialog();
            Log.e("UsuarioAdapter", "usuario: " + model.getEmail() + " logado: " + model.isLogado());
            Log.e("UsuarioAdapter", "daoUID: " + dao.getUserId() + " modelUID: " + model.getId());

            if(model.getId() != dao.getUserId()){
                TextView tvUsuario = v.findViewById(R.id.tvUsuario);
                ImageView imgLogado = v.findViewById(R.id.imgLogado);

                tvUsuario.setText(model.getEmail());
                if(model.isLogado()){
                    imgLogado.setImageDrawable(getResources().getDrawable(R.drawable.verde));
                }else{
                    imgLogado.setImageDrawable(getResources().getDrawable(R.drawable.vermelho));
                }
            }

        }
    }
}
