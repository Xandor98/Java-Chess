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
 * <p>Java-Klasse für AcceptMessage complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AcceptMessage"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accept" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="errortypeCode" type="{}Errortype"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcceptMessage", propOrder = {
    "accept",
    "errortypeCode"
})
public class AcceptMessage {

    protected boolean accept;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Errortype errortypeCode;

    /**
     * Ruft den Wert der accept-Eigenschaft ab.
     * 
     */
    public boolean isAccept() {
        return accept;
    }

    /**
     * Legt den Wert der accept-Eigenschaft fest.
     * 
     */
    public void setAccept(boolean value) {
        this.accept = value;
    }

    /**
     * Ruft den Wert der errortypeCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Errortype }
     *     
     */
    public Errortype getErrortypeCode() {
        return errortypeCode;
    }

    /**
     * Legt den Wert der errortypeCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Errortype }
     *     
     */
    public void setErrortypeCode(Errortype value) {
        this.errortypeCode = value;
    }

}
