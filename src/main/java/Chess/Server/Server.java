package Chess.Server;

import Chess.misc.Logger;
import Chess.misc.Settings;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class Server {

    private ServerSocket socket;
    private ServerSocket sslSocket;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
