package controllers;

import utils.SessionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Named(value = "sitebeanimpl")
@Local(SiteBean.class)
public class SiteBeanImpl implements SiteBean {
    private final static Logger LOGGER = Logger.getLogger(SiteBeanImpl.class.toString());

    public SiteBeanImpl() {
    }

    private List<String> notifications;

    @PostConstruct
    public void init() {
        LOGGER.info("notifications initalizied");
        notifications = new ArrayList<>();
    }

    @Override
    public void addNotification(String text) {
        LOGGER.info("notifications added: " + text);
        notifications.add(text);
    }

    @Override
    public boolean isUserAdmin() {
        return SessionUtils.getRequest().isUserInRole("ADMIN");
    }

    @Override
    public boolean isUserGuard() {
        return SessionUtils.getRequest().isUserInRole("GUARD");
    }



    /*--------------------GETTERS & SETTERS --------------------------*/
    @Override
    public List<String> getNotifications() {
        return notifications;
    }
}
