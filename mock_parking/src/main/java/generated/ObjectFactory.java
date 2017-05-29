
package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _NotifyOccupation_QNAME = new QName("http://soap/", "notifyOccupation");
    private final static QName _NotifyOccupationResponse_QNAME = new QName("http://soap/", "notifyOccupationResponse");
    private final static QName _NotifyVacationResponse_QNAME = new QName("http://soap/", "notifyVacationResponse");
    private final static QName _NotifyVacation_QNAME = new QName("http://soap/", "notifyVacation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotifyVacationResponse }
     * 
     */
    public NotifyVacationResponse createNotifyVacationResponse() {
        return new NotifyVacationResponse();
    }

    /**
     * Create an instance of {@link NotifyOccupationResponse }
     * 
     */
    public NotifyOccupationResponse createNotifyOccupationResponse() {
        return new NotifyOccupationResponse();
    }

    /**
     * Create an instance of {@link NotifyVacation }
     * 
     */
    public NotifyVacation createNotifyVacation() {
        return new NotifyVacation();
    }

    /**
     * Create an instance of {@link NotifyOccupation }
     * 
     */
    public NotifyOccupation createNotifyOccupation() {
        return new NotifyOccupation();
    }

    /**
     * Create an instance of {@link DateTime }
     * 
     */
    public DateTime createDateTime() {
        return new DateTime();
    }

    /**
     * Create an instance of {@link Spot }
     * 
     */
    public Spot createSpot() {
        return new Spot();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyOccupation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "notifyOccupation")
    public JAXBElement<NotifyOccupation> createNotifyOccupation(NotifyOccupation value) {
        return new JAXBElement<NotifyOccupation>(_NotifyOccupation_QNAME, NotifyOccupation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyOccupationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "notifyOccupationResponse")
    public JAXBElement<NotifyOccupationResponse> createNotifyOccupationResponse(NotifyOccupationResponse value) {
        return new JAXBElement<NotifyOccupationResponse>(_NotifyOccupationResponse_QNAME, NotifyOccupationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyVacationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "notifyVacationResponse")
    public JAXBElement<NotifyVacationResponse> createNotifyVacationResponse(NotifyVacationResponse value) {
        return new JAXBElement<NotifyVacationResponse>(_NotifyVacationResponse_QNAME, NotifyVacationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotifyVacation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "notifyVacation")
    public JAXBElement<NotifyVacation> createNotifyVacation(NotifyVacation value) {
        return new JAXBElement<NotifyVacation>(_NotifyVacation_QNAME, NotifyVacation.class, null, value);
    }

}
