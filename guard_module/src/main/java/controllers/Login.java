package controllers;

import entity.User;
import utils.SessionUtils;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;


@ManagedBean
@SessionScoped
@DeclareRoles({"admin", "pool1", "pool2"})
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
//
//    public String validateUsernamePassword() {
//        boolean valid = checkCredentials(user, pwd);
//        if (valid) {
//            HttpSession session = SessionUtils.getSession();
//            session.setAttribute("username", user);
//            return "guard";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(
//                    null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//                            "Incorrect Username and Passowrd",
//                            "Please enter correct username and Password"));
//            return "login";
//        }
//    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        // TODO usuwanie z singltonowego beana
        return "SecureServlet";
    }

    @RolesAllowed("admin")
    public List<User> getUserList() {
        String hql = "from User ";
        javax.persistence.Query query = entityManager.createQuery(hql);
        List<User> list = query.getResultList();
        return list;
    }

    public String changeUserPassword(Integer id) {
        return "modify_passwords";
    }

}