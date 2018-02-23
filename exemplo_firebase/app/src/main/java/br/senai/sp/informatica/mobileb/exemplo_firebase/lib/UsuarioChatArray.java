package br.senai.sp.informatica.mobileb.exemplo_firebase.lib;

import android.support.annotation.NonNull;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import br.senai.sp.informatica.mobileb.exemplo_firebase.dao.UsuarioDao;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Usuario;

/**
 * Created by WEB on 22/02/2018.
 */

public class UsuarioChatArray extends FirebaseArray<Usuario> {

    private UsuarioDao dao = UsuarioDao.dao;
    private String chavePreviaAdicionada;

    public UsuarioChatArray(){
        super(UsuarioDao.dao.getReference().orderByChild("email"),
                new SnapshotParser<Usuario>() {
                    @NonNull
                    @Override
                    public Usuario parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.getValue(Usuario.class);
                    }
                });
    }

    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        if(previousChildKey == null) chavePreviaAdicionada = null;

        if(!snapshot.getKey().equals(dao.getUserId())) {
            super.onChildAdded(snapshot, chavePreviaAdicionada);
            chavePreviaAdicionada = snapshot.getKey();
        }
    }
}
