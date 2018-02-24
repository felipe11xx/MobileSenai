package br.senai.sp.informatica.mobileb.exemplo_firebase.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.senai.sp.informatica.mobileb.exemplo_firebase.R;
import br.senai.sp.informatica.mobileb.exemplo_firebase.dao.MensagemDao;
import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Mensagem;

/**
 * Created by WEB on 23/02/2018.
 */

public class ChatActivity extends BaseActivity {

    private MensagemDao dao;

    private TextView tvMsg;
    private ListView listView;
    private MensagemAdater adapter;
    private String destinatarioId;


    public class MensagemAdater extends FirebaseListAdapter<Mensagem>{

        @SuppressLint("SimpleDateFormat")
        private SimpleDateFormat sdf = new SimpleDateFormat("'dia' dd 'as' HH:mm");

        public MensagemAdater(@NonNull FirebaseListOptions<Mensagem> options) {
            super(options);
            dao.verificaMensagens();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Mensagem model = getItem(position);

            if(destinatarioId.equals(model.getOrigem())){
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_mensagem2, viewGroup, false);
            }else{
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(mLayout, viewGroup, false);
            }

            return super.getView(position, view, viewGroup);
        }

        @Override
        protected void populateView(View v, Mensagem model, int position) {
            hideProgressDialog();

            TextView tvMSG = v.findViewById(R.id.tvUsuario);
            TextView tvData = v.findViewById(R.id.tvData);

            tvMSG.setText(model.getMensagem());
            tvData.setText( sdf.format(new Date(model.getData())) );
        }
    }

}

