package browserfilter;

import multiplesessions.SesssionFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
    }

    @Override
    public void destroy() {}
}
