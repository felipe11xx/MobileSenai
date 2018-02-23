package br.senai.sp.informatica.mobileb.exemplo_firebase.lib;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by WEB on 22/02/2018.
 */

public class DataCallback implements ValueEventListener{

    private OnDataChange onDataChange;

    public interface OnDataChange {
        public void dataChange(DataSnapshot dataSnapshot);
    }

    public DataCallback() {
    }

    public DataCallback(OnDataChange onDataChange) {
        this.onDataChange = onDataChange;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(this.onDataChange != null)
            this.onDataChange.dataChange(dataSnapshot);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
