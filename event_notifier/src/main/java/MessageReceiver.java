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
import java.util.logging.Logger;

@MessageDriven(name = "MessageReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "project/RepositoryToNotifierQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MessageReceiver implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(MessageReceiver.class.toString());

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
                sendMessage(text);
                sendToDashboard(text);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
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
}
