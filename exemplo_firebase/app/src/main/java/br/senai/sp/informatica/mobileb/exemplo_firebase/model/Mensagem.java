package br.senai.sp.informatica.mobileb.exemplo_firebase.model;


/**
 * Created by WEB on 21/02/2018.
 */

public class Mensagem {

    private String id;
    private String mensagem;
    private long data;
    private String origem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
