package com.bk.bean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bk.model.Customer;
import com.bk.model.EmailAddress;
import com.bk.service.CustomerService;
import com.bk.util.Message;
import com.bk.util.PasswordHash;

/**
 * @author Andrei Petraru
 * Date: 1/16/13
 */

@Component
@Scope("request")
public class RegisterBean {

    private String username;
    private String password;
    private String email;

    @Autowired
    private CustomerService customerService;

    public void register() {

        if (checkExistingUser()) {
            return;
        }

        if (checkExistingEmail()) {
            return;
        }

        String pass = hashPassword();
        if (pass == null) {
            return;
        }

        Customer newCustomer = new Customer();
        newCustomer.setUsername(username);
        newCustomer.setPassword(pass);
        newCustomer.setEmailAddress(new EmailAddress(email));

        if (customerService.save(newCustomer) != null) {
            addSuccessMessage();
            clearFields();
        } else {
            addErrorMessage();
        }
    }

    private boolean checkExistingUser() {
        Customer existingCustomer = customerService.findByUsername(username);
        if (existingCustomer == null) {
            return false;
        }
        String existingUsername = existingCustomer.getUsername();
        if (StringUtils.equalsIgnoreCase(username, existingUsername)) {
            Message.addMessage("registerFormId:registerUsernameId", "Username already taken", FacesMessage.SEVERITY_ERROR);
            return true;
        }
        return false;
    }

    public boolean checkExistingEmail() {
        Customer existingCustomer = customerService.findByEmailAddress(new EmailAddress(email));
        if (existingCustomer == null) {
            return false;
        }
        EmailAddress existingEmail = existingCustomer.getEmailAddress();
        if (StringUtils.equalsIgnoreCase(email, existingEmail.toString())) {
            Message.addMessage("registerFormId:registerEmailId", "Email already taken", FacesMessage.SEVERITY_ERROR);
            return true;
        }
        return false;
    }

    private String hashPassword() {
        try {
            return PasswordHash.hash(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage();
            return null;
        }
    }

    private void addErrorMessage() {
        Message.addMessage("registerFormId:registerButtonId", "Registration failed !", FacesMessage.SEVERITY_ERROR);
    }

    private void addSuccessMessage() {
        Message.addMessage("registerFormId:registerButtonId", "Registration successful !", FacesMessage.SEVERITY_INFO);
    }

    private void clearFields() {
        username = "";
        password = "";
        email = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
