
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.ParseException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Multicast{
    /**
     * <h1>Servidor Multicast</h1>
     * Responsavél por receber os packets e enviá-los para o MulticastServer
     * O construtor cria a socket, com um endereço e um porto e faz join desse grupo multicast
     * @param socket Socket multicast
     * @param server Servidor
     * @param server objeto MulticastServer que tratará de decifrar os pacotes recebidos e enviar devida resposta
     *
     */
    private MulticastSocket socket;
    private MulticastServer server;
    public static final long serialVersionUID = 42L;
    private Multicast() throws IOException{
        server = new MulticastServer();
        String multicast_address = "224.0.224.0";
        InetAddress group = InetAddress.getByName(multicast_address);
        socket = new MulticastSocket(4321);
        socket.setReuseAddress(true);
        socket.joinGroup(group);

    }

    private void start() throws ParseException{
        try {
            while (true) {
                byte[] buffer = new byte[10000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(message);
                server.packetHandler(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        Multicast receiver = new Multicast();
        System.out.println("Multicast Ready...");
        receiver.start();
    }
}

class MulticastServer {
    static final long serialVersionUID = 42L;
    private static MulticastSocket socket;
    private static InetAddress group;
    private static ArrayList<User> userslist = new ArrayList<User>();
    private static ArrayList<Url> urlslist = new ArrayList<Url>();
    private static HashMap< String, ArrayList<String>> Hmap=new HashMap< String, ArrayList<String>>();
    public static int msgEnv=0;
private int LIMITE=5;
    private static FicheiroObjeto users = new FicheiroObjeto();
    private static FicheiroObjeto urls = new FicheiroObjeto();

    MulticastServer() throws IOException{

        socket = new MulticastSocket(4321);
        socket.setReuseAddress(true);
        String multicast_address = "224.0.1.0";
        group = InetAddress.getByName(multicast_address);
        socket.joinGroup(group);
        File file = new File("HashMaps.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String palavra ;
        /*users.abreEscrita("urls.bin");
        users.escreveObjeto(userslist);
        users.fechaEscrita();
        users.abreEscrita("users.bin");
        users.escreveObjeto(userslist);
        users.fechaEscrita();*/
        ArrayList<String> ull=  new ArrayList<String>();
        try {
            this.userslist = readUsersFile("users.bin");
            this.urlslist = readUrlsFile("urls.bin");
        } catch (ClassNotFoundException exception) {
            System.out.println("erro");
        }
        while ((st = br.readLine()) != null){
            ull.clear();
            String[] info = st.split(",");
            palavra = info[0];
            for (int i=1;i<info.length;i++) {
                ull.add(info[i]);
            }
            this.Hmap.put(palavra,ull);
        }
        System.out.println(Arrays.asList("->"+this.Hmap));
        writeHash();
        for (int i=0;i<this.userslist.size();i++){
            if(this.userslist.get(i).getUsername().equals("ze")){
                this.userslist.get(i).setAdministrador(true);
            }
        }


    }
    //connection = getConnection();


    public void intera(String ws, int vez) {
        if (vez<LIMITE){
            vez++;
            msgEnv=0;
            ArrayList<String> urlSec = new ArrayList<String>();
            String titulo="";
            // Read website
            String lk = ws;
            try {
                if (! ws.startsWith("http://") && ! ws.startsWith("https://")){
                    lk="http://"+lk;
                    ws = "http://".concat(ws);
                }
                // Attempt to connect and get the document
                Document doc;
                doc = null;
                try{
                    doc = Jsoup.connect(ws).get();  // Documentation: https://jsoup.org/
                }
                catch (MalformedURLException e) {//caso o url nao exista
                    this.sendPacket(String.format("type|indexa;status|declined"));
                    //e.printStackTrace();
                    System.out.println("erro");
                }
                // Title
                titulo=doc.title();
                System.out.println(doc.title() + "\n");
                // Get all links
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    // Ignore bookmarks within the page
                    if (link.attr("href").startsWith("#")) {
                        continue;
                    }
                    // Shall we ignore local links? Otherwise we have to rebuild them for future parsing
                    if (!link.attr("href").startsWith("http")) {
                        continue;
                    }
                    intera(link.attr("href"), vez);
                }
                // Get website text and count words
                String intro= doc.body().text();
                String[] parts = intro.split(" ");
                intro=parts[0]+" "+parts[1]+" "+parts[2]+" "+parts[3]+" "+parts[4]+" "+parts[5]+" "+parts[6]+" "+parts[7]+" "+parts[8]+" "+parts[9]+"....";
                System.out.println("exp:  "+ intro);
                String text = doc.text(); // We can use doc.body().text() if we only want to get text from <body></body>
                Url aux;
                int ver=0;
                for (int i=0;i<this.urlslist.size();i++){
                    if (this.urlslist.get(i).get_link().equals(ws)){
                        ver=1;
                    }
                }
                if (ver==0){
                    aux= new Url(ws, doc.title(), intro, urlSec);
                    this.urlslist.add(aux);
                    System.out.println();
                    urls.abreEscrita("urls.bin");
                    urls.escreveObjeto(this.urlslist);
                    urls.fechaEscrita();
                    this.sendPacket(String.format("type|indexa;status|acepted"));
                    countWords(text, ws);
                }
            } catch (IOException e ) {
                this.sendPacket(String.format("type|indexa;status|declined"));
                //e.printStackTrace();
                System.out.println("erro");
            }
        }

    }



    private void countWords(String text, String ws) {
        Map<String, Integer> countMap = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;
        int verifica=0;
        // Get words and respective count
        while (true) {
            try {
                if ((line = reader.readLine()) == null)
                    break;
                String[] words = line.split("[ ,;:.?!(){}\\[\\]<>']+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if ("".equals(word)) {
                        continue;
                    }
                    if (!countMap.containsKey(word)) {
                        countMap.put(word, 1);
                    }
                    else {
                        countMap.put(word, countMap.get(word) + 1);
                    }
                }
            } catch (IOException e) {
                this.sendPacket(String.format("type|indexa;status|declined"));
                e.printStackTrace();
            }
        }
        // Close reader
        try {
            reader.close();
        } catch (IOException e) {
            this.sendPacket(String.format("type|indexa;status|declined"));
        }
        ArrayList<String> arlist=new ArrayList<String>();
        ArrayList<String> aux=new ArrayList<String>();
        // Display words and counts
        for (String word : countMap.keySet()) {
            verifica=0;
            if (word.length() >= 3) {   //Shall we ignore small words?
                if (Hmap.containsKey(word)){
                    aux=Hmap.get(word);
                    for (int j=0;j<aux.size();j++){
                        if(aux.get(j).equals(ws)){
                            verifica=1;
                            break;
                        }
                    }
                    if (verifica==0){
                        aux=Hmap.get(word);
                        aux.add(ws);
                        Hmap.replace(word,aux);
                    }
                }
                else {
                    arlist.clear();
                    arlist.add(ws);
                    Hmap.put(word,arlist);
                }
            }
        }
        /*writeHash();
        if(vez<=3){
            for (int i=0;i<urlSec.size();i++){
                String st="";
                st=urlSec.get(i);
                vez++;
                if(vez<3){
                    v2(st,vez);
                }
            }
        }
        else if(this.msgEnv==0){
            this.msgEnv=1;
            this.sendPacket(String.format("type|indexa;status|acepted"));
        }*/
        writeHash();
    }

    public static void writeHash(){
        PrintWriter writer = null;
        String st="";
        String aux="";
        try {
            writer = new PrintWriter("HashMaps.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Set setOfKeys = Hmap.keySet();
        Iterator iterator = setOfKeys.iterator();
        while (iterator.hasNext()){
            st="";
            String key = (String) iterator.next();
            ArrayList<String> liista = (ArrayList<String>) Hmap.get(key);
            for (int i=0;i<liista.size();i++){
                aux=liista.get(i);
                st=st+","+aux;
            }
            writer.println(key+st);
        }
        writer.close();
    }


    public String pesquisa(String ws) {
        File file = new File("HashMaps.txt");
        BufferedReader br=null;
        try{
            br = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("erro");
            // FileNotFoundExceptions are handled here.
        }

        String st="";
        String palavra;
        System.out.println(Arrays.asList("->"+this.Hmap));
        System.out.println("palavra"+ws);
        if (this.Hmap.containsKey(ws)){
            System.out.println("entrou n1");
            ArrayList <String> aux=new ArrayList<String>();
            aux=this.Hmap.get(ws);
            Url asd;
            int t=0;
            st="";
            if(aux!=null){
                System.out.println("if");
                for (int j=0;j<aux.size();j++) {
                    System.out.println("j"+j);
                    for(int i=0;i<this.urlslist.size();i++){
                        System.out.println(aux.get(j)+"VS"+this.urlslist.get(i).get_link());
                        if (aux.get(j).equals(this.urlslist.get(i).get_link())) {
                            st=st+this.urlslist.get(j).get_titulo()+","+this.urlslist.get(j).get_link()+","+this.urlslist.get(j).get_introducao()+":::";
                        }
                    }
                }
            }
            else {
                System.out.println("else");
            }
            return st;
        }
        else {
            System.out.println("nao contem");
            return "";
        }

    }


    public void Apontados(String[] info) {
        String st="";
        String username=this.getValue(info[1]);
        String link=this.getValue(info[2]);
        ArrayList<String> aux =new ArrayList<String>();
        for (int i =0;i<this.urlslist.size();i++) {
            aux=this.urlslist.get(i).get_indexado();
            for (int j=0;j<aux.size();j++) {
                if(link.equals(aux.get(j))) {
                    st=st+this.urlslist.get(i).get_link()+"\n"+this.urlslist.get(i).get_titulo()+"\n"+this.urlslist.get(i).get_introducao()+"\n\n";

                }
            }
        }
        if(st.length()==0){
            this.sendPacket(String.format("type|hist;user|%s;status|accepted;msg|Nao encontrou nada",username));
        }
        else{
            this.sendPacket(String.format("type|hist;user|%s;status|declined;msg|%s",username,st));
        }

    }


    void packetHandler(String message) throws ParseException, IOException, ClassNotFoundException {
        String[] info = message.split(";");
        String action = this.getValue(info[0]);
        switch (action) {
            case "signup":
                this.signUp(info);
                break;
            case "login":
                this.login(info);
                break;
            case "logout":
                this.logout(info);
                break;
            case "admin":
                this.addAdmin(info);
                break;
            case "admincheck":
                this.checkAdmin(info);
                break;
            case "search":
                this.search(info);
                break;
            case "indexa":
                String v3 = this.getValue(info[1]);
                this.intera(v3,0);
                break;
            case "hist":
                this.showHistorico(info);
                break;
            case "begin":
                this.deliverNoti(info);
                break;
            case "liga":
                this.Apontados(info);
                break;
            default:
                break;
        }
    }


    private void sendPacket(String check) {
        /**<h1>SendPacket</h1>
         * Recebe a resposta e envia-la de volta para o Servidor RMIM;
         * @param buffer mensagem convertida em byte stream
         * @param packet pacote a enviar
         */
        try {
            byte[] buffer = check.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 4321);
            try {
                socket.send(packet);
                socket.send(packet);
            }
            catch (SocketException e) {
                final int mid = check.length() / 2; //get the middle of the String
                String[] parts = {check.substring(0, mid),check.substring(mid)};
                check=parts[0];
                buffer = check.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, group, 4321);
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValue(String value) {
        /**<h1>getValue</h1>
         * getter do comando a executar
         * @param value String do tipo "type | comando"
         * @return Comando a executar
         */
        String[] splitString = value.split("\\|");
        return splitString[1];
    }

    private int getInt(String value) {
        /**<h1>getValue</h1>
         * getter do comando a executar
         * @param value String do tipo "type | comando"
         * @return Comando a executar
         */
        String[] splitString = value.split("\\|");
        int v = Integer.parseInt(splitString[1]);
        return v;
    }

    private static ArrayList<User> readUsersFile(String filename) throws IOException, ClassNotFoundException {
        users.abreLeitura(filename);
        ArrayList<User> list = new ArrayList<>();
        list =(ArrayList<User>) users.leObjeto();
        users.fechaLeitura();
        return list;
    }

    private static ArrayList<Url> readUrlsFile(String filename) throws IOException, ClassNotFoundException {
        FicheiroObjeto urls = new FicheiroObjeto();
        urls.abreLeitura(filename);
        ArrayList<Url> list = (ArrayList<Url>) urls.leObjeto();
        urls.fechaLeitura();
        return list;
    }

    private boolean findUser(String username) throws IOException, ClassNotFoundException {
        FicheiroObjeto users = new FicheiroObjeto();
        boolean exists = false;
        for (User u : this.userslist) {
            if (u.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        System.out.println(exists);
        return exists;
    }

    private boolean checkUsers() throws IOException, ClassNotFoundException {
        boolean exists = false;
        if(this.userslist.size()==0){
            exists=true;
        }
        return exists;
    }

    private void checkAdmin(String[] info) throws IOException, ClassNotFoundException {
        String username = this.getValue(info[1]);
        System.out.println("->"+username);
        for (User u : this.userslist) {
            if (u.getUsername().equals(username)) {
                if(!u.getAdmin()){
                    this.sendPacket(String.format("type|admincheck;user|%s;status|declined;msg|1",username));
                }else{
                    this.sendPacket(String.format("type|admincheck;user|%s;status|acepted;msg|2",username));
                }
            }
        }
    }

    private void deliverNoti(String[] info)throws IOException, ClassNotFoundException{
        String username = this.getValue(info[1]);
        ArrayList<String> aux= new ArrayList<>();
        for (int i=0;i<this.userslist.size();i++){
            if (this.userslist.get(i).getUsername().equals(username)){
                if(this.userslist.get(i).getNotificacoes().isEmpty()){
                    this.sendPacket(String.format("type|begin;user|%s;status|declined",username));
                }
                else {
                    this.sendPacket(String.format("type|begin;user|%s;status|acepted;msg|Foi promovido a administrador!",username));
                }
                this.userslist.get(i).clearNotificacoes();
                break;
            }
        }

    }

    private void showHistorico(String[] info) throws IOException, ClassNotFoundException {
        String username = this.getValue(info[1]);
        String pesquisas="";
        for (User u : this.userslist) {
            if (u.getUsername().equals(username)) {
                ArrayList<String> hist = new ArrayList<>();
                hist = u.getHistorico();
                if(!hist.isEmpty()){
                    for(int i=0;i<hist.size();i++){
                        pesquisas += hist.get(i)+"\n";
                    }
                }else{
                    System.out.println("Nao tem pesquisas!");
                    pesquisas="Nao tem pesquisas";
                }
                this.sendPacket(String.format("type|hist;user|%s;status|accepted;msg|%s",username,pesquisas));
            }
        }
    }

    private void signUp(String[] info) throws IOException, ClassNotFoundException {
        FicheiroObjeto users = new FicheiroObjeto();
        final long serialVersionUID = 1L;
        boolean administrador=checkUsers();
        String username= this.getValue(info[1]);
        String password= this.getValue(info[2]);
        String email = this.getValue(info[3]);
        ArrayList<String> notificacoes = new ArrayList<>();
        boolean exists = findUser(username);
        if (exists) { // Se encontrar um user
            System.out.println("User already registered!");
            this.sendPacket(String.format("type|signup;user|%s;status|Declined;msg|1!",username));//ja esta registado
        }else {
            boolean adm=checkUsers();
            User u = new User(adm,username,password,email);
            this.userslist.add(u);
            users.abreEscrita("users.bin");
            users.escreveObjeto(this.userslist);
            this.sendPacket(String.format("type|signup;user|%s;status|Accepted;msg|2",username));//aceita
            System.out.println("User registered");
            users.fechaEscrita();
        }
    }

    private void login(String[] info) throws IOException, ClassNotFoundException {
        /**<h1>login</h1>
         * Faz o login de um user, caso os dados estejam corretos
         * @param username,password dados do utilizador
         */

        String username = this.getValue(info[1]);
        String password = this.getValue(info[2]);
        boolean exists = findUser(username);
        if (!exists) {   // declined porque nao existe username
            this.sendPacket(String.format("type|login;logged|off;msg|1"));//nao existe
        } else {
            for (User u : this.userslist) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    this.sendPacket(String.format("type|login;logged|on;msg|2"));//aceitou
                    userOnline(username);
                    break;
                } else if (u.getUsername().equals(username) && !u.getPassword().equals(password)) {
                    this.sendPacket(String.format("type|login;logged|off;msg|3"));//passworderrada
                    break;
                }
            }
        }
    }

    private void logout(String[] info) throws IOException, ClassNotFoundException {
        /**<h1>logout</h1>
         * Faz logout do utilizador
         * @param username utilizador a desconectar
         */
        String username = this.getValue(info[1]);
        userOffline(username);
        this.sendPacket(String.format("type|logout;user|%s;status|Accepted;msg|1",username));//saiu
    }

    private void search(String[] info) throws IOException, ClassNotFoundException {
        String username = this.getValue(info[1]);
        String pesquisa = this.getValue(info[2]);
        boolean exists = false;
        String st="";
        if(username.equals("none")){
            exists = true;
        }
        else {
            for(User u : this.userslist){
                if (u.getUsername().equals(username)){
                    u.addPesquisa(pesquisa);
                    exists=true;
                    users.abreEscrita("users.bin");
                    users.escreveObjeto(this.userslist);
                    users.fechaEscrita();
                    break;
                }
            }
        }
        if (!exists){ // não encontrar o username
            this.sendPacket(String.format("type|search;user|%s;status|Declined;",username));
        } else{ // se encontrar o username
            String[] words = pesquisa.split(" ");
            st="";
            String x="";
            System.out.println("passou aqui");
            for(int i=0;i<words.length;i++) {
                System.out.println("entrou:"+words[i]);
                x=pesquisa(words[i]);
                System.out.println("result:"+x);
                st= st+x;
            }
            if(st.length()==0){
                this.sendPacket(String.format("type|search;user|%s;status|Accepted;msg|Nao encontrou nada",username));
            }
            else {
                st="type|search;user|"+username+";status|Accepted;msg|"+st;
                this.sendPacket(String.format(st));
                //this.sendPacket(String.format("type|search;user|%s;status|Accepted;msg|%s",username,st));
            }
        }
    }

    private void addAdmin(String[] info) throws IOException, ClassNotFoundException {
        FicheiroObjeto users = new FicheiroObjeto();
        users.abreEscrita("users.bin");
        String username = this.getValue(info[1]);
        String notify="Foi promovido a Administrador!";
        boolean exists = findUser(username);
        if(!exists){
            this.sendPacket(String.format("type|admin;user|%s;status|declined",username));
        }
        else {
            for (User u : this.userslist) {
                if (u.getUsername().equals(username)) {
                    if(!u.getAdmin()){//ENCONTROU E MUDOU ADMIN
                        u.setAdministrador(true);
                        users.escreveObjeto(this.userslist);
                        users.fechaEscrita();
                        System.out.println();
                        if(u.isOnline() ){
                            sendPacket(String.format("type|admin;user|%s;status|notify",username));
                        }
                        else {
                            u.addNotificacao(notify);
                            sendPacket(String.format("type|admin;user|%s;status|accepted",username));

                        }
                    }
                    else{
                        this.sendPacket(String.format("type|admin;user|%s;status|kept",username));
                    }
                    break;
                }
            }
        }

    }

    private void userOnline(String username) throws IOException, ClassNotFoundException{
        for (User u : this.userslist) {
            if (u.getUsername().equals(username)) {
                u.online=true;
                break;
            }
        }
    }

    private void userOffline(String username) throws IOException, ClassNotFoundException {
        for (User u : this.userslist) {
            if (u.getUsername().equals(username)) {
                u.online=false;
                break;
            }
        }
    }

}
