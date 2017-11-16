package br.senai.sp.informatica.listadejogos.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.listadejogos.R;
import br.senai.sp.informatica.listadejogos.model.Jogo;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

/**
 * Created by WEB on 31/10/2017.
 */

public class JogoAdapter extends BaseAdapter {

    private JogoDao dao = JogoDao.manager;
    private Map<Integer,Long> mapa;
    private boolean statusApaga;

    public JogoAdapter() {
        criarMapa();
    }

    @Override
    public void notifyDataSetChanged() {

       criarMapa() ;
       super.notifyDataSetChanged();
    }

    private void criarMapa(){
        //Cira mapa com associação de linha e ID
        mapa = new HashMap<>();
        //Recebe as lista de obj DAO
        List<Jogo> lista = dao.getLista();

        //associa o id com o numero da linha
        for (int linha = 0; linha < lista.size(); linha++ ){
            Jogo jogo = lista.get(linha);
            mapa.put(linha, jogo.getId());
        }
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getJogo((long)id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {

        ConstraintLayout layout;
        if(view == null) {

            Context ctx = viewGroup.getContext();
            // localizar o serviço de construção do layout
            LayoutInflater inflater =
                    (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // informar o layout xml a ser carregado
            layout = new ConstraintLayout(ctx);

            inflater.inflate(R.layout.detalhe_layout,layout);
            // iniciliazar o ConstraintLayout a partir da carga
        } else {
            layout = (ConstraintLayout)view;
        }

        // o registro da posição solicitada e encontrar o objeto
        // atribuir o objeto ao layout
        TextView tvJogo = layout.findViewById(R.id.tvJogo);
        TextView tvGenero = layout.findViewById(R.id.tvGenero);
        Jogo jogo = dao.getJogo(mapa.get(linha));
        tvJogo.setText(jogo.getNome());
        tvGenero.setText(jogo.getGenero());
        return layout;
    }


}
