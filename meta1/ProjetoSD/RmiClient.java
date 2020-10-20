import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.rmi.registry.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class RmiClient extends UnicastRemoteObject implements Rmi_C_I {
	public static int menuuu;
	public static RmiClient c;
	public static String userN="anon";

	RmiClient() throws RemoteException {
		super();
	}

	public void print_on_client(String s, String user) throws RemoteException {
		if (this.userN.equals(user)){
			System.out.println(s);
		}
	}

	static void menu1() {
		System.out.println("-----------------------------<ucBusca>----------------------------");
		System.out.println("------------------------------<MENU>------------------------------\n");
		System.out.println("1- Login");
		System.out.println("2- Registar");
		System.out.println("3- Pesquisar");
		System.out.println("4- Sair");
	}

	static void menu2() {
		System.out.println("-----------------------------<ucBusca>----------------------------");
		System.out.println("------------------------------<MENU>------------------------------\n");
		System.out.println("1- Pesquisar");
		System.out.println("2- Historico");
		System.out.println("3- Funcoes de Administrador");
		System.out.println("4- Pesquisar urls para que apontam");
		System.out.println("5- LogOut");
	}

	static void menu3() {
		System.out.println("-----------------------------<ucBusca>----------------------------");
		System.out.println("-----------------------<MODO ADMINISTRADOR>-----------------------\n");
		System.out.println("1- Indexar novo URL");
		System.out.println("2- Informações gerais sobre o sistema");
		System.out.println("3- Atribuir novo utilizador");
		System.out.println("4- Sair do modo Administrador");
	}

	public boolean testePrimary(){
		return true;
	}

	public static void main(String args[]) {
		String ipaddress = "192.168.1.94";
		String a;
		char ch;
		String n ;
		Scanner keyboardScanner = new Scanner(System.in);
		Rmi_S_I look_up;
		Registry reg;
		Rmi_S_I server;
		Registry reg2;
		Rmi_S_I server2;
		RmiClient c2;
		Boolean state = true;
		// usage: java RmiClient username
		System.getProperties().put("java.security.policy", "policy.all");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
 
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		try {
			//User user = new User();

			//Registry reg = LocateRegistry.getRegistry("192.168.x.x", 1099);
			reg = LocateRegistry.getRegistry(ipaddress, 5000);
			server = (Rmi_S_I) reg.lookup("Foo");
			c = new RmiClient();
			server.subscribe("", (Rmi_C_I) c);
			System.out.println("Client sent subscription to server");

			menuuu=1;
			while (true) {
				if (menuuu==1){
					menu1();
					n = keyboardScanner.nextLine();
					if(n.equals("1")){//login
						System.out.println("Username:");
						String username = keyboardScanner.nextLine();
						System.out.println("Password:");
						String password = keyboardScanner.nextLine();
						userN=username;
						String msg = server.login(username,password);
						if(msg.equals("1")){
							System.out.println("Nao está registado!");
						}else if(msg.equals("2")){
							System.out.println("Bem vindo!");
							String not =server.begin(username);
							if (!not.equals("none")){
								System.out.println(not);
							}
							menuuu=2;
						}else{
							System.out.println("Password Errada!");
						}
					}
					else if (n.equals("2")){//registar
						System.out.println("registo");
						System.out.println("Username:");
						String username = keyboardScanner.nextLine();
						System.out.println("Password:");
						String password = keyboardScanner.nextLine();
						System.out.println("Email:");
						String email = keyboardScanner.nextLine();
						String msg = server.signup(username,password,email);
						if(msg.equals("2")){
							System.out.println("Registado!");
						}
						else {
							System.out.println("Já existe!");
						}
					}
					else if (n.equals("3")){//pesquisa
						String urlst;
						System.out.println("Pesquisa: ");
						BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
						String ws = bf.readLine();
						urlst = server.pesquisa(ws,"none");
                        System.out.println(urlst);
					}
					else if(n.equals("4")){//sair
						server.sair(c);
						System.out.println("Obrigado");
						System.exit(0);
					}
					else{

						System.out.println("Valor Invalido");
					}
				}
				else if(menuuu==2){
					menu2();
					n = keyboardScanner.nextLine();
					if(n.equals("1")){//pesquisa
						String urlst;
						System.out.println("Pesquisa: ");
						BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
						String ws = bf.readLine();
						urlst = server.pesquisa(ws,userN);
						System.out.println(urlst);
					}
					else if (n.equals("2")){//historico
						System.out.println("Historico:");
						String pesq = server.showHistorico(userN);
						System.out.println(pesq);
					}/////////////////////////////////////////////////////////////////////////Isto de certeza que ta mal
					else if (n.equals("3")){//entrar admin
						String msg=server.checkAdmin(userN);
						if(msg.equals("acepted")){
							menuuu=3;
						}else{
							System.out.println("Não é Administrador!");
						}
					}
					else if (n.equals("4")){//Apontados
						String urlst;
						System.out.println("Pesquisa: ");
						BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
						String ws = bf.readLine();
						urlst = server.pLigacoes(ws,userN);
						System.out.println(urlst);

					}
					else if(n.equals("5")){//logout
						server.logout(userN);
						userN="anon";
						menuuu=1;
					}
					else{
						System.out.println("valor invalido");
					}


				}
				else {
					menu3();
					n = keyboardScanner.nextLine();
					if(n.equals("1")){//indexar url
						String comp="";
						System.out.println("Insira o Url: ");
						String ur = keyboardScanner.nextLine();
						comp= server.indexa(ur);
						//se login valido
						if (comp.equals("acepted")){
							System.out.println("Url indexado");
						}
						else {
							System.out.println("url nao indexado");
						}
					}
					else if (n.equals("2")){//info
						System.out.println("info");
						//server.info();
					}
					else if (n.equals("3")){//new admin
						System.out.println("Novo Admin:");
						String username = keyboardScanner.nextLine();
						String msg = server.addAdmin(username);
						System.out.println(msg);
						menuuu=3;
					}
					else if (n.equals("4")){//sair admin
						server.sair(c);
						System.out.println("sair admin");
						menuuu=2;
					}
					else{
						System.out.println("valor invalido");
					}
				}


				try {
					System.setProperty("java.rmi.server.hostname", ipaddress);
					look_up = (Rmi_S_I) LocateRegistry.getRegistry(5000).lookup("Foo");
					if (look_up != null) {
						state = look_up.testePrimary();
					}
				}catch (RemoteException e) {
					System.out.println("Nova ligaçao");
					continue;
				}
				catch (NotBoundException e){
					System.out.println("entrou neste");
					Thread.sleep(6000);
					reg = LocateRegistry.getRegistry(ipaddress, 5000);
					server = (Rmi_S_I) reg.lookup("Fooo");
					c = new RmiClient();
					server.subscribe("", (Rmi_C_I) c);
				}
			}

		}
		catch (NotBoundException |  RemoteException x) {
			try {
				reg = LocateRegistry.getRegistry(ipaddress, 5000);
				server = (Rmi_S_I) reg.lookup("Foo");
				c = new RmiClient();
				server.subscribe("", (Rmi_C_I) c);
				System.out.println("Ligado a novo servidor rmi");
				while (true) {
					if (menuuu==1){
						menu1();
						n = keyboardScanner.nextLine();
						if(n.equals("1")){//login
							System.out.println("Username:");
							String username = keyboardScanner.nextLine();
							System.out.println("Password:");
							String password = keyboardScanner.nextLine();
							userN=username;
							String msg="";
							try {
								msg = server.login(username,password);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							if(msg.equals("1")){
								System.out.println("Nao está registado!");
							}else if(msg.equals("2")){
								System.out.println("Bem vindo!");
								String not="";
								try {
									not =server.begin(username);
								} catch (ClassNotFoundException exception) {
									System.out.println("erro");
								}
								if (!not.equals("none")){
									System.out.println(not);
								}
								menuuu=2;
							}else{
								System.out.println("Password Errada!");
							}
						}
						else if (n.equals("2")){//registar
							System.out.println("registo");
							System.out.println("Username:");
							String username = keyboardScanner.nextLine();
							System.out.println("Password:");
							String password = keyboardScanner.nextLine();
							System.out.println("Email:");
							String email = keyboardScanner.nextLine();
							String msg="";
							try {
								msg = server.signup(username,password,email);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}

							if(msg.equals("2")){
								System.out.println("Registado!");
							}else {
								System.out.println("Já existe!");
							}
						}
						else if (n.equals("3")){//pesquisa
							String urlst="";
							System.out.println("Pesquisa: ");
							BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
							String ws="";
							try{
								ws = bf.readLine();
							}
							catch(IOException ex){
								System.out.println("erro");
							}
							try {
								urlst = server.pesquisa(ws,"none");
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println(urlst);
						}
						else if(n.equals("4")){//sair
							try {
								server.sair(c);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println("Obrigado");
							System.exit(0);
						}
						else{

							System.out.println("Valor Invalido");
						}
					}
					else if(menuuu==2){
						menu2();
						n = keyboardScanner.nextLine();
						if(n.equals("1")){//pesquisa
							String urlst="";
							System.out.println("Pesquisa: ");
							BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
							String ws="";
							try{
								ws = bf.readLine();
							}
							catch(IOException ex){
								System.out.println("erro");
							}
							try {
								urlst = server.pesquisa(ws,userN);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println(urlst);
						}
						else if (n.equals("2")){//historico
							System.out.println("Historico:");
							String pesq="";
							try {
								pesq = server.showHistorico(userN);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println(pesq);
						}/////////////////////////////////////////////////////////////////////////Isto de certeza que ta mal
						else if (n.equals("3")){//entrar admin
							String msg="";
							try {
								msg=server.checkAdmin(userN);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							if(msg.equals("acepted")){
								menuuu=3;
							}else{
								System.out.println("Não é Administrador!");
							}
						}
						else if (n.equals("4")){//Apontados
							String urlst="";
							System.out.println("Pesquisa: ");
							BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
							String ws="";
							try{
								ws = bf.readLine();
							}
							catch(IOException ex){
								System.out.println("erro");
							}
							try {
								urlst = server.pLigacoes(ws,userN);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println(urlst);

						}
						else if(n.equals("5")){//logout
							try {
								server.logout(userN);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							userN="anon";
							menuuu=1;
						}
						else{
							System.out.println("valor invalido");
						}

					}
					else {
						menu3();
						n = keyboardScanner.nextLine();
						if(n.equals("1")){//indexar url
							String comp="";
							System.out.println("Insira o Url: ");
							String ur = keyboardScanner.nextLine();
							try {
								comp= server.indexa(ur);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}

							//se login valido
							if (comp.equals("acepted")){
								System.out.println("Url indexado");
							}
							else {
								System.out.println("url nao indexado");
							}
						}
						else if (n.equals("2")){//info
							System.out.println("info");
							//server.info();
						}
						else if (n.equals("3")){//new admin
							System.out.println("Novo Admin:");
							String username = keyboardScanner.nextLine();
							String msg ="";
							try {
								msg = server.addAdmin(username);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}

							System.out.println(msg);
							menuuu=3;
						}
						else if (n.equals("4")){//sair admin
							try {
								server.sair(c);
							} catch (ClassNotFoundException exception) {
								System.out.println("erro");
							}
							System.out.println("sair admin");
							menuuu=2;
						}
						else{
							System.out.println("valor invalido");
						}
					}


					try {
						System.setProperty("java.rmi.server.hostname", ipaddress);
						look_up = (Rmi_S_I) LocateRegistry.getRegistry(5000).lookup("Foo");
						if (look_up != null) {
							state = look_up.testePrimary();
						}
					}catch (NotBoundException |  RemoteException e) {
						System.out.println("entrou neste");
						reg = LocateRegistry.getRegistry(ipaddress, 5000);
						server = (Rmi_S_I) reg.lookup("Fooo");
						c = new RmiClient();
						server.subscribe("", (Rmi_C_I) c);
						System.out.println("Client sent subscription to server");

					}
				}

				/*No segundo fica aqui*/
			} catch (NotBoundException |RemoteException e) {
				System.out.println("erroooo");
			}
		}
		catch (Exception e) {
			System.out.println("Exception in main: " + e);
		}

	}

}
