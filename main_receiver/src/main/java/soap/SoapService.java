package soap;

import controllers.ReceiverBean;
import model.Spot;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebService
@Stateless
public class SoapService {
    private final static Logger LOGGER = Logger.getLogger(SoapService.class.toString());

    @Inject
    ReceiverBean receiverBean;

    @WebMethod
    public void notifyOccupation(Spot spot) {
        LOGGER.info("spot occupation receiver: " + spot);
        receiverBean.addNotification("miejsce zajete");
    }

    @WebMethod
    public void notifyVacation(Integer id) {
        LOGGER.info("spot vacation receiver: " + id);
        receiverBean.addNotification("miejsce " + id + " opuszczone");
    }

}
