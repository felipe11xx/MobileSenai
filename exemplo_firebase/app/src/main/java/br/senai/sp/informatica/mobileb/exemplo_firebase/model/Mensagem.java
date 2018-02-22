package br.senai.sp.informatica.mobileb.exemplo_firebase.model;


/**
 * Created by WEB on 21/02/2018.
 */

public class Mensagem {

    private long id;
    private String mensagem;

    public Mensagem(long id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {

    }
}
