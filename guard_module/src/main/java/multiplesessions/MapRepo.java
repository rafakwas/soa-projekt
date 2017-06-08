package multiplesessions;

import javax.servlet.http.HttpSession;

public interface MapRepo {
    HttpSession put(String user, HttpSession httpSession);
    HttpSession get(String user);
    HttpSession remove(String user);
}
