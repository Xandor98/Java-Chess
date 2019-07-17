package Chess.networking;

import Chess.generated.ChessMessage;
import Chess.misc.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public class XmlOutputStream extends UTFOutputStream {

    private Marshaller marshaller;

    public XmlOutputStream(OutputStream outputStream) {
        super(outputStream);
        // Anlegen der JAXB-Komponenten
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ChessMessage.class);
            this.marshaller = jaxbContext.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException jaxbException) {
            Logger.error("Fail to start JAXB");
        }
    }

    /**
     * Versenden einer XML Nachricht
     *
     * @param chessMessage
     */
    public void write(ChessMessage chessMessage) throws IOException {
        // Generierung des fertigen XML
        try {
            // Versenden des XML
            this.writeUTF8(messageToXML(chessMessage));
            this.flush();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stellt ein MazeCom-Objekt als XML dar
     *
     * @param chessMessage darzustellendes MazeCom-Objekt
     * @return XML-Darstellung als String
     * @throws JAXBException
     */
    public String messageToXML(ChessMessage chessMessage) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        this.marshaller.marshal(chessMessage, stringWriter);
        return stringWriter.toString();
    }

}
