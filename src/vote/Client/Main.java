package vote.Client;

import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.ConnexionReponse;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteConnexion;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteGetSondage;
import vote.Urne.Requete.RequeteClient.RequeteUtilisateur.RequeteVote;
import vote.Urne.metier.Sondage;
import vote.crypto.ElGamal;
import vote.crypto.Message;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        ConnexionReponse rep = null;
        Sondage sondage = null;
        String IP = "localhost";
        int port = 5565;
        boolean isConnected = false;
        System.setProperty("javax.net.ssl.trustStore", SSLConf.getInstance().getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", SSLConf.getInstance().getPassword());

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while (!s.equals("exit")){
            String[] split = new String[4];
            split = s.split(" ");
            String command = split[0];
            if (command.equals("connect")){
                String email = split[1];
                String password = split[2];
                if (split.length > 3) {
                    IP = split[3];

                    port = Integer.parseInt(split[4]);
                }

                System.out.println("Connecting to " + IP + " on port " + port + " with email " + email + " and password " + password);
                try {
                    SSLSocketFactory socket = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    Socket socket1 = socket.createSocket(IP, port);
                    ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
                    oos.writeObject(new RequeteConnexion(email, password));
                    rep = (ConnexionReponse) new ObjectInputStream(socket1.getInputStream()).readObject();
                    System.out.println("Connected with id " + rep);
                    oos.flush();

                    SSLSocketFactory socket2 = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    Socket socket3 = socket2.createSocket(IP, port);
                    ObjectOutputStream oos1 = new ObjectOutputStream(socket3.getOutputStream());

                    System.out.println("Sent , Connection established");
                    RequeteGetSondage req = new RequeteGetSondage(rep.getSsid());
                    oos1.writeObject(req);
                    oos1.flush();

                    System.out.println("Sent , Waiting for response");

                    ObjectInputStream ois = new ObjectInputStream(socket3.getInputStream());
                    sondage = (Sondage) ois.readObject();
                    System.out.println("sondage right here : " + sondage);
                    isConnected = true;
            } catch (IOException e) {
                    System.out.println("Error");

                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

                if (command.equals("send") && isConnected){
                    BigInteger id = new BigInteger(split[1]);

                    assert sondage != null;
                    Message message = ElGamal.encrypt(id,sondage.getPublicKeyInfo());

                    SSLSocketFactory socket2 = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    Socket socket3 = socket2.createSocket(IP, port);

                    ObjectOutputStream oos117 = new ObjectOutputStream(socket3.getOutputStream());
                    assert rep != null;
                    oos117.writeObject(new RequeteVote(message, rep.getSsid()));

                }
                s = sc.nextLine();
        }

    }



}