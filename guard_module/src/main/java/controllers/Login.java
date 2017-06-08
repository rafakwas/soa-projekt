package controllers;

import entity.User;
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

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/public/index?faces-redirect=true";
    }

    @Transactional
    public Integer getUserId() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        String username = principal.getName();
        LOGGER.info("principal name: " + username);
        Session session = entityManager.unwrap(Session.class);
        User user = (User)session.createQuery("from User where username = :username")
                .setParameter("username",username)
                .uniqueResult();
        if(user == null) {
            LOGGER.info("NO USER FOUND :( with username " + username);
        }
        else {
            LOGGER.info("Found id : " + user.getId());
        }
        return user.getId();
    }

    @RolesAllowed("admin")
    public List<User> getUserList() {
        String hql = "from User ";
        javax.persistence.Query query = entityManager.createQuery(hql);
        List<User> list = query.getResultList();
        return list;
    }

    public void changeUserPassword(Integer id) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        req.setAttribute("user_id",id);
        req.getRequestDispatcher("/secured/password_change.xhtml").forward(req, resp);
    }

    public void redirectUserToPasswordChange() throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        req.setAttribute("user_id",getUserId());
        req.getRequestDispatcher("/secured/password_change.xhtml").forward(req, resp);
    }

    @Transactional
    public String submitNewPassword() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String param = parameterMap.get("user_id");
        Integer id = Integer.parseInt(param);
        String newHashedPassword = Util.createPasswordHash("MD5",
                Util.BASE64_ENCODING,
                null,
                null,
                pwd);

        Session session = entityManager.unwrap(Session.class);
        session.createQuery("update User set password = :password where id = :id")
                .setParameter("password",newHashedPassword)
                .setParameter("id",id).executeUpdate();

        return "/secured/guard.xhtml";
    }



}