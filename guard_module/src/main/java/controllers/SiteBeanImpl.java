package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Named(value = "sitebeanimpl")
@Local(SiteBean.class)
public class SiteBeanImpl implements SiteBean {
    public SiteBeanImpl() {
    }

    private List<String> notifications;

    @PostConstruct
    public void init() {
        notifications = new ArrayList<>();
    }

    @Override
    public void addNotification(String text) {
        notifications.add(text);
    }

    /*--------------------GETTERS & SETTERS --------------------------*/
    @Override
    public List<String> getNotifications() {
        return notifications;
    }
}
