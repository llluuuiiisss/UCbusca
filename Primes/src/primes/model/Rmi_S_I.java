package primes.model;

import java.rmi.*;
import java.lang.String;
public interface Rmi_S_I extends Remote {
	public void print_on_server(String s) throws RemoteException;
    public boolean testePrimary() throws  RemoteException;
    public String login(String username, String password) throws RemoteException,ClassNotFoundException;
    public String pesquisa(String pesquisa, String username)throws RemoteException,ClassNotFoundException;
    public String addAdmin(String username)throws RemoteException,ClassNotFoundException;
    public String signup(String username, String password, String email)throws RemoteException,ClassNotFoundException;
    public String logout(String username)throws RemoteException,ClassNotFoundException;
    public String pLigacoes(String link, String username)throws RemoteException,ClassNotFoundException;
    public String showHistorico(String username)throws RemoteException,ClassNotFoundException;
    public String indexa(String link) throws RemoteException,ClassNotFoundException;
    public String checkAdmin(String username) throws RemoteException,ClassNotFoundException;
   public  String begin(String username) throws RemoteException,ClassNotFoundException;
}