package Chess.networking;

import Chess.generated.ChessMessage;
import Chess.misc.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class XmlInputStream extends UTFInputStream {

    private Unmarshaller unmarshaller;

    public XmlInputStream(InputStream inputStream) {
        super(inputStream);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ChessMessage.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                // muss getResourceAsStream() statt getResource() sein
                // damit es auch in jars funktioniert
                InputStream resourceAsStream = getClass().getResourceAsStream("/xsd/mazeCom.xsd");
                // Der Inputstream resourceAsStream wird in die Datei temp.xsd
                // geschrieben und dann dem Schema uebergeben
                // XXX: Kein bessere Implementierung gefunden
                File tempFile = File.createTempFile("temp", ".xsd");
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                int read;
                byte[] bytes = new byte[1024];
                while ((read = resourceAsStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, read);
                }
                fileOutputStream.close();
                Schema schema = schemaFactory.newSchema(tempFile);
                unmarshaller.setSchema(schema);
                tempFile.deleteOnExit();
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            Logger.error("Fail to Load JAXB");
        }
    }

    /**
     * Liest eine Nachricht und gibt die entsprechende Instanz zurueck
     * 
     * @return
     * @throws IOException
     */
    public ChessMessage readChessMessage() throws IOException, UnmarshalException {
        ChessMessage result = null;
        try {
            String xml = this.readUTF8();
            result = XMLToChessMessage(xml);
        } catch (UnmarshalException e) {
            throw e;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {
        }
        return result;
    }

    public ChessMessage XMLToChessMessage(String xml) throws JAXBException {
        StringReader stringReader = new StringReader(xml);
        return (ChessMessage) this.unmarshaller.unmarshal(stringReader);
    }

}