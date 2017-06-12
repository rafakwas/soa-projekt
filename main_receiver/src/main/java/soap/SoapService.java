package soap;

import controllers.ReceiverBean;
import entity.Spot;
import repository.Repository;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebService
@Singleton
public class SoapService {
    private final static Logger LOGGER = Logger.getLogger(SoapService.class.toString());

    @Inject
    ReceiverBean receiverBean;

    @Inject
    Repository repository;

    @WebMethod
    public void notifyOccupation(Spot spot) {
        LOGGER.info("spot occupation receiver: " + spot);
        receiverBean.addNotification("Mock: spot " + spot.getPlace() + " occupied");
        repository.addSpot(spot);
    }

    @WebMethod
    public void notifyVacation(Integer id) {
        LOGGER.info("spot vacation receiver: " + id);
        receiverBean.addNotification("Mock: spot " + id + " abondoned");
        repository.removeSpot(id);
    }
}
