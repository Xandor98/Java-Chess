package Chess.misc;

import java.io.*;
import java.util.Properties;

public class Settings {

    public static int PORT;
    public static int SSLPORT;

    public static String SSL_CERT_STORE;
    public static String SSL_CERT_STORE_PASSWD;

    public static void load(String path){
        Properties props = new Properties();

        if(path != null){
            try(InputStream propStream = new FileInputStream(new File(path))){
                props.load(propStream);
            } catch (IOException e) {
                Logger.error("Could not load Properties. Given Path", path);
            }
        }

        PORT = Integer.parseInt(props.getProperty("PORT", "8765"));
        SSLPORT = Integer.parseInt(props.getProperty("SSL_PORT", "8766"));

        SSL_CERT_STORE = props.getProperty("SSL_CER_STORE", "");
        SSL_CERT_STORE_PASSWD = props.getProperty("SSL_CER_STORE_PASSWD", "");
    }
}
