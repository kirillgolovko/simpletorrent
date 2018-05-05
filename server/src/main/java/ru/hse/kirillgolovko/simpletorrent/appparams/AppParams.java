
package ru.hse.kirillgolovko.simpletorrent.appparams;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="appParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServerPort" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SharedDirPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxClients" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appParams", propOrder = {
    "serverPort",
    "sharedDirPath",
    "maxClients"
})
public class AppParams {

    @XmlElement(name = "ServerPort", defaultValue = "8080")
    protected int serverPort;
    @XmlElement(name = "SharedDirPath", required = true, defaultValue = "./")
    protected String sharedDirPath;
    protected int maxClients;

    /**
     * Gets the value of the serverPort property.
     * 
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Sets the value of the serverPort property.
     * 
     */
    public void setServerPort(int value) {
        this.serverPort = value;
    }

    /**
     * Gets the value of the sharedDirPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSharedDirPath() {
        return sharedDirPath;
    }

    /**
     * Sets the value of the sharedDirPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSharedDirPath(String value) {
        this.sharedDirPath = value;
    }

    /**
     * Gets the value of the maxClients property.
     * 
     */
    public int getMaxClients() {
        return maxClients;
    }

    /**
     * Sets the value of the maxClients property.
     * 
     */
    public void setMaxClients(int value) {
        this.maxClients = value;
    }

}
