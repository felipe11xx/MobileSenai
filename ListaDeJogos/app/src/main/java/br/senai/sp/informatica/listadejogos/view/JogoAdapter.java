package br.senai.sp.informatica.listadejogos.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import br.senai.sp.informatica.listadejogos.model.JogoDao;

/**
 * Created by WEB on 31/10/2017.
 */

public class JogoAdapter extends BaseAdapter {

    private JogoDao dao = JogoDao.manager;

    @Override
    public int getCount() {
        return dao.getLista().size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getJogo((long)id);
    }

    @Override
    public long getItemId(int pos) {
        return dao.getLista().get(pos).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
