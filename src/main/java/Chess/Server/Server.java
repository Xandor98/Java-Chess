package Chess.Server;

import Chess.Game.Game;
import Chess.generated.*;
import Chess.misc.Logger;
import Chess.misc.Settings;
import Chess.networking.Client;
import Chess.networking.XmlInputStream;
import Chess.networking.XmlOutputStream;

import javax.net.ssl.SSLServerSocketFactory;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private ServerSocket socket;
    private ServerSocket sslSocket;

    private ConcurrentHashMap<Integer, Game> games;

    public Server() {
        this.games = new ConcurrentHashMap<>();
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

            while(true){
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
                    LoginMessage login = message.getLogin();
                    switch (login.getMethod()){
                        case NEW_GAME:
                            int GAMEID = ThreadLocalRandom.current().nextInt(1000, 10000);

                            while(games.keySet().contains(GAMEID)){
                                GAMEID = ThreadLocalRandom.current().nextInt(1000, 10000);
                            }

                            System.out.println(GAMEID);

                            Game game = new Game(GAMEID, login.getLobbyPass() != null ? login.getLobbyPass() : "");
                            COLOR gameColor = login.getColor() != null? login.getColor() : getRandomColor();
                            Client client1 = new Client(client, 0, gameColor, login.getName());
                            game.registerClient(client1);

                            games.put(GAMEID, game);

                            int finalGAMEID = GAMEID;
                            out.write(new ChessMessage(){{
                                this.setLoginReply(new LoginReplyMessage(){{
                                    this.setGameID(finalGAMEID);
                                    this.setNewID(0);
                                    this.setColor(gameColor);
                                }});
                                this.setMessageType(MessageType.LOGIN_REPLY);
                                this.setId(0);
                                this.setGameID(finalGAMEID);
                            }});
                            truth = true;
                            break;
                        case JOIN_GAME:
                            if(games.containsKey(login.getGameID())){
                                if(games.get(login.getGameID()).verify(login.getLobbyPass() != null ? login.getLobbyPass() : "")){
                                    Game game1 = games.get(login.getGameID());
                                    COLOR gameColor1 = game1.getFreeColor();
                                    Client client2 = new Client(client, 1, gameColor1, login.getName());

                                    int finalGAMEID1 = login.getGameID();
                                    out.write(new ChessMessage(){{
                                        this.setLoginReply(new LoginReplyMessage(){{
                                            this.setGameID(finalGAMEID1);
                                            this.setNewID(1);
                                            this.setColor(gameColor1);
                                        }});
                                        this.setMessageType(MessageType.LOGIN_REPLY);
                                        this.setId(1);
                                        this.setGameID(finalGAMEID1);
                                    }});

                                    game1.registerClient(client2);

                                    truth = true;
                                }else {
                                    for (Game value : games.values()) {
                                        if(value.isFreeToJoin()){
                                            COLOR gameColor1 = value.getFreeColor();
                                            Client client2 = new Client(client, 1, gameColor1, login.getName());

                                            int finalGAMEID1 = login.getGameID();
                                            out.write(new ChessMessage(){{
                                                this.setLoginReply(new LoginReplyMessage(){{
                                                    this.setGameID(finalGAMEID1);
                                                    this.setNewID(1);
                                                    this.setColor(gameColor1);
                                                }});
                                                this.setMessageType(MessageType.LOGIN_REPLY);
                                                this.setId(1);
                                                this.setGameID(finalGAMEID1);
                                            }});

                                            value.registerClient(client2);

                                            truth = true;
                                        }
                                    }
                                    if(!truth) {
                                        out.write(new ChessMessage() {{
                                            this.setAccept(new AcceptMessage() {{
                                                this.setErrortypeCode(Errortype.WRONG_LOBBY_PASS);
                                                this.setAccept(false);
                                            }});
                                            this.setMessageType(MessageType.ACCEPT);
                                            this.setId(-1);
                                            this.setGameID(-1);
                                        }});
                                    }
                                }
                            }else{
                                out.write(new ChessMessage(){{
                                    this.setAccept(new AcceptMessage(){{
                                        this.setErrortypeCode(Errortype.NO_GAME_FOUND);
                                        this.setAccept(false);
                                    }});
                                    this.setMessageType(MessageType.ACCEPT);
                                    this.setId(-1);
                                    this.setGameID(-1);
                                }});
                            }
                            break;
                    }
                }else{
                    ChessMessage falseMess = new ChessMessage(){{
                        this.setAccept(new AcceptMessage(){{
                            this.setAccept(false);
                            this.setErrortypeCode(Errortype.AWAIT_LOGIN);
                        }});
                        this.setMessageType(MessageType.ACCEPT);
                        this.setId(-1);
                        this.setGameID(-1);
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
