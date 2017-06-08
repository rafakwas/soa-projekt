package controllers;

import entity.Message;

import java.util.List;

public interface SiteBean {
    List<Message> getNotifications();
    List<Message> getFilteredNotifications(String pool);
}
