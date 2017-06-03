package controllers;

import java.util.List;

public interface SiteBean {
    void addNotification(String text);
    List<String> getNotifications();
}
