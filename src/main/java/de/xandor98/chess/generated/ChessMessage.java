//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.11.24 um 09:54:54 PM CET 
//


package de.xandor98.chess.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="AwaitMove" type="{}AwaitMoveMessage"/&gt;
 *         &lt;element name="Move" type="{}MoveMessage"/&gt;
 *         &lt;element name="Login" type="{}LoginMessage"/&gt;
 *         &lt;element name="LoginReply" type="{}LoginReplyMessage"/&gt;
 *         &lt;element name="MakeWish" type="{}MakeAWishMessage"/&gt;
 *         &lt;element name="Wish" type="{}WishMessage"/&gt;
 *         &lt;element name="Win" type="{}WinMessage"/&gt;
 *         &lt;element name="Accept" type="{}AcceptMessage"/&gt;
 *         &lt;element name="Disconnect" type="{}DisconnectMessage"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="messageType" type="{}MessageType" /&gt;
 *       &lt;attribute name="color" type="{}COLOR" /&gt;
 *       &lt;attribute name="PlayerID" type="{}UUID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "awaitMove",
    "move",
    "login",
    "loginReply",
    "makeWish",
    "wish",
    "win",
    "accept",
    "disconnect"
})
@XmlRootElement(name = "ChessMessage")
public class ChessMessage {

    @XmlElement(name = "AwaitMove")
    protected AwaitMoveMessage awaitMove;
    @XmlElement(name = "Move")
    protected MoveMessage move;
    @XmlElement(name = "Login")
    protected LoginMessage login;
    @XmlElement(name = "LoginReply")
    protected LoginReplyMessage loginReply;
    @XmlElement(name = "MakeWish")
    protected MakeAWishMessage makeWish;
    @XmlElement(name = "Wish")
    protected WishMessage wish;
    @XmlElement(name = "Win")
    protected WinMessage win;
    @XmlElement(name = "Accept")
    protected AcceptMessage accept;
    @XmlElement(name = "Disconnect")
    protected DisconnectMessage disconnect;
    @XmlAttribute(name = "messageType")
    protected MessageType messageType;
    @XmlAttribute(name = "color")
    protected COLOR color;
    @XmlAttribute(name = "PlayerID")
    protected String playerID;

    /**
     * Ruft den Wert der awaitMove-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AwaitMoveMessage }
     *     
     */
    public AwaitMoveMessage getAwaitMove() {
        return awaitMove;
    }

    /**
     * Legt den Wert der awaitMove-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AwaitMoveMessage }
     *     
     */
    public void setAwaitMove(AwaitMoveMessage value) {
        this.awaitMove = value;
    }

    /**
     * Ruft den Wert der move-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MoveMessage }
     *     
     */
    public MoveMessage getMove() {
        return move;
    }

    /**
     * Legt den Wert der move-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MoveMessage }
     *     
     */
    public void setMove(MoveMessage value) {
        this.move = value;
    }

    /**
     * Ruft den Wert der login-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LoginMessage }
     *     
     */
    public LoginMessage getLogin() {
        return login;
    }

    /**
     * Legt den Wert der login-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginMessage }
     *     
     */
    public void setLogin(LoginMessage value) {
        this.login = value;
    }

    /**
     * Ruft den Wert der loginReply-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LoginReplyMessage }
     *     
     */
    public LoginReplyMessage getLoginReply() {
        return loginReply;
    }

    /**
     * Legt den Wert der loginReply-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginReplyMessage }
     *     
     */
    public void setLoginReply(LoginReplyMessage value) {
        this.loginReply = value;
    }

    /**
     * Ruft den Wert der makeWish-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MakeAWishMessage }
     *     
     */
    public MakeAWishMessage getMakeWish() {
        return makeWish;
    }

    /**
     * Legt den Wert der makeWish-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MakeAWishMessage }
     *     
     */
    public void setMakeWish(MakeAWishMessage value) {
        this.makeWish = value;
    }

    /**
     * Ruft den Wert der wish-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link WishMessage }
     *     
     */
    public WishMessage getWish() {
        return wish;
    }

    /**
     * Legt den Wert der wish-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link WishMessage }
     *     
     */
    public void setWish(WishMessage value) {
        this.wish = value;
    }

    /**
     * Ruft den Wert der win-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link WinMessage }
     *     
     */
    public WinMessage getWin() {
        return win;
    }

    /**
     * Legt den Wert der win-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link WinMessage }
     *     
     */
    public void setWin(WinMessage value) {
        this.win = value;
    }

    /**
     * Ruft den Wert der accept-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AcceptMessage }
     *     
     */
    public AcceptMessage getAccept() {
        return accept;
    }

    /**
     * Legt den Wert der accept-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptMessage }
     *     
     */
    public void setAccept(AcceptMessage value) {
        this.accept = value;
    }

    /**
     * Ruft den Wert der disconnect-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DisconnectMessage }
     *     
     */
    public DisconnectMessage getDisconnect() {
        return disconnect;
    }

    /**
     * Legt den Wert der disconnect-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DisconnectMessage }
     *     
     */
    public void setDisconnect(DisconnectMessage value) {
        this.disconnect = value;
    }

    /**
     * Ruft den Wert der messageType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MessageType }
     *     
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Legt den Wert der messageType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageType }
     *     
     */
    public void setMessageType(MessageType value) {
        this.messageType = value;
    }

    /**
     * Ruft den Wert der color-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link COLOR }
     *     
     */
    public COLOR getColor() {
        return color;
    }

    /**
     * Legt den Wert der color-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link COLOR }
     *     
     */
    public void setColor(COLOR value) {
        this.color = value;
    }

    /**
     * Ruft den Wert der playerID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Legt den Wert der playerID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerID(String value) {
        this.playerID = value;
    }

}
