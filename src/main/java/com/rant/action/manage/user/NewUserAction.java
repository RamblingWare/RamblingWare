package com.rant.action.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.Author;
import com.rant.model.AuthorAware;

/**
 * New User action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class NewUserAction extends ActionSupport
        implements
            AuthorAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Author user;

    private String username;
    private String uriName;
    private String email;
    private String name;
    private String password;
    private String password2;
    private String role;

    @Override
    public String execute() {

        System.out.println("/manage/newuser Requested");

        if (servletRequest.getParameter("submitForm") != null) {
            // submitted new user!
            return newUser();
        }

        // they opened the form
        System.out.println("User " + user.getUsername() + " opened new user.");
        return INPUT;
    }

    private String newUser() {
        // Validate each field
        if (name == null || name.trim().isEmpty()) {
            addActionError("Name was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Name was empty.");
            return ERROR;
        }
        if (uriName == null || uriName.trim().isEmpty()) {
            addActionError("URI Name was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. URI was empty.");
            return ERROR;
        }
        if (username == null || username.trim().isEmpty()) {
            addActionError("Username was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Username was empty.");
            return ERROR;
        }
        if (email == null || email.trim().isEmpty()) {
            addActionError("Email was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Email was empty.");
            return ERROR;
        }
        if (!Utils.isValidEmail(email)) {
            addActionError("Invalid Email Address. Please try again.");
            System.out.println(
                    user.getUsername() + " tried to create user with an invalid Email Address.");
            return ERROR;
        }

        // valid passwords?
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            addActionError("Password was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Password was empty.");
            return ERROR;
        }
        if (password.length() < 8) {
            addActionError("Password needs to be at least 8 characters long. Please try again.");
            System.out.println(user.getUsername() + " tried to create user with a short password.");
            return ERROR;
        }
        if (!password.equals(password2)) {
            // passwords do not match
            addActionError("Passwords do not match. Please try again.");
            System.out.println(
                    user.getUsername() + " tried to create user with non-matching passwords.");
            return ERROR;
        }

        if (role == null || role.trim().isEmpty()) {
            addActionError("Role was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Role was empty.");
            return ERROR;
        }
        if (!role.equalsIgnoreCase("author") && !role.equalsIgnoreCase("editor")
                && !role.equalsIgnoreCase("owner") && !role.equalsIgnoreCase("admin")) {
            addActionError("Role was invalid. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to create user. Role was invalid.");
            return ERROR;
        }

        try {
            // check that the URI is unique
            Author author = Application.getDatabaseSource().getAuthor(uriName, true);

            if (author != null) {
                // URI was not unique. Please try again.
                addActionError(
                        "URI is not unique. Its being used by another author. Please change it, and try again.");
                System.out.println("URI was not unique.");
                return ERROR;
            }

            // salt and hash the password
            password = Hash.create(password, Type.PBKDF2_SHA256);

            // add new user
            Author newUser = new Author(-1);
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setUriName(uriName);
            newUser.setPassword(password);
            newUser.setThumbnail("/img/placeholder-200.png");

            switch (role) {
                case "author" :
                    newUser.setRole(0);
                    break;
                case "editor" :
                    newUser.setRole(1);
                    break;
                case "owner" :
                    newUser.setRole(2);
                    break;
                case "admin" :
                    newUser.setRole(3);
                    break;
                default :
                    newUser.setRole(0);
                    break;
            }

            // insert into database
            newUser = Application.getDatabaseSource().newUser(newUser);

            if (newUser.getId() != -1) {
                // Success
                addActionMessage("Successfully created new Author: " + username);
                System.out
                        .println("User " + user.getUsername() + " created new author: " + username);
                return SUCCESS;
            } else {
                // failed to insert new user
                addActionError("Failed to create new author. ");
                System.out.println(user.getUsername() + " failed to create new user. " + username);
                return ERROR;
            }
        } catch (Exception e) {
            addActionError("Failed to create new user: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    @Override
    public void setUser(Author user) {
        this.user = user;
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Utils.removeBadChars(username);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Utils.removeNonAsciiChars(name);
    }

    public String getUriName() {
        return uriName;
    }

    public void setUriName(String uriName) {
        this.uriName = Utils.removeAllSpaces(uriName.trim().toLowerCase());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}