package message;

import entity.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MessageRepository {
    List<Message> getMessages();
    void addMessage(String message);
}
