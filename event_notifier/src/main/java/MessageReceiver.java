import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@MessageDriven(name = "MessageReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "project/RepositoryToNotifierQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MessageReceiver implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(MessageReceiver.class.toString());

    private final static String TICKET_EXPIRATION_MESSAGE = "Ticket expired. Place: ";
    private final static String TICKET_NOT_BOUGHT = "Ticket not bought. Place: ";
//    private final static List<String> MESSAGES_LIST = Arrays.asList(TICKET_EXPIRATION_MESSAGE,TICKET_NOT_BOUGHT);

    private final static Integer NUMBER_OF_PLACES = 30;

    private static final String REST_URL = "http://localhost:8080/guard_module/api/notification";
    private static final String DASHBOARD_URL = "http://localhost:8080/main_receiver/api/ticket";
    private Client client;

    public MessageReceiver() {}

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            try {
                final String text = ((TextMessage) msg).getText();
                LOGGER.info("MessageReceiver: " + text);
                final String converted_message = prepareMessage(text);
                procedure(converted_message);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void procedure(String message) {
        sendMessage(message);
        sendToDashboard(message);
    }

    private void sendMessage(String text) {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(REST_URL + "/message");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(text, MediaType.APPLICATION_JSON));
        LOGGER.info("message send on api rest");
        String message = response.readEntity(String.class);
        LOGGER.info("response: " + message);
    }
    private void sendToDashboard(String text) {
        client = ClientBuilder.newClient();
        WebTarget target = client.target(DASHBOARD_URL + "/notification");
        Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(text, MediaType.APPLICATION_JSON));
        LOGGER.info("message send to DASHBOARD");
        String message = response.readEntity(String.class);
        LOGGER.info("response: " + message);
    }
    private String prepareMessage(final String text) {
        LOGGER.info("prepareMessage from text: " + text);
        String[] chunks = text.split(":");
        Integer place = Integer.parseInt(chunks[1]);
        LOGGER.info("place: " + place);
        String prefix = null;
        StringBuilder message = new StringBuilder();
        if (place >= 0 && place < NUMBER_OF_PLACES/2) {// 0..14
            prefix = "pool1 - ";
        }
        else if(place > (NUMBER_OF_PLACES/2)+1 && place < NUMBER_OF_PLACES) {// 15..29
            prefix = "pool2 - ";
        }
        else {
            throw new RuntimeException("Incorrect spot number. Given " + place);
        }
        LOGGER.info("prefix : " + prefix);
        return message.append(prefix).append(text).toString();
    }
}
