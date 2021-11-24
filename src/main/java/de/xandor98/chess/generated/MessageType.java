//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.11.24 um 09:54:54 PM CET 
//


package de.xandor98.chess.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MessageType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AwaitMove"/&gt;
 *     &lt;enumeration value="Move"/&gt;
 *     &lt;enumeration value="Login"/&gt;
 *     &lt;enumeration value="LoginReply"/&gt;
 *     &lt;enumeration value="Accept"/&gt;
 *     &lt;enumeration value="MakeWish"/&gt;
 *     &lt;enumeration value="Wish"/&gt;
 *     &lt;enumeration value="Win"/&gt;
 *     &lt;enumeration value="Disconnect"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MessageType")
@XmlEnum
public enum MessageType {

    @XmlEnumValue("AwaitMove")
    AWAIT_MOVE("AwaitMove"),
    @XmlEnumValue("Move")
    MOVE("Move"),
    @XmlEnumValue("Login")
    LOGIN("Login"),
    @XmlEnumValue("LoginReply")
    LOGIN_REPLY("LoginReply"),
    @XmlEnumValue("Accept")
    ACCEPT("Accept"),
    @XmlEnumValue("MakeWish")
    MAKE_WISH("MakeWish"),
    @XmlEnumValue("Wish")
    WISH("Wish"),
    @XmlEnumValue("Win")
    WIN("Win"),
    @XmlEnumValue("Disconnect")
    DISCONNECT("Disconnect");
    private final String value;

    MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MessageType fromValue(String v) {
        for (MessageType c: MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
