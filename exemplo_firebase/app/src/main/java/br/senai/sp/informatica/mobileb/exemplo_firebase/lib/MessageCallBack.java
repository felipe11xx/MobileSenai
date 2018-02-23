package br.senai.sp.informatica.mobileb.exemplo_firebase.lib;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by WEB on 22/02/2018.
 */

public class MessageCallBack implements DatabaseReference.CompletionListener {
    private String msg;
    private Context ctx;

    public MessageCallBack(Context ctx, String msg){
        this.ctx = ctx;
        this.msg = msg;
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if(databaseError != null){
            Toast.makeText(ctx,msg, Toast.LENGTH_LONG).show();
        }
    }
}
