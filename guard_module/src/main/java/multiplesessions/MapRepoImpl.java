package multiplesessions;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Local(MapRepo.class)
public class MapRepoImpl implements MapRepo {

    private Map<String,HttpSession> sessions = new HashMap<>();

    @Override
    public HttpSession put(String user, HttpSession httpSession) {
        return sessions.put(user,httpSession);
    }

    @Override
    public HttpSession get(String user) {
        return sessions.get(user);
    }

    @Override
    public HttpSession remove(String user) {
        return sessions.remove(user);
    }
}
