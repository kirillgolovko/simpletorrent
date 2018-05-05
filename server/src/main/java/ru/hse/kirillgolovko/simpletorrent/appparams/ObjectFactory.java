
package ru.hse.kirillgolovko.simpletorrent.appparams;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.hse.kirillgolovko.simpletorrent.appparams package. 
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

    private final static QName _AppParams_QNAME = new QName("", "appParams");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.hse.kirillgolovko.simpletorrent.appparams
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AppParams }
     * 
     */
    public AppParams createAppParams() {
        return new AppParams();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "appParams")
    public JAXBElement<AppParams> createAppParams(AppParams value) {
        return new JAXBElement<AppParams>(_AppParams_QNAME, AppParams.class, null, value);
    }

}