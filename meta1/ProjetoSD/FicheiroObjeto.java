import java.io.*;
import java.io.Serializable;

public class FicheiroObjeto implements Serializable{
    private ObjectInputStream iS;
    private ObjectOutputStream oS;
    private static final long serialVersionUID = 42L;

    public boolean abreLeitura(String nomeDoFicheiro) {
        try {
            iS = new ObjectInputStream(new FileInputStream (nomeDoFicheiro));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void abreEscrita(String nomeDoFicheiro) throws IOException {
        oS = new ObjectOutputStream(new FileOutputStream (nomeDoFicheiro));
    }

    public Object leObjeto() throws IOException, ClassNotFoundException {
        return iS.readObject();
    }

    public void escreveObjeto(Object o) throws IOException {
        oS.writeObject(o);
    }

    public void fechaLeitura() throws IOException {
        iS.close();
    }
    public void fechaEscrita() throws IOException {
        oS.close();
    }
}
