import java.io.Serializable;
import java.util.ArrayList;
public class Url implements Serializable {
    private static final long serialVersionUID = 42L;
    private String link;
    private String titulo;
    private String introducao;
    private ArrayList<String> indexado = new ArrayList<String>();

    public Url(String link, String titulo, String introducao, ArrayList<String> indexado){
        this.link=link;
        this.titulo=titulo;
        this.introducao=introducao;
        this.indexado=indexado;
    }

    public String get_titulo(){return this.titulo;}

    public String get_link(){return this.link;}

    public String get_introducao(){return this.introducao;}

    public ArrayList<String> get_indexado(){return this.indexado;}
}