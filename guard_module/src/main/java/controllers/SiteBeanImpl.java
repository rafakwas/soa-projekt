package controllers;

import entity.Message;
import message.MessageRepository;
import utils.SessionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
@Named(value = "sitebeanimpl")
@Local(SiteBean.class)
@DeclareRoles({"admin", "pool1", "pool2"})
public class SiteBeanImpl implements SiteBean {
    private final static Logger LOGGER = Logger.getLogger(SiteBeanImpl.class.toString());

    public SiteBeanImpl() {
    }

    @Inject
    MessageRepository messageRepository;

    /*--------------------GETTERS & SETTERS --------------------------*/
    @Override
    @RolesAllowed("admin")
    public List<Message> getNotifications() {
        return messageRepository.getMessages();
    }

    @Override
    @RolesAllowed({"pool1","pool2"})
    public List<Message> getFilteredNotifications(String pool) {
        List<Message> filteredList = new ArrayList<>();
        filteredList.addAll(getNotifications().stream().filter(message -> message.getData().contains(pool)).collect(Collectors.toList()));
        return filteredList;
    }
}
