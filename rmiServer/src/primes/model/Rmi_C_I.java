package primes.model;

import java.rmi.*;

public interface Rmi_C_I extends Remote{
	public void print_on_client(String s, String user) throws java.rmi.RemoteException;
}