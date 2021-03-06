package controllers;

import entity.User;
import multiplesessions.MapRepo;
import org.jboss.security.auth.spi.Util;
import utils.SessionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



@ManagedBean
@SessionScoped
@DeclareRoles({"admin", "pool1", "pool2"})
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Login implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(Login.class.toString());

    private static final long serialVersionUID = 1094801825228386363L;

    @Inject
    MapRepo sessions;

    @PersistenceContext(unitName = "guardunit")
    private EntityManager entityManager;

    private String pwd;
    private String msg;
    private String user;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String logout() {
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        sessions.remove(username);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/public/index?faces-redirect=true";
    }

    @RolesAllowed("admin")
    public List<User> getUserList() {
        String hql = "from User ";
        javax.persistence.Query query = entityManager.createQuery(hql);
        List<User> list = query.getResultList();
        return list;
    }

    public String changeUserPassword(String username) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        req.setAttribute("username",username);
        return "/secured/password_change.xhtml";
    }

    public String redirectUserToPasswordChange() throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        req.setAttribute("username",username);
//        req.getRequestDispatcher("/secured/password_change.xhtml").forward(req, resp);
        return "/secured/password_change.xhtml";
    }

    @Transactional
    public String submitNewPassword() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String username = parameterMap.get("username");
        String newHashedPassword = Util.createPasswordHash("MD5",
                Util.BASE64_ENCODING,
                null,
                null,
                pwd);

        LOGGER.info("submitNewPassoword: new password " + newHashedPassword + " for user " + username);
        Session session = entityManager.unwrap(Session.class);
        session.createQuery("update User set password = :password where username = :username")
                .setParameter("password",newHashedPassword)
                .setParameter("username",username).executeUpdate();
        LOGGER.info("should get submited....");

        return "/secured/guard.xhtml";
    }



}