package Chess.Server;

import Chess.Game.Game;
import Chess.generated.*;
import Chess.misc.Logger;
import Chess.misc.Settings;
import Chess.networking.XmlInputStream;
import Chess.networking.XmlOutputStream;

import javax.net.ssl.SSLServerSocketFactory;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Server {

    private ServerSocket socket;
    private ServerSocket sslSocket;

    private Game game;

    public Server() {
    }

    private void printAllListeningConnections(ServerSocket s, boolean ssl) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

        while (nets.hasMoreElements()){
            NetworkInterface n = nets.nextElement();
            Enumeration<InetAddress> inetAddress = n.getInetAddresses();
            while (inetAddress.hasMoreElements()){
                InetAddress address = inetAddress.nextElement();

                if (address instanceof Inet4Address){
                    Logger.info("Listening on", address.getHostAddress() + ":" + s.getLocalPort(), "(" + (ssl? "" : "no") + " ssl)");
                }
            }
        }
    }

    public void start(){

        try{

            socket = new ServerSocket(Settings.PORT);

            boolean ssl = false;
            if(!Settings.SSL_CERT_STORE.isEmpty()){
                if(new File(Settings.SSL_CERT_STORE).exists()){
                    System.setProperty("javax.net.ssl.keyStorePassword", Settings.SSL_CERT_STORE_PASSWD);
                    System.setProperty("javax.net.ssl.keyStore", Settings.SSL_CERT_STORE);

                    System.setProperty("javax.net.ssl.trustStorePassword", Settings.SSL_CERT_STORE_PASSWD);
                    System.setProperty("javax.net.ssl.trustStore", Settings.SSL_CERT_STORE);

                    sslSocket = SSLServerSocketFactory.getDefault().createServerSocket(Settings.SSLPORT);
                    ssl = true;
                }
            }
            printAllListeningConnections(socket, false);
            if(ssl){
                printAllListeningConnections(sslSocket, true);
            }

            FutureTask<Socket> clientRev = new FutureTask<>(() -> socket.accept());
            clientRev.run();
            FutureTask<Socket> sslClientRev = null;
            if(ssl){
                sslClientRev = new FutureTask<>(() -> sslSocket.accept());
                sslClientRev.run();
            }

            game = new Game();

            while(!game.hasStarted()){
                if(clientRev.isDone()){
                    try {
                        Socket client = clientRev.get();
                        Logger.info("Client connected.", client.getInetAddress());
                        Thread t1 = new Thread(() -> firstConntact(client));
                        t1.start();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    clientRev = new FutureTask<>(() -> socket.accept());
                    clientRev.run();
                }

                if(ssl && sslClientRev.isDone()){
                    try {
                        Socket client = sslClientRev.get();
                        Logger.info("Client connected.", client.getInetAddress());
                        Thread t1 = new Thread(() -> firstConntact(client));
                        t1.start();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    sslClientRev = new FutureTask<>(() -> socket.accept());
                    sslClientRev.run();
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void firstConntact(Socket client){
        boolean truth = false;

        try {
            XmlInputStream in = new XmlInputStream(client.getInputStream());
            XmlOutputStream out = new XmlOutputStream(client.getOutputStream());

            while (!truth){
                ChessMessage message = in.readChessMessage();
                if(message.getLogin() != null){
                    LoginMessage loginMessage = message.getLogin();
                    COLOR color = getRandomColor();

                    if(game.getFreeColor() != null){
                        color = game.getFreeColor();
                    }

                    Client c = new Client(color, client, loginMessage.getName());

                    final COLOR Fcolor = color;
                    ChessMessage trueMess = new ChessMessage(){{
                        this.setLoginReply(new LoginReplyMessage(){{
                            this.setColor(Fcolor);
                        }});
                        this.setMessageType(MessageType.LOGIN_REPLY);
                        this.setColor(Fcolor);
                    }};
                    out.write(trueMess);

                    game.addClient(c);
                    truth = true;
                }else{
                    ChessMessage falseMess = new ChessMessage(){{
                        this.setAccept(new AcceptMessage(){{
                            this.setAccept(false);
                            this.setErrortypeCode(Errortype.AWAIT_LOGIN);
                        }});
                        this.setMessageType(MessageType.ACCEPT);
                        this.setColor(null);
                    }};
                    out.write(falseMess);
                }
            }
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private COLOR getRandomColor(){
        return new Random(System.currentTimeMillis()).nextDouble() < 0.5 ? COLOR.BLACK : COLOR.WHITE;
    }
}
