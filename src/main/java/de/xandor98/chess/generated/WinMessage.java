//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.11.24 um 09:54:54 PM CET 
//


package de.xandor98.chess.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für WinMessage complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WinMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="board" type="{}BoardData"/&gt;
 *         &lt;element name="winner" type="{}COLOR" minOccurs="0"/&gt;
 *         &lt;element name="remis" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WinMessage", propOrder = {
    "board",
    "winner",
    "remis"
})
public class WinMessage {

    @XmlElement(required = true)
    protected BoardData board;
    @XmlSchemaType(name = "string")
    protected COLOR winner;
    protected boolean remis;

    /**
     * Ruft den Wert der board-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BoardData }
     *     
     */
    public BoardData getBoard() {
        return board;
    }

    /**
     * Legt den Wert der board-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BoardData }
     *     
     */
    public void setBoard(BoardData value) {
        this.board = value;
    }

    /**
     * Ruft den Wert der winner-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link COLOR }
     *     
     */
    public COLOR getWinner() {
        return winner;
    }

    /**
     * Legt den Wert der winner-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link COLOR }
     *     
     */
    public void setWinner(COLOR value) {
        this.winner = value;
    }

    /**
     * Ruft den Wert der remis-Eigenschaft ab.
     * 
     */
    public boolean isRemis() {
        return remis;
    }

    /**
     * Legt den Wert der remis-Eigenschaft fest.
     * 
     */
    public void setRemis(boolean value) {
        this.remis = value;
    }

}
