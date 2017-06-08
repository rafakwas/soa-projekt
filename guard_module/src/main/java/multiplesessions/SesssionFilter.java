package multiplesessions;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SessionFilter", urlPatterns = { "*.xhtml" })
public class SesssionFilter implements Filter {
    private final static Logger LOGGER = Logger.getLogger(SesssionFilter.class.toString());

    @Inject
    MapRepo sessions;

//    private Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Principal principal = request.getUserPrincipal();
        HttpSession session = request.getSession();

        if (principal != null && session.getAttribute("THE_PRINCIPAL") == null) {

            // update the current session
            session.setAttribute("THE_PRINCIPAL", session);

            // get the username from the principal
            // (assumes you using container based authentication)
            String username = principal.getName();

            // invalidate previous session if it exists
            HttpSession s = sessions.get(username);

            if (s != null) {
                LOGGER.info("username: " + username + " session: " + s.getId());
            }
            else {
                LOGGER.info("usename " + username + " session is null");
            }


            if (s != null) {
                session.invalidate();
                //trying to log in as same username. should inform
                response.sendRedirect("/guard_module/public/alreadyLogged.html");
            }



            // register this session as the one for the user
            sessions.put(username, session);

        }

        chain.doFilter(servletRequest, servletResponse);

    }

}