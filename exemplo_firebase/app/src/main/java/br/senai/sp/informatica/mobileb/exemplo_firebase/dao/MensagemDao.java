package br.senai.sp.informatica.mobileb.exemplo_firebase.dao;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.mobileb.exemplo_firebase.lib.DataCallback;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Mensagem;

/**
 * Created by WEB on 21/02/2018.
 */

public class MensagemDao {
    private DatabaseReference base;
    private DatabaseReference reference;
    private FirebaseUser user;

    private String destinatarioId;

    public MensagemDao(String destinatarioId){
        this.destinatarioId = destinatarioId;
        user = FirebaseAuth.getInstance().getCurrentUser();
        base = FirebaseDatabase.getInstance().getReference();
        reference = base.child("mensagens").child(user.getUid()).child(destinatarioId);
    }

    public DatabaseReference getReference(){
        return reference;
    }

    private String makeReference1(String id){
        return "/mensagens/"+user.getUid() +"/"+ destinatarioId + "/" + id;
    }

    private String makeReference2(String id){
        return "/mensagens/"+ destinatarioId +"/"+ user.getUid() + "/" + id;
    }


    public void salvar(Mensagem obj, DatabaseReference.CompletionListener callback){

        if(obj.getId() ==  null){
            obj.setId(reference.push().getKey());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id",obj.getId());
        map.put("msg",obj.getMensagem());
        map.put("data", obj.getData());
        map.put("origem", obj.getMensagem());

        Map<String, Object> updates = new HashMap<>();
        updates.put(makeReference1(obj.getId()),map);
        updates.put(makeReference2(obj.getId()),map);

        base.updateChildren(updates, callback);
    }

    public void remover(String id, DatabaseReference.CompletionListener callback){
        Map<String, Object> updates = new HashMap<>();
        updates.put(makeReference1(id),null);
        updates.put(makeReference2(id),null);

        base.updateChildren(updates,callback);
    }

    public void verificaMensagens(){
        getReference().addListenerForSingleValueEvent(new DataCallback());
    }

}
