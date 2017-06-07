package security;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

@SuppressWarnings("serial")
@WebServlet("/SecuredServlet")
@ServletSecurity(@HttpConstraint(rolesAllowed = { "admin","pool1","pool2" }))
public class SecuredServlet extends HttpServlet {

    private static String PAGE_HEADER = "<html><head><title>guard-security</title></head><body>";
    private static String PAGE_FOOTER = "</body></html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/guard.xhtml").forward(req, resp);

        HttpSession session = req.getSession(false);
        session.setAttribute("username",req.getUserPrincipal().getName());
        /*BEZ MOŻLIWOŚCI WEJŚCIA BEZPOŚREDNIO DO GUARD JSF */
//        request.getRequestDispatcher("/WEB-INF/guard.xhtml").forward(request, response);

//        PrintWriter writer = resp.getWriter();
//        Principal principal = null;
//        String authType = null;
//        String remoteUser = null;
//        boolean isAdmin;
//        boolean isPool1;
//        boolean isPool2;
//
//        isAdmin = req.isUserInRole("admin");
//        isPool1 = req.isUserInRole("pool1");
//        isPool2 = req.isUserInRole("pool2");
//
//
//        // Get security principal
//        principal = req.getUserPrincipal();
//        // Get user name from login principal
//        remoteUser = req.getRemoteUser();
//        // Get authentication type
//        authType = req.getAuthType();
//
//
//
//        writer.println(PAGE_HEADER);
//        writer.println("<h1>" + "Successfully called Secured Servlet " + "</h1>");
//        writer.println("<p>" + "Principal  : " + principal.getName() + "</p>");
//        writer.println("<p>" + "Remote User : " + remoteUser + "</p>");
//        writer.println("<p>" + "isAdmin: " + String.valueOf(isAdmin) + "</p>");
//        writer.println("<p>" + "isPool1: " + String.valueOf(isPool1) + "</p>");
//        writer.println("<p>" + "isPool2: " + String.valueOf(isPool2) + "</p>");
//
//        writer.println("<p>" + "Authentication Type : " + authType + "</p>");
//        writer.println(PAGE_FOOTER);
//        writer.close();
    }
}
