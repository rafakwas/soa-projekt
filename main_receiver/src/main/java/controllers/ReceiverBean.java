package controllers;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named("receiverbean")
public class ReceiverBean {
    private List<String> notifications = new ArrayList<>();

    public void addNotification(String info) {
        notifications.add(info);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
