//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.11.24 um 09:54:54 PM CET 
//


package de.xandor98.chess.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Errortype.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Errortype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NOERROR"/&gt;
 *     &lt;enumeration value="ERROR"/&gt;
 *     &lt;enumeration value="AWAIT_LOGIN"/&gt;
 *     &lt;enumeration value="AWAIT_MOVE"/&gt;
 *     &lt;enumeration value="ILLEGAL_MOVE"/&gt;
 *     &lt;enumeration value="WRONG_PLAYERID"/&gt;
 *     &lt;enumeration value="TIMEOUT"/&gt;
 *     &lt;enumeration value="TOO_MANY_TRIES"/&gt;
 *     &lt;enumeration value="AWAIT_WISH"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Errortype")
@XmlEnum
public enum Errortype {

    NOERROR,
    ERROR,
    AWAIT_LOGIN,
    AWAIT_MOVE,
    ILLEGAL_MOVE,
    WRONG_PLAYERID,
    TIMEOUT,
    TOO_MANY_TRIES,
    AWAIT_WISH;

    public String value() {
        return name();
    }

    public static Errortype fromValue(String v) {
        return valueOf(v);
    }

}
