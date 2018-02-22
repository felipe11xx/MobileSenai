package br.senai.sp.informatica.mobileb.exemplo_firebase.adpter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

import br.senai.sp.informatica.mobileb.exemplo_firebase.R;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Mensagem;
import br.senai.sp.informatica.mobileb.exemplo_firebase.view.MainActivity;

/**
 * Created by WEB on 21/02/2018.
 */

public class MensagemAdapter extends FirebaseListAdapter<Mensagem> {


    public MensagemAdapter(@NonNull FirebaseListOptions<Mensagem> options) {
        super(options);
    }

    @Override
    protected void populateView(View v, Mensagem model, int position) {
        TextView tvMsg = v.findViewById(R.id.edtMsg);
        ImageButton imgDel = v.findViewById(R.id.imgCancel);

        tvMsg.setText(model.getMensagem());
        imgDel.setTag(model.getId());
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder();
                alerta.setMessage("Confirma exclusão da mensagems ?");
                alerta.setNegativeButton("Não",null);
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}
