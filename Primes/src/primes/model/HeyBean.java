package primes.model;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

public class HeyBean {
    Registry reg;
    static Rmi_S_I server;
    private String username;
    private String password;
    String ipaddress = "192.168.1.94";

    private String result;


    public HeyBean()  {
        try {
            //String ipaddress = "192.168.43.53";
            //LocateRegistry.getRegistry(ipaddress, 5000);
            //reg = LocateRegistry.getRegistry(ipaddress, 5000);
            reg = LocateRegistry.getRegistry(ipaddress, 5000);
            server = (Rmi_S_I) reg.lookup("Foo");
            server.print_on_server("ligousadas");

        } catch (NotBoundException  | RemoteException e) {
            e.printStackTrace();
        }
    }

    public HeyBean getHeyBean(){
            return this;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getLogin(String username, String password) throws RemoteException, ClassNotFoundException {
        return server.login(username,password);
    }
    public static String getRegisto(String username, String password,String email) throws RemoteException, ClassNotFoundException {
        return server.signup(username,password,email);
    }
    public static String getSearchOff(String pesq) throws RemoteException, ClassNotFoundException {
        return server.pesquisa(pesq,"none");
    }
    public static String getHist(String username) throws RemoteException, ClassNotFoundException {
        return server.showHistorico(username);
    }
    public static String getAdmin(String username) throws RemoteException, ClassNotFoundException {
        return server.checkAdmin(username);
    }
    public static String setAdmin(String username) throws RemoteException, ClassNotFoundException {
        return server.addAdmin(username);
    }
    public static String indexaUrl(String link) throws RemoteException, ClassNotFoundException {
        return server.indexa(link);
    }
    public String getSearchOn(String pesquisa, String user) throws RemoteException, ClassNotFoundException {
        return server.pesquisa(pesquisa,user);
    }
}