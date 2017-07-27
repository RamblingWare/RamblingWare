package com.rant.action.manage.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.User;
import com.rant.model.UserAware;

/**
 * Edit User action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class EditUserAction extends ActionSupport
        implements
            UserAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private User user;

    // post parameters
    private User author;
    private String id;
    private String reqUri;

    // updated values
    private String name;
    private String uri;
    private String thumbnail;
    private String description;
    private String content;

    public String execute() {

        // /manage/editpost/user-name-goes-here
        // this allows blog posts to be shown without parameter arguments (i.e. ?uri=foobar&test=123
        // )
        String uriTemp = servletRequest.getRequestURI();
        if (reqUri == null && uriTemp.startsWith("/manage/edituser/")) {
            reqUri = Utils.removeBadChars(uriTemp.substring(17, uriTemp.length()));
        }

        if (servletRequest.getParameter("delete") != null) {
            // delete the user
            return deleteUser();
        } else if (servletRequest.getParameter("submitForm") != null) {
            // validate and save changes
            return editUser();
        } else {
            // user opened user to edit
            return openUser();
        }
    }

    private String openUser() {
        if (reqUri != null && reqUri.length() > 0) {
            // search in db for user by name
            try {
                author = Application.getDatabaseSource().getUser(reqUri);

                // was author found
                if (author != null) {

                    if (author.getUri().equals(user.getUri()) && !user.getRole().isUsersEdit()) {
                        addActionError("You do not have permission to edit your account.");
                        addActionMessage(
                                "Only certain roles can edit themselves. Contact your sysadmin or manager.");
                        System.out.println("User " + user.getUsername()
                                + " tried to edit themselves. Does not have permission.");
                        return ERROR;
                    }
                    if (!author.getUri().equals(user.getUri())
                            && !user.getRole().isUsersEditOthers()) {
                        addActionError("You do not have permission to edit other authors.");
                        addActionMessage(
                                "Only certain roles can edit other authors. Contact your sysadmin or manager.");
                        System.out.println("User " + user.getUsername() + " tried to edit a user ("
                                + reqUri + "). Does not have permission.");
                        return ERROR;
                    }

                    // set attributes
                    servletRequest.setAttribute("author", author);

                    System.out.println(
                            "User " + user.getUsername() + " opened author to edit: " + reqUri);
                    return Action.INPUT;
                } else {
                    System.err.println("Author '" + reqUri + "' not found. Please try again.");
                    return Action.NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("User '" + reqUri + "' not found. Please try again.");
            return Action.NONE;
        }

    }

    private String editUser() {

        // Validate each field
        if (name == null || name.trim().isEmpty()) {
            addActionError("Name was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit user. Name was empty.");
            return ERROR;
        }
        if (uri == null || uri.trim().isEmpty()) {
            addActionError("URI Name was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit user. URI was empty.");
            return ERROR;
        }
        if (thumbnail == null || thumbnail.trim().isEmpty()) {
            thumbnail = "";
        }
        if (description == null) {
            description = "";
        }
        if (content == null) {
            content = "";
        }
        if (content != null && content.length() > 12288) {
            addActionError(
                    "Post Content is too long. Character limit is 12,288. Please shorten the content.");
            System.out.println(user.getUsername() + " failed to edit user. Content too large.");
            return ERROR;
        }

        try {
            // check if uri exists or not
            User existingUser = Application.getDatabaseSource().getUser(uri);

            if (existingUser.getUri().equals(user.getUri()) && !user.getRole().isUsersEdit()) {
                addActionError("You do not have permission to edit your account.");
                addActionMessage(
                        "Only certain roles can edit themselves. Contact your sysadmin or manager.");
                System.out.println("User " + user.getUsername()
                        + " tried to edit themselves. Does not have permission.");
                return ERROR;
            }
            if (!existingUser.getUri().equals(user.getUri())
                    && !user.getRole().isUsersEditOthers()) {
                addActionError("You do not have permission to edit other authors.");
                addActionMessage(
                        "Only certain roles can edit other authors. Contact your sysadmin or manager.");
                System.out.println("User " + user.getUsername() + " tried to edit a user (" + reqUri
                        + "). Does not have permission.");
                return ERROR;
            }

            /*if (existingUser.get_Id() != id) {
                // URI was not unique. Please try again.
                addActionError(
                        "URI is not unique. Its being used by another author. Please change it, and try again.");
                System.out.println("URI was not unique.");
                return ERROR;
            }*/

            // save fields into object
            author = new User(uri);
            author.setName(name);
            author.setDescription(description);
            author.setThumbnail(thumbnail);
            author.setContent(content);

            // update author in database
            if (Application.getDatabaseSource().editUser(author)) {
                // Success
                System.out.println(
                        "User " + user.getUsername() + " saved changes to the author: " + uri);
                addActionMessage("Successfully saved changes to the author.");
                return SUCCESS;
            }
            {
                // failed to update
                addActionError("Oops. Failed to update author. Please try again.");
                System.out.println("Failed to update author. " + uri);
                return ERROR;
            }
        } catch (Exception e) {
            addActionError("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }

    }

    private String deleteUser() {

        // they've requested to delete a user
        try {
            User existingUser = Application.getDatabaseSource().getUser(uri);

            if (!existingUser.getUri().equals(user.getUri()) && !user.getRole().isUsersDelete()) {
                addActionError("You do not have permission to delete other authors.");
                addActionMessage(
                        "Only certain roles can delete other authors. Contact your sysadmin or manager.");
                System.out.println("User " + user.getUsername() + " tried to delete a user ("
                        + reqUri + "). Does not have permission.");
                return ERROR;
            }

            author = new User(id);
            if (Application.getDatabaseSource().deleteUser(author)) {
                // Success
                System.out.println("User " + user.getUsername() + " deleted user: " + reqUri);
                addActionMessage("The author was deleted!");
                return SUCCESS;
            } else {
                // failed to delete user
                addActionError("Oops. Failed to delete the user. Please try again.");
                return ERROR;
            }

        } catch (Exception e) {
            addActionError("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected HttpServletResponse servletResponse;

    protected HttpServletRequest servletRequest;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = Utils.removeAllSpaces(uri.trim().toLowerCase());
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = Utils.removeNonAsciiChars(thumbnail.trim());
    }

    public String getDescription() {
        return description.trim();
    }

    public void setDescription(String description) {
        this.description = Utils.removeNonAsciiChars(description.trim());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}