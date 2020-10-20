package primes.model;

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.rmi.registry.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;


public class RmiServer extends UnicastRemoteObject implements Rmi_S_I {
	static ArrayList<Rmi_C_I> client=new ArrayList<Rmi_C_I>();
	static int serv=0;
    static int  espera=0;
	static String MULTICAST_ADDRESS = "224.0.1.0";
	static int PORT = 4321;
	static MulticastSocket socket = null;
	static String userIn=null;


	public RmiServer() throws RemoteException {
		super();
	}

	public void print_on_server(String s) throws RemoteException {
		System.out.println("> " + s);
	}

	public String getUserIn() throws RemoteException {
		return userIn;
	}

	public void subscribe(String name, Rmi_C_I c) throws RemoteException {
		System.out.println("Subscribing " + name);
		client.add(c);
	}

	public String login(String username,String password) throws RemoteException,ClassNotFoundException{
		String msg = "type|login;user|"+username+";password|"+password;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			System.out.println(res);
			socket.close();



			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String logout(String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|logout;user|"+username;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			System.out.println(res);
			socket.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String checkAdmin(String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|admincheck;user|"+username;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			socket.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String signup(String username,String password, String email) throws RemoteException,ClassNotFoundException{
		String msg = "type|signup;user|"+username+";password|"+password+";email|"+email;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			socket.close();
			return res;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return res;
	}

	public String addAdmin(String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|admin;user|"+username;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			String[] splitString = res.split(",");
			if (splitString[1].equals("notify")){
				res="acepted";
			}
			return res;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void sair(Rmi_C_I c) throws RemoteException,ClassNotFoundException{
		this.client.remove(c);
	}

	public String pesquisa(String pesquisa, String username) throws RemoteException,ClassNotFoundException{//ver com o monteiro
		String msg = "type|search;user|"+username+";pesquisa|"+pesquisa;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[100000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			System.out.println(res);
			socket.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String pLigacoes(String link,String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|liga;user|"+username+";plig|"+link;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			socket.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String showHistorico(String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|hist;user|"+username;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			System.out.println(res);
			socket.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}


    public String indexa(String link) throws RemoteException,ClassNotFoundException{
        String msg = "type|indexa;link|"+link;
        sendMsg(msg);
        String res="";
        try {
            socket = new MulticastSocket(PORT);  // create socket and bind it
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            byte[] buffer = new byte[10000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
            String message = new String(packet.getData(), 0, packet.getLength());
            res = packetHandler(message);
            System.out.println(res);
            socket.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }




	public boolean testePrimary(){
		return true;
	}

    public static void sendMsg(String msg) {
		String MULTICAST_ADDRESS = "224.0.224.0";
		int PORT = 4321;
		MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();  // create socket without binding it (only for sending)
			byte[] buffer = msg.getBytes();
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
			socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

	private static String getValue(String value) {
		/**<h1>getValue</h1>
		 * getter do comando a executar
		 * @param value String do tipo "type | comando"
		 * @return Comando a executar
		 */
		String[] splitString = value.split("\\|");
		return splitString[1];
	}

	public  String begin (String username) throws RemoteException,ClassNotFoundException{
		String msg = "type|begin;user|"+username;
		sendMsg(msg);
		String res="";
		try {
			socket = new MulticastSocket(PORT);  // create socket and bind it
			InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
			socket.joinGroup(group);
			byte[] buffer = new byte[10000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
			String message = new String(packet.getData(), 0, packet.getLength());
			res = packetHandler(message);
			socket.close();
			return res;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return res;

	}

	public static String packetHandler(String message) throws IOException, ClassNotFoundException{
	    String[] info = message.split(";");
		String action = getValue(info[0]);
		String resposta ="";
		int r;
		switch (action) {
			case "signup":
				resposta = getValue(info[3]);
				break;
			case "login":
                resposta = getValue(info[2]);
				break;
			case "logout":
                resposta = getValue(info[3]);
				break;
			case "admin":
				resposta = getValue(info[1])+","+getValue(info[2]);
				break;
			case "admincheck":
				resposta = getValue(info[2]);
				break;
			case "search":
				resposta = getValue(info[3]);
				break;
            case "indexa":
                resposta = getValue(info[1]);
                break;
			case "hist":
				resposta = getValue(info[3]);
				break;
			case "liga":
				resposta = getValue(info[3]);
				break;
			case "begin":
				if(getValue(info[2]).equals("acepted")){
					resposta=getValue(info[3]);
				}
				else {
					resposta="none";
				}
				break;
			default:
				break;
		}
		return resposta;
	}




	public static void backup(RmiServer s) throws RemoteException {
		Rmi_S_I look_up;
		Boolean state = true;
		RmiServer hh=null;
		while (state) {
			try {
				Thread.sleep(3000);
			}catch (InterruptedException e){
				System.out.println("Excecao no backup " + e);
			}

			try {
				//look_up = (InterfaceServer) Naming.lookup("rmi://localhost:5000/sd");
				look_up = (Rmi_S_I) LocateRegistry.getRegistry(5000).lookup("sd");

				if (look_up != null) {
					state = look_up.testePrimary();
				}
			}catch (NotBoundException |  RemoteException e) {
				hh = new RmiServer();
				state = false;
				System.out.println("----MAIN SERVER----");
				Registry regg = LocateRegistry.createRegistry(5000);
				regg.rebind("sd", hh);
				System.out.println("Hello Server ready.");
				/*no segundo fica aqui*/


			}
		}
	}

	// =======================================================

	public static void main(String args[]) throws ClassNotFoundException{
		String ipaddress = "192.168.1.94";
		String a="";



		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		RmiServer h=null;
		try {
			//User user = new User();
			h = new RmiServer();
			//System.setProperty("java.rmi.server.hostname", "192.168.x.x");
			System.setProperty("java.rmi.server.hostname", ipaddress);
			Registry reg = LocateRegistry.createRegistry(5000);
			reg.rebind("Foo", h);
			System.out.println("Hello Server ready.");
			/*while (true) {
				if (serv==1){
					client.get(0).print_on_client("segundo","anon");
				}
				}*/
		}catch (ExportException e){
			System.out.println("----SECUNDARY SERVER----");

		} catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}
	}
}
