package message;

import entity.Message;

import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static javax.ejb.LockType.WRITE;
import static javax.ejb.LockType.READ;

@Stateless
@Local(MessageRepository.class)
public class MessageRepositoryImpl implements MessageRepository{

    @PersistenceContext(unitName = "guardunit")
    private EntityManager em;

    @Override
    @Lock(READ)
    public List<Message> getMessages() {
        String hql = "from Message";
        javax.persistence.Query query = em.createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Lock(WRITE)
    public void addMessage(String data) {
        Message message = new Message();
        message.setData(data);
        em.persist(message);
    }
}
