//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.11.24 um 09:54:54 PM CET 
//


package de.xandor98.chess.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.xandor98.chess.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.xandor98.chess.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ChessMessage }
     * 
     */
    public ChessMessage createChessMessage() {
        return new ChessMessage();
    }

    /**
     * Create an instance of {@link AwaitMoveMessage }
     * 
     */
    public AwaitMoveMessage createAwaitMoveMessage() {
        return new AwaitMoveMessage();
    }

    /**
     * Create an instance of {@link MoveMessage }
     * 
     */
    public MoveMessage createMoveMessage() {
        return new MoveMessage();
    }

    /**
     * Create an instance of {@link LoginMessage }
     * 
     */
    public LoginMessage createLoginMessage() {
        return new LoginMessage();
    }

    /**
     * Create an instance of {@link LoginReplyMessage }
     * 
     */
    public LoginReplyMessage createLoginReplyMessage() {
        return new LoginReplyMessage();
    }

    /**
     * Create an instance of {@link MakeAWishMessage }
     * 
     */
    public MakeAWishMessage createMakeAWishMessage() {
        return new MakeAWishMessage();
    }

    /**
     * Create an instance of {@link WishMessage }
     * 
     */
    public WishMessage createWishMessage() {
        return new WishMessage();
    }

    /**
     * Create an instance of {@link WinMessage }
     * 
     */
    public WinMessage createWinMessage() {
        return new WinMessage();
    }

    /**
     * Create an instance of {@link AcceptMessage }
     * 
     */
    public AcceptMessage createAcceptMessage() {
        return new AcceptMessage();
    }

    /**
     * Create an instance of {@link DisconnectMessage }
     * 
     */
    public DisconnectMessage createDisconnectMessage() {
        return new DisconnectMessage();
    }

    /**
     * Create an instance of {@link BoardData }
     * 
     */
    public BoardData createBoardData() {
        return new BoardData();
    }

}
