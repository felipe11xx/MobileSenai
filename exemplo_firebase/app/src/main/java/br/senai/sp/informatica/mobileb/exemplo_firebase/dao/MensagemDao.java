package br.senai.sp.informatica.mobileb.exemplo_firebase.dao;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.exemplo_firebase.model.Mensagem;

/**
 * Created by WEB on 21/02/2018.
 */

public class MensagemDao {
    public static MensagemDao instance = new MensagemDao();
    private List<Mensagem> lista;
    private long id = 0;

    private MensagemDao(){

        lista = new ArrayList<>();

    }


}
