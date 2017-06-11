package org.rw.action.manage;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.Post;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Edit Post action class
 * 
 * @author Austin Delamar
 * @date 5/30/2016
 */
public class EditPostAction extends ActionSupport
        implements
            UserAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Author user;

    // post parameters
    private Post post;
    private int id;
    private String uri;

    // updated values
    private String title;
    private String uriName;
    private String thumbnail;
    private String publishDate;

    private boolean visible;
    private boolean featured;

    private boolean hasBanner;
    private String banner;
    private String bannerCaption;

    private String tags;
    private String category;
    private String description;
    private String htmlContent;

    // taken uris and tags
    private ArrayList<String> usedUris;
    private ArrayList<String> usedTags;

    public String execute() {

        // get used variables
        usedTags = Application.getDatabaseSource().getArchiveTags();
        usedUris = Application.getDatabaseSource().getPostUris();

        // /manage/editpost/file-name-goes-here
        // this allows blog posts to be shown without parameter arguments (i.e. ?uri=foobar&test=123
        // )
        String uriTemp = servletRequest.getRequestURI().toLowerCase();
        if (uri == null && uriTemp.startsWith("/manage/editpost/")) {
            uri = Utils.removeBadChars(uriTemp.substring(17, uriTemp.length()));
        }

        if (servletRequest.getParameter("delete") != null) {
            // delete the post
            return deletePost();
        } else if (servletRequest.getParameter("submitForm") != null) {
            // validate and save changes
            return editPost();
        } else {
            // user opened post to edit
            return openPost();
        }
    }

    /**
     * Check if the user opened a post to edit.
     * 
     * @return INPUT if found, NONE if not, and ERROR if error
     */
    private String openPost() {
        if (uri != null && uri.length() > 0) {
            // search in db for post by title
            try {
                post = Application.getDatabaseSource().getPost(uri, true);

                // was post found
                if (post != null) {
                    // remove post URI from list
                    usedUris.remove(uri);

                    // set attributes
                    servletRequest.setAttribute("post", post);

                    System.out
                            .println("User " + user.getUsername() + " opened post to edit: " + uri);
                    return INPUT;
                } else {
                    System.err.println("Post '" + uri + "' not found. Please try again.");
                    return NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Post '" + uri + "' not found. Please try again.");
            return NONE;
        }
    }

    /**
     * Check if user submitted valid data for the post.
     * 
     * @return SUCCESS if saved, ERROR if error
     */
    private String editPost() {
        // Validate each field
        if (title == null || title.trim().isEmpty()) {
            addActionError("Title was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Title was empty.");
            return ERROR;
        }
        if (uriName == null || uriName.trim().isEmpty()) {
            addActionError("URI Name was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. URI was empty.");
            return ERROR;
        }
        if (thumbnail == null || thumbnail.trim().isEmpty()) {
            addActionError("Thumbnail was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Thumbnail was empty.");
            return ERROR;
        }
        if (description == null || description.trim().isEmpty()) {
            addActionError("Description was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Description was empty.");
            return ERROR;
        }
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            addActionError("Post Content was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Content was empty.");
            return ERROR;
        }
        if (htmlContent.length() > 12288) {
            addActionError(
                    "Post Content is too long. Character limit is 12,288. Please shorten the post.");
            System.out.println(user.getUsername() + " failed to edit post. Content too large.");
            return ERROR;
        }
        if (hasBanner && (banner == null || banner.trim().isEmpty())) {
            addActionError("Banner Image was empty. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Banner was empty.");
            return ERROR;
        }
        if (Utils.convertStringToDate(publishDate) == null) {
            addActionError("Publish Date was invalid. Please fill out all fields before saving.");
            System.out.println(user.getUsername() + " failed to edit post. Banner was empty.");
            return ERROR;
        }
        if (tags == null || tags.trim().isEmpty()) {
            tags = "none";
        }
        if (category == null || category.trim().isEmpty()) {
            category = "none";
        }

        // check that the URI is unique
        try {
            Post existingPost = Application.getDatabaseSource().getPost(uri, true);

            if (existingPost.getId() != id) {
                // URI was not unique. Please try again.
                addActionError(
                        "URI is not unique. Its being used by another post. Please change it, and try again.");
                System.out.println("URI was not unique.");
                return ERROR;
            }

            // save fields into object
            post = new Post(id);
            post.setUriName(uriName);
            post.setTitle(title);
            post.setAuthor(user);
            Calendar cal = Calendar.getInstance();
            cal.setTime(Utils.convertStringToDate(publishDate));
            post.setPublishDate(new java.sql.Date(cal.getTimeInMillis()));
            post.setVisible(visible);
            post.setFeatured(featured);
            post.setCategory(category);
            post.setBanner(banner);
            post.setBannerCaption(bannerCaption);
            post.setThumbnail(thumbnail);
            post.setDescription(description);
            post.setHtmlContent(htmlContent);

            // chop off [ ] if they added them
            tags = tags.replaceAll("\\[", "");
            tags = tags.replaceAll("\\]", "");
            System.out.println("Tags: '" + tags + "'");

            String[] tagsArray = tags.split(",");
            ArrayList<String> tagsList = new ArrayList<String>();
            for (String t : tagsArray) {
                if (!t.trim().isEmpty())
                    tagsList.add(t.trim());
            }
            post.setTags(tagsList);

            // update post in database
            if (Application.getDatabaseSource().editPost(post)) {
                // Success
                System.out.println(
                        "User " + user.getUsername() + " saved changes to the post: " + uri);
                addActionMessage("Successfully saved changes to the post.");
                return SUCCESS;
            }
            {
                // failed to update
                addActionError("Oops. Failed to update post. Please try again.");
                System.out.println("Failed to update post. " + uriName);
                return ERROR;
            }

        } catch (Exception e) {
            addActionError("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * Check if user can delete the post.
     * 
     * @return SUCCESS if deleted, ERROR if error
     */
    private String deletePost() {
        // they've requested to delete a post
        try {
            post = new Post(id);
            if (Application.getDatabaseSource().deletePost(post)) {
                // Success
                System.out.println("User " + user.getUsername() + " deleted post: " + uri);
                addActionMessage("The post was deleted!");
                return SUCCESS;
            } else {
                // failed to delete post
                addActionError("Failed to delete post: " + uri);
                return ERROR;
            }

        } catch (Exception e) {
            addActionError("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected HttpServletResponse servletResponse;

    protected HttpServletRequest servletRequest;

    @Override
    public void setUser(Author user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public String getUriName() {
        return uriName;
    }

    public void setUriName(String uriName) {
        this.uriName = Utils.removeAllSpaces(uriName.trim().toLowerCase());
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = Utils.removeNonAsciiChars(thumbnail.trim());
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean getHasBanner() {
        return hasBanner;
    }

    public void setHasBanner(boolean hasBanner) {
        this.hasBanner = hasBanner;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner.trim();
    }

    public String getBannerCaption() {
        return bannerCaption;
    }

    public void setBannerCaption(String bannerCaption) {
        this.bannerCaption = bannerCaption;
    }

    public String getTags() {
        return tags.trim();
    }

    public void setTags(String tags) {
        this.tags = Utils.removeNonAsciiChars(tags.trim());
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Utils.removeNonAsciiChars(category.trim());
    }

    public String getDescription() {
        return description.trim();
    }

    public void setDescription(String description) {
        this.description = Utils.removeNonAsciiChars(description.trim());
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public ArrayList<String> getUsedUris() {
        return usedUris;
    }

    public void setUsedUris(ArrayList<String> usedUris) {
        this.usedUris = usedUris;
    }

    public ArrayList<String> getUsedTags() {
        return usedTags;
    }

    public void setUsedTags(ArrayList<String> usedTags) {
        this.usedTags = usedTags;
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