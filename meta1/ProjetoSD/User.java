import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 42L;
    public boolean online;
    private boolean administrador;
    private String username;
    private String password;
    private String email;
    private ArrayList<String> notificacoes;
    private ArrayList<String> historico;

    public User() {
        this.online = false;
        this.notificacoes = new ArrayList<>();
        this.historico = new ArrayList<>();
    }

    public User(boolean administrador,String username, String password, String email) {
        this.online = false;
        this.administrador = administrador;
        this.username = username;
        this.password = password;
        this.email = email;
        this.notificacoes = new ArrayList<>();
        this.historico = new ArrayList<>();
    }



    public void addHistorico(String pesquisa){ this.notificacoes.add(pesquisa); }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isEditor() {
        return administrador;
    }

    public void addNotificacao(String notificacao) {
        notificacoes.add(notificacao);
    }

    public void addPesquisa(String pesquisa){historico.add(pesquisa);}

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public ArrayList<String> getNotificacoes() {
        return notificacoes;
    }

    public void clearNotificacoes() {
        notificacoes.clear();
    }

    public ArrayList<String> getHistorico() {
        return historico;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin(){return administrador;}

    public void setAddress(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}