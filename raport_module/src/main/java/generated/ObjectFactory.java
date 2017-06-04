
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

    private final static QName _GetAllSpotsNumber_QNAME = new QName("http://soap.api/", "getAllSpotsNumber");
    private final static QName _FindTicketByPlaceResponse_QNAME = new QName("http://soap.api/", "findTicketByPlaceResponse");
    private final static QName _FindTicketByPlace_QNAME = new QName("http://soap.api/", "findTicketByPlace");
    private final static QName _GetTicketsNumber_QNAME = new QName("http://soap.api/", "getTicketsNumber");
    private final static QName _GetTicketsNumberResponse_QNAME = new QName("http://soap.api/", "getTicketsNumberResponse");
    private final static QName _Ticket_QNAME = new QName("http://soap.api/", "ticket");
    private final static QName _GetAllSpotsNumberResponse_QNAME = new QName("http://soap.api/", "getAllSpotsNumberResponse");
    private final static QName _GetAllSpots_QNAME = new QName("http://soap.api/", "getAllSpots");
    private final static QName _GetAllTicketsResponse_QNAME = new QName("http://soap.api/", "getAllTicketsResponse");
    private final static QName _FindSpotByPlace_QNAME = new QName("http://soap.api/", "findSpotByPlace");
    private final static QName _GetAllSpotsResponse_QNAME = new QName("http://soap.api/", "getAllSpotsResponse");
    private final static QName _FindSpotByPlaceResponse_QNAME = new QName("http://soap.api/", "findSpotByPlaceResponse");
    private final static QName _GetAllTickets_QNAME = new QName("http://soap.api/", "getAllTickets");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllSpotsNumberResponse }
     * 
     */
    public GetAllSpotsNumberResponse createGetAllSpotsNumberResponse() {
        return new GetAllSpotsNumberResponse();
    }

    /**
     * Create an instance of {@link Ticket }
     * 
     */
    public Ticket createTicket() {
        return new Ticket();
    }

    /**
     * Create an instance of {@link FindTicketByPlaceResponse }
     * 
     */
    public FindTicketByPlaceResponse createFindTicketByPlaceResponse() {
        return new FindTicketByPlaceResponse();
    }

    /**
     * Create an instance of {@link FindTicketByPlace }
     * 
     */
    public FindTicketByPlace createFindTicketByPlace() {
        return new FindTicketByPlace();
    }

    /**
     * Create an instance of {@link GetTicketsNumber }
     * 
     */
    public GetTicketsNumber createGetTicketsNumber() {
        return new GetTicketsNumber();
    }

    /**
     * Create an instance of {@link GetTicketsNumberResponse }
     * 
     */
    public GetTicketsNumberResponse createGetTicketsNumberResponse() {
        return new GetTicketsNumberResponse();
    }

    /**
     * Create an instance of {@link GetAllSpotsNumber }
     * 
     */
    public GetAllSpotsNumber createGetAllSpotsNumber() {
        return new GetAllSpotsNumber();
    }

    /**
     * Create an instance of {@link FindSpotByPlaceResponse }
     * 
     */
    public FindSpotByPlaceResponse createFindSpotByPlaceResponse() {
        return new FindSpotByPlaceResponse();
    }

    /**
     * Create an instance of {@link GetAllTickets }
     * 
     */
    public GetAllTickets createGetAllTickets() {
        return new GetAllTickets();
    }

    /**
     * Create an instance of {@link GetAllTicketsResponse }
     * 
     */
    public GetAllTicketsResponse createGetAllTicketsResponse() {
        return new GetAllTicketsResponse();
    }

    /**
     * Create an instance of {@link FindSpotByPlace }
     * 
     */
    public FindSpotByPlace createFindSpotByPlace() {
        return new FindSpotByPlace();
    }

    /**
     * Create an instance of {@link GetAllSpotsResponse }
     * 
     */
    public GetAllSpotsResponse createGetAllSpotsResponse() {
        return new GetAllSpotsResponse();
    }

    /**
     * Create an instance of {@link GetAllSpots }
     * 
     */
    public GetAllSpots createGetAllSpots() {
        return new GetAllSpots();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSpotsNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllSpotsNumber")
    public JAXBElement<GetAllSpotsNumber> createGetAllSpotsNumber(GetAllSpotsNumber value) {
        return new JAXBElement<GetAllSpotsNumber>(_GetAllSpotsNumber_QNAME, GetAllSpotsNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindTicketByPlaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "findTicketByPlaceResponse")
    public JAXBElement<FindTicketByPlaceResponse> createFindTicketByPlaceResponse(FindTicketByPlaceResponse value) {
        return new JAXBElement<FindTicketByPlaceResponse>(_FindTicketByPlaceResponse_QNAME, FindTicketByPlaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindTicketByPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "findTicketByPlace")
    public JAXBElement<FindTicketByPlace> createFindTicketByPlace(FindTicketByPlace value) {
        return new JAXBElement<FindTicketByPlace>(_FindTicketByPlace_QNAME, FindTicketByPlace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTicketsNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getTicketsNumber")
    public JAXBElement<GetTicketsNumber> createGetTicketsNumber(GetTicketsNumber value) {
        return new JAXBElement<GetTicketsNumber>(_GetTicketsNumber_QNAME, GetTicketsNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTicketsNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getTicketsNumberResponse")
    public JAXBElement<GetTicketsNumberResponse> createGetTicketsNumberResponse(GetTicketsNumberResponse value) {
        return new JAXBElement<GetTicketsNumberResponse>(_GetTicketsNumberResponse_QNAME, GetTicketsNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ticket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "ticket")
    public JAXBElement<Ticket> createTicket(Ticket value) {
        return new JAXBElement<Ticket>(_Ticket_QNAME, Ticket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSpotsNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllSpotsNumberResponse")
    public JAXBElement<GetAllSpotsNumberResponse> createGetAllSpotsNumberResponse(GetAllSpotsNumberResponse value) {
        return new JAXBElement<GetAllSpotsNumberResponse>(_GetAllSpotsNumberResponse_QNAME, GetAllSpotsNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSpots }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllSpots")
    public JAXBElement<GetAllSpots> createGetAllSpots(GetAllSpots value) {
        return new JAXBElement<GetAllSpots>(_GetAllSpots_QNAME, GetAllSpots.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTicketsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllTicketsResponse")
    public JAXBElement<GetAllTicketsResponse> createGetAllTicketsResponse(GetAllTicketsResponse value) {
        return new JAXBElement<GetAllTicketsResponse>(_GetAllTicketsResponse_QNAME, GetAllTicketsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSpotByPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "findSpotByPlace")
    public JAXBElement<FindSpotByPlace> createFindSpotByPlace(FindSpotByPlace value) {
        return new JAXBElement<FindSpotByPlace>(_FindSpotByPlace_QNAME, FindSpotByPlace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSpotsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllSpotsResponse")
    public JAXBElement<GetAllSpotsResponse> createGetAllSpotsResponse(GetAllSpotsResponse value) {
        return new JAXBElement<GetAllSpotsResponse>(_GetAllSpotsResponse_QNAME, GetAllSpotsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSpotByPlaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "findSpotByPlaceResponse")
    public JAXBElement<FindSpotByPlaceResponse> createFindSpotByPlaceResponse(FindSpotByPlaceResponse value) {
        return new JAXBElement<FindSpotByPlaceResponse>(_FindSpotByPlaceResponse_QNAME, FindSpotByPlaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTickets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api/", name = "getAllTickets")
    public JAXBElement<GetAllTickets> createGetAllTickets(GetAllTickets value) {
        return new JAXBElement<GetAllTickets>(_GetAllTickets_QNAME, GetAllTickets.class, null, value);
    }

}
