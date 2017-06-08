package browserfilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(filterName = "BrowserFilter", urlPatterns = { "*.xhtml" })
public class BrowserFilter implements Filter {
    private final static Logger LOGGER = Logger.getLogger(BrowserFilter.class.toString());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String userAgent = req.getHeader("User-Agent");
        LOGGER.info("Client user agent: " + userAgent);
        String ubuntuFirefox = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0";

        if (userAgent.equals(ubuntuFirefox)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/guard_module/public/browserBlocked.html");
        }
        else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {}
}