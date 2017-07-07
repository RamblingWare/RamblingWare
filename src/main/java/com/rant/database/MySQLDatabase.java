package com.rant.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

import com.rant.config.Application;
import com.rant.model.Author;
import com.rant.model.Database;
import com.rant.model.Post;
import com.rant.model.Role;

public class MySQLDatabase extends DatabaseSource {

    public MySQLDatabase(Database database) {
        super(database);
    }

    /**
     * Obtains a connection to the MySQL DB if possible.
     * 
     * @return Connection
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return Application.getBasicDatabaseSource().getConnection();
    }

    /**
     * Quietly closes each object if they're not closed already. Checks for nulls along the way.
     * 
     * @param rs
     *            ResultSet
     * @param ps
     *            PreparedStatement
     * @param conn
     *            Connection
     */
    private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Post getPost(String uri, boolean includeHidden) {

        Post post = null;
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select p.*, u.name, u.uri_name as 'authorUri', u.description as 'authorDesc',"
                    + " u.thumbnail as 'authorThumbnail' "
                    + "from posts p left join users u on p.user_id = u.user_id "
                    + "where p.uri_name = ?";

            if (!includeHidden) {
                query += " and p.is_visible <> 0";
            }

            conn = getConnection();
            pt = conn.prepareStatement(query);
            pt.setString(1, uri);
            rs = pt.executeQuery();

            if (rs.next()) {
                // get the post properties
                post = new Post(rs.getInt("post_id"));
                post.setUriName(rs.getString("uri_name"));
                post.setTitle(rs.getString("title"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setModifyDate(rs.getDate("modify_date"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setVisible(rs.getInt("is_visible") > 0);
                post.setFeatured(rs.getInt("is_featured") > 0);
                post.setCategory(rs.getString("category"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setBanner(rs.getString("banner"));
                post.setBannerCaption(rs.getString("banner_caption"));
                post.setHtmlContent(rs.getString("html_content"));
                post.setDescription(rs.getString("description"));

                Author author = new Author(rs.getInt("user_id"));
                author.setName(rs.getString("name"));
                author.setUriName(rs.getString("authorUri"));
                author.setThumbnail(rs.getString("authorThumbnail"));
                author.setDescription(rs.getString("authorDesc"));
                post.setAuthor(author);
                close(rs, pt, null);

                // get post tags - there could be more than 1
                pt = conn.prepareStatement("select * from tags where post_id = ?");
                pt.setInt(1, post.getId());
                rs = pt.executeQuery();
                ArrayList<String> tags = new ArrayList<String>();
                while (rs.next()) {
                    tags.add(rs.getString("tag_name"));
                }
                post.setTags(tags);
                close(rs, pt, null);

                // get post view count
                pt = conn.prepareStatement("select * from views where post_id = ?");
                pt.setInt(1, post.getId());
                rs = pt.executeQuery();
                if (rs.next()) {
                    post.setRawViews(rs.getLong("count"));
                    post.setSessionViews(rs.getLong("session"));
                } else {
                    post.setRawViews(0);
                    post.setSessionViews(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }

        return post;
    }

    @Override
    public Post newPost(Post post) {

        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // save fields into database
            pt = conn.prepareStatement(
                    "insert into posts (user_id,title,uri_name,publish_date,is_visible,is_featured,category,thumbnail,"
                            + "banner,banner_caption,description,html_content) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            pt.setInt(1, post.getAuthor().getId());
            pt.setString(2, post.getTitle());
            pt.setString(3, post.getUriName());
            Calendar cal = Calendar.getInstance();
            cal.setTime(post.getPublishDate());
            pt.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
            pt.setInt(5, post.isVisible() ? 1 : 0);
            pt.setInt(6, post.isFeatured() ? 1 : 0);
            pt.setString(7, post.getCategory());
            pt.setString(8, post.getThumbnail());
            pt.setString(9, post.getBanner());
            pt.setString(10, post.getBannerCaption());
            pt.setString(11, post.getDescription());
            pt.setString(12, post.getHtmlContent());

            if (pt.execute()) {
                // failed to insert
                throw new Exception("Oops. Failed to create new post. Please try again.");
            }
            close(null, pt, null);

            // get the post_id and add the tags
            pt = conn.prepareStatement("select post_id from posts where uri_name = ?");
            pt.setString(1, post.getUriName());
            rs = pt.executeQuery();
            rs.next();
            post.setId(rs.getInt(1));
            close(rs, pt, null);

            String qry = "insert into tags (post_id,tag_name) values ";
            for (String tag : post.getTags()) {
                qry += "(" + post.getId() + ",'" + tag + "'),";
            }
            qry = qry.substring(0, qry.length() - 1); // remove last comma

            // insert tags
            pt = conn.prepareStatement(qry);
            int r = pt.executeUpdate();

            if (r == 0) {
                // error inserting tags
                throw new Exception(
                        "Oops. Failed to add tags to the new post. Please try adding them later.");
            }

            // done
            conn.commit();
            return post;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return null;
        } finally {
            close(rs, pt, conn);
        }
    }

    @Override
    public boolean editPost(Post post) {

        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String update = "update posts set user_id = ?,title = ?,uri_name = ?,"
                    + "modify_date = CURRENT_TIMESTAMP,publish_date = ?,is_visible = ?,"
                    + "is_featured = ?,category = ?,thumbnail = ?,description = ?,"
                    + "banner = ?,banner_caption = ?,html_content = ? where post_id = "
                    + post.getId();
            pt = conn.prepareStatement(update);
            pt.setInt(1, post.getAuthor().getId());
            pt.setString(2, post.getTitle());
            pt.setString(3, post.getUriName());
            Calendar cal = Calendar.getInstance();
            cal.setTime(post.getPublishDate());
            pt.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
            pt.setInt(5, post.isVisible() ? 1 : 0);
            pt.setInt(6, post.isFeatured() ? 1 : 0);
            pt.setString(7, post.getCategory());
            pt.setString(8, post.getThumbnail());
            pt.setString(9, post.getDescription());
            pt.setString(10, post.getBanner());
            pt.setString(11, post.getBannerCaption());
            pt.setString(12, post.getHtmlContent());

            if (pt.execute()) {
                // failed to update post
                throw new Exception("Oops. Failed to update the post. Please try again.");
            }
            close(null, pt, null);

            // remove old tags
            pt = conn.prepareStatement("delete from tags where post_id = ?");
            pt.setInt(1, post.getId());
            int r2 = pt.executeUpdate();

            if (r2 == 0) {
                throw new Exception("Oops. Failed to delete the post tags. Please try again.");
            }
            close(null, pt, null);

            // insert new tags
            String qry = "insert into tags (post_id,tag_name) values ";
            for (String tag : post.getTags()) {
                qry += "(" + post.getId() + ",'" + tag + "'),";
            }
            qry = qry.substring(0, qry.length() - 1); // remove last comma

            // insert tags
            pt = conn.prepareStatement(qry);
            int r3 = pt.executeUpdate();

            if (r3 == 0) {
                // error inserting tags
                throw new Exception(
                        "Oops. Failed to add tags to the post. Please try adding them later.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public boolean deletePost(Post post) {

        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pt = conn.prepareStatement("delete from posts where post_id = ?");
            pt.setInt(1, post.getId());
            int r = pt.executeUpdate();

            if (r == 0) {
                throw new Exception("Oops. Failed to delete the post. Please try again.");
            }
            close(null, pt, null);

            pt = conn.prepareStatement("delete from tags where post_id = ?");
            pt.setInt(1, post.getId());
            int r2 = pt.executeUpdate();

            if (r2 == 0) {
                throw new Exception("Oops. Failed to delete the post tags. Please try again.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public Author getAuthor(String uri, boolean includeHidden) {

        Author author = null;
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {            
            String query = "select u.*, r.name as 'name2', r.description as 'desc2', r.* from users u inner join roles r on r.role_id = u.role where u.uri_name = ?";

            if (!includeHidden) {
                query += " and r.is_public <> 0 ";
            }

            conn = getConnection();
            pt = conn.prepareStatement(query);
            pt.setString(1, uri);
            rs = pt.executeQuery();

            if (rs.first()) {
                // get the author properties
                author = new Author(rs.getInt("user_id"));
                author.setName(rs.getString("name"));
                author.setUsername(rs.getString("username"));
                author.setUriName(rs.getString("uri_name"));
                author.setThumbnail(rs.getString("thumbnail"));
                author.setDescription(rs.getString("description"));
                author.setHtmlContent(rs.getString("html_content"));
                author.setCreateDate(rs.getDate("create_date"));
                author.setModifyDate(rs.getDate("modify_date"));
                author.setLastLoginDate(rs.getDate("last_login_date"));
                author.setEmail(rs.getString("email"));
                
                // get role properties
                Role role = new Role(rs.getInt("role"));
                role.setName(rs.getString("name2"));
                role.setDescription(rs.getString("desc2"));
                role.setPublic(rs.getBoolean("is_public"));
                role.setPostsCreate(rs.getBoolean("posts_create"));
                role.setPostsEdit(rs.getBoolean("posts_edit_own"));
                role.setPostsEditOthers(rs.getBoolean("posts_edit_others"));
                role.setPostsSeeHidden(rs.getBoolean("posts_see_hidden"));
                role.setPostsDelete(rs.getBoolean("posts_delete"));
                role.setUsersCreate(rs.getBoolean("users_create"));
                role.setUsersEdit(rs.getBoolean("users_edit_own"));
                role.setUsersEditOthers(rs.getBoolean("users_edit_others"));
                role.setUsersDelete(rs.getBoolean("users_delete"));
                role.setRolesCreate(rs.getBoolean("roles_create"));
                role.setRolesEdit(rs.getBoolean("roles_edit"));
                role.setRolesDelete(rs.getBoolean("roles_delete"));
                role.setPagesCreate(rs.getBoolean("pages_create"));
                role.setPagesEdit(rs.getBoolean("pages_edit"));
                role.setPagesDelete(rs.getBoolean("pages_delete"));
                role.setCommentsCreate(rs.getBoolean("comments_create"));
                role.setCommentsEdit(rs.getBoolean("comments_edit_own"));
                role.setCommentsEditOthers(rs.getBoolean("comments_edit_others"));
                role.setCommentsDelete(rs.getBoolean("comments_delete"));
                role.setSettingsCreate(rs.getBoolean("settings_create"));
                role.setSettingsEdit(rs.getBoolean("settings_edit"));
                role.setSettingsDelete(rs.getBoolean("settings_delete"));
                author.setRole(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }

        return author;
    }

    @Override
    public boolean editAuthor(Author author) {

        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String update = "update users set name = ?,uri_name = ?,"
                    + "modify_date = CURRENT_TIMESTAMP,thumbnail = ?,description = ?,"
                    + "html_content = ? where user_id = ?";
            pt = conn.prepareStatement(update);
            pt.setString(1, author.getName());
            pt.setString(2, author.getUriName());
            pt.setString(3, author.getThumbnail());
            pt.setString(4, author.getDescription());
            pt.setString(5, author.getHtmlContent());
            pt.setInt(6, author.getId());

            if (pt.execute()) {
                // failed to update user
                throw new Exception("Oops. Failed to update the user. Please try again.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public boolean deleteAuthor(Author author) {

        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pt = conn.prepareStatement("delete from users where user_id = ?");
            pt.setInt(1, author.getId());
            int r = pt.executeUpdate();

            if (r == 0) {
                throw new Exception("Oops. Failed to delete the user. Please try again.");
            }
            close(null, pt, null);

            pt = conn.prepareStatement("delete from passwords where user_id = ?");
            pt.setInt(1, author.getId());
            int r2 = pt.executeUpdate();

            if (r2 == 0) {
                throw new Exception("Oops. Failed to delete the user. Please try again.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public ArrayList<Post> getArchiveFeatured() {

        ArrayList<Post> archiveFeatured = new ArrayList<Post>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select p.post_id, p.title, p.uri_name, p.publish_date, p.thumbnail, p.description "
                    + "from posts p where p.is_visible <> 0 and p.is_featured <> 0 "
                    + "order by p.create_date desc limit 2";
            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();

            while (rs.next()) {
                // get the post properties
                Post post = new Post(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setUriName(rs.getString("uri_name"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setDescription(rs.getString("description"));
                post.setThumbnail(rs.getString("thumbnail"));

                // add to results list
                archiveFeatured.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return archiveFeatured;
    }

    @Override
    public ArrayList<String> getArchiveYears() {

        ArrayList<String> archiveYears = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select YEAR(create_date) as year, COUNT(*) as count "
                    + "from posts where is_visible <> 0 group by YEAR(create_date) "
                    + "order by YEAR(create_date) desc";
            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();

            while (rs.next()) {
                // get year and count
                int year = rs.getInt("year");
                int count = rs.getInt("count");
                archiveYears.add(year + " (" + count + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return archiveYears;
    }

    @Override
    public ArrayList<String> getArchiveCategories() {

        ArrayList<String> archiveCategories = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select p.category, COUNT(*) as count "
                    + "from posts p where is_visible <> 0 "
                    + "group by category order by p.category asc";
            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();

            while (rs.next()) {
                // add to results list
                int count = rs.getInt("count");
                archiveCategories.add(rs.getString("category") + " (" + count + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return archiveCategories;
    }

    @Override
    public ArrayList<String> getArchiveTags() {

        ArrayList<String> archiveTags = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select t.tag_name, COUNT(*) as count " + "from tags t "
                    + "inner join posts p on t.post_id = p.post_id where p.is_visible <> 0 "
                    + "group by t.tag_name order by COUNT(*) desc, t.tag_name";
            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();

            while (rs.next()) {
                // get tag name and count
                String tag = rs.getString("tag_name");
                int count = rs.getInt("count");
                archiveTags.add(tag + " (" + count + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return archiveTags;
    }

    @Override
    public ArrayList<String> getPostUris() {

        ArrayList<String> uris = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            String query = "select p.uri_name from posts p order by p.uri_name desc";
            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();

            while (rs.next()) {
                // add to results list
                uris.add(rs.getString("uri_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return uris;
    }

    @Override
    public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {

        ArrayList<Post> posts = new ArrayList<Post>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            int offset = (page - 1) * limit;
            String query = "select p.* from posts p ";

            if (!includeHidden) {
                query += "where is_visible <> 0 ";
            }

            query += "order by p.create_date desc limit " + limit + " offset " + offset;

            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();
            while (rs.next()) {

                // get the post properties
                Post post = new Post(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setUriName(rs.getString("uri_name"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setDescription(rs.getString("description"));
                // post.setHtmlContent(rs.getString("html_content"));
                post.setVisible(rs.getInt("is_visible") == 1);
                post.setFeatured(rs.getInt("is_featured") == 1);
                post.setCategory(rs.getString("category"));
                post.setModifyDate(rs.getDate("modify_date"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setBanner(rs.getString("banner"));
                post.setBannerCaption(rs.getString("banner_caption"));
                post.setAuthor(new Author(rs.getInt("user_id")));

                // add to results list
                posts.add(post);
            }
            close(rs, pt, null);

            // gather tags, author, and view count
            for (Post p : posts) {
                pt = conn.prepareStatement("select t.* from tags t where t.post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                // get this post's tags - there could be more than 1
                ArrayList<String> post_tags = new ArrayList<String>();
                while (rs.next()) {
                    post_tags.add(rs.getString("tag_name"));
                }
                close(rs, pt, null);
                p.setTags(post_tags);

                // get post's author
                pt = conn.prepareStatement(
                        "select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, "
                                + "a.create_date, a.modify_date from users a where a.user_id = ?");
                pt.setInt(1, p.getAuthor().getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    // get the user properties
                    Author author = new Author(rs.getInt("user_id"));
                    author.setUriName(rs.getString("uri_name"));
                    author.setName(rs.getString("name"));
                    author.setCreateDate(rs.getDate("create_date"));
                    author.setDescription(rs.getString("description"));
                    author.setModifyDate(rs.getDate("modify_date"));
                    author.setThumbnail(rs.getString("thumbnail"));
                    p.setAuthor(author);
                } else {
                    p.getAuthor().setName("Anonymous");
                    p.getAuthor().setUriName("anonymous");
                }
                close(rs, pt, null);

                // get post's view count
                pt = conn.prepareStatement("select * from views where post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    p.setRawViews(rs.getLong("count"));
                    p.setSessionViews(rs.getLong("session"));
                } else {
                    p.setRawViews(0);
                    p.setSessionViews(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return posts;
    }

    @Override
    public ArrayList<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden) {

        ArrayList<Post> posts = new ArrayList<Post>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            int offset = (page - 1) * limit;
            String query = "select p.* from posts p where category = '" + category + "'";

            if (!includeHidden) {
                query += " and p.is_visible <> 0 ";
            }
            query += "order by p.create_date desc limit " + limit + " offset " + offset;

            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();
            while (rs.next()) {

                // get the post properties
                Post post = new Post(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setUriName(rs.getString("uri_name"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setDescription(rs.getString("description"));
                // post.setHtmlContent(rs.getString("html_content"));
                post.setVisible(rs.getInt("is_visible") == 1);
                post.setFeatured(rs.getInt("is_featured") == 1);
                post.setCategory(rs.getString("category"));
                post.setModifyDate(rs.getDate("modify_date"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setBanner(rs.getString("banner"));
                post.setBannerCaption(rs.getString("banner_caption"));
                post.setAuthor(new Author(rs.getInt("user_id")));

                // add to results list
                posts.add(post);
            }
            close(rs, pt, null);

            // gather tags, author, and view count
            for (Post p : posts) {
                pt = conn.prepareStatement("select t.* from tags t where t.post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                // get this post's tags - there could be more than 1
                ArrayList<String> post_tags = new ArrayList<String>();
                while (rs.next()) {
                    post_tags.add(rs.getString("tag_name"));
                }
                close(rs, pt, null);
                p.setTags(post_tags);

                // get post's author
                pt = conn.prepareStatement(
                        "select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, "
                                + "a.create_date, a.modify_date from users a where a.user_id = ?");
                pt.setInt(1, p.getAuthor().getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    // get the user properties
                    Author author = new Author(rs.getInt("user_id"));
                    author.setUriName(rs.getString("uri_name"));
                    author.setName(rs.getString("name"));
                    author.setCreateDate(rs.getDate("create_date"));
                    author.setDescription(rs.getString("description"));
                    author.setModifyDate(rs.getDate("modify_date"));
                    author.setThumbnail(rs.getString("thumbnail"));
                    p.setAuthor(author);
                } else {
                    p.getAuthor().setName("Anonymous");
                    p.getAuthor().setUriName("anonymous");
                }
                close(rs, pt, null);

                // get post's view count
                pt = conn.prepareStatement("select * from views where post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    p.setRawViews(rs.getLong("count"));
                    p.setSessionViews(rs.getLong("session"));
                } else {
                    p.setRawViews(0);
                    p.setSessionViews(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return posts;
    }

    @Override
    public ArrayList<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {

        ArrayList<Post> posts = new ArrayList<Post>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            int offset = (page - 1) * limit;
            String query = "select distinct t.tag_name, p.* "
                    + "from tags t inner join posts p on t.post_id = p.post_id "
                    + "where t.tag_name = '" + tag + "'";

            if (!includeHidden) {
                query += " and p.is_visible <> 0 ";
            }
            query += "order by p.create_date desc limit " + limit + " offset " + offset;

            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();
            while (rs.next()) {

                // get the post properties
                Post post = new Post(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setUriName(rs.getString("uri_name"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setDescription(rs.getString("description"));
                // post.setHtmlContent(rs.getString("html_content"));
                post.setVisible(rs.getInt("is_visible") == 1);
                post.setFeatured(rs.getInt("is_featured") == 1);
                post.setCategory(rs.getString("category"));
                post.setModifyDate(rs.getDate("modify_date"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setBanner(rs.getString("banner"));
                post.setBannerCaption(rs.getString("banner_caption"));
                post.setAuthor(new Author(rs.getInt("user_id")));

                // add to results list
                posts.add(post);
            }
            close(rs, pt, null);

            // gather tags, author, and view count
            for (Post p : posts) {
                pt = conn.prepareStatement("select t.* from tags t where t.post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                // get this post's tags - there could be more than 1
                ArrayList<String> post_tags = new ArrayList<String>();
                while (rs.next()) {
                    post_tags.add(rs.getString("tag_name"));
                }
                close(rs, pt, null);
                p.setTags(post_tags);

                // get post's author
                pt = conn.prepareStatement(
                        "select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, "
                                + "a.create_date, a.modify_date from users a where a.user_id = ?");
                pt.setInt(1, p.getAuthor().getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    // get the user properties
                    Author author = new Author(rs.getInt("user_id"));
                    author.setUriName(rs.getString("uri_name"));
                    author.setName(rs.getString("name"));
                    author.setCreateDate(rs.getDate("create_date"));
                    author.setDescription(rs.getString("description"));
                    author.setModifyDate(rs.getDate("modify_date"));
                    author.setThumbnail(rs.getString("thumbnail"));
                    p.setAuthor(author);
                } else {
                    p.getAuthor().setName("Anonymous");
                    p.getAuthor().setUriName("anonymous");
                }
                close(rs, pt, null);

                // get post's view count
                pt = conn.prepareStatement("select * from views where post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    p.setRawViews(rs.getLong("count"));
                    p.setSessionViews(rs.getLong("session"));
                } else {
                    p.setRawViews(0);
                    p.setSessionViews(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return posts;
    }

    @Override
    public ArrayList<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {

        ArrayList<Post> posts = new ArrayList<Post>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            int offset = (page - 1) * limit;
            String query = "select p.* from posts p where YEAR(p.publish_date) = " + year + "";

            if (!includeHidden) {
                query += " and p.is_visible <> 0 ";
            }
            query += "order by p.create_date desc limit " + limit + " offset " + offset;

            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();
            while (rs.next()) {

                // get the post properties
                Post post = new Post(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setUriName(rs.getString("uri_name"));
                post.setCreateDate(rs.getDate("create_date"));
                post.setPublishDate(rs.getDate("publish_date"));
                post.setDescription(rs.getString("description"));
                // post.setHtmlContent(rs.getString("html_content"));
                post.setVisible(rs.getInt("is_visible") == 1);
                post.setFeatured(rs.getInt("is_featured") == 1);
                post.setCategory(rs.getString("category"));
                post.setModifyDate(rs.getDate("modify_date"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setBanner(rs.getString("banner"));
                post.setBannerCaption(rs.getString("banner_caption"));
                post.setAuthor(new Author(rs.getInt("user_id")));

                // add to results list
                posts.add(post);
            }
            rs.close();
            pt.close();

            // gather tags, author, and view count
            for (Post p : posts) {
                pt = conn.prepareStatement("select t.* from tags t where t.post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                // get this post's tags - there could be more than 1
                ArrayList<String> post_tags = new ArrayList<String>();
                while (rs.next()) {
                    post_tags.add(rs.getString("tag_name"));
                }
                p.setTags(post_tags);
                close(rs, pt, null);

                // get post's author
                pt = conn.prepareStatement(
                        "select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = ?");
                pt.setInt(1, p.getAuthor().getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    // get the user properties
                    Author author = new Author(rs.getInt("user_id"));
                    author.setUriName(rs.getString("uri_name"));
                    author.setName(rs.getString("name"));
                    author.setCreateDate(rs.getDate("create_date"));
                    author.setDescription(rs.getString("description"));
                    author.setModifyDate(rs.getDate("modify_date"));
                    author.setThumbnail(rs.getString("thumbnail"));
                    p.setAuthor(author);
                } else {
                    p.getAuthor().setName("Anonymous");
                    p.getAuthor().setUriName("anonymous");
                }
                close(rs, pt, null);

                // get post's view count
                pt = conn.prepareStatement("select * from views where post_id = ?");
                pt.setInt(1, p.getId());
                rs = pt.executeQuery();

                if (rs.next()) {
                    p.setRawViews(rs.getLong("count"));
                    p.setSessionViews(rs.getLong("session"));
                } else {
                    p.setRawViews(0);
                    p.setSessionViews(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }
        return posts;
    }

    @Override
    public ArrayList<Author> getAuthors(int page, int limit, boolean includeAdmins) {

        ArrayList<Author> authors = new ArrayList<Author>();
        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            int offset = (page - 1) * limit;
            String query = "select u.*, r.name as 'name2', r.description as 'desc2', r.* from users u inner join roles r on r.role_id = u.role ";

            if (!includeAdmins) {
                query += "where r.is_public <> 0 ";
            }

            query += "order by u.create_date desc limit " + limit + " offset " + offset;

            conn = getConnection();
            pt = conn.prepareStatement(query);
            rs = pt.executeQuery();
            while (rs.next()) {

                // get the user properties
                Author author = new Author(rs.getInt("user_id"));
                author = new Author(rs.getInt("user_id"));
                author.setName(rs.getString("name"));
                author.setUsername(rs.getString("username"));
                author.setUriName(rs.getString("uri_name"));
                author.setThumbnail(rs.getString("thumbnail"));
                author.setDescription(rs.getString("description"));
                // author.setHtmlContent(rs.getString("html_content"));
                author.setCreateDate(rs.getDate("create_date"));
                author.setModifyDate(rs.getDate("modify_date"));
                author.setLastLoginDate(rs.getDate("last_login_date"));
                author.setEmail(rs.getString("email"));

                // get role properties
                Role role = new Role(rs.getInt("role"));
                role.setName(rs.getString("name2"));
                role.setDescription(rs.getString("desc2"));
                role.setPublic(rs.getBoolean("is_public"));
                role.setPostsCreate(rs.getBoolean("posts_create"));
                role.setPostsEdit(rs.getBoolean("posts_edit_own"));
                role.setPostsEditOthers(rs.getBoolean("posts_edit_others"));
                role.setPostsSeeHidden(rs.getBoolean("posts_see_hidden"));
                role.setPostsDelete(rs.getBoolean("posts_delete"));
                role.setUsersCreate(rs.getBoolean("users_create"));
                role.setUsersEdit(rs.getBoolean("users_edit_own"));
                role.setUsersEditOthers(rs.getBoolean("users_edit_others"));
                role.setUsersDelete(rs.getBoolean("users_delete"));
                role.setRolesCreate(rs.getBoolean("roles_create"));
                role.setRolesEdit(rs.getBoolean("roles_edit"));
                role.setRolesDelete(rs.getBoolean("roles_delete"));
                role.setPagesCreate(rs.getBoolean("pages_create"));
                role.setPagesEdit(rs.getBoolean("pages_edit"));
                role.setPagesDelete(rs.getBoolean("pages_delete"));
                role.setCommentsCreate(rs.getBoolean("comments_create"));
                role.setCommentsEdit(rs.getBoolean("comments_edit_own"));
                role.setCommentsEditOthers(rs.getBoolean("comments_edit_others"));
                role.setCommentsDelete(rs.getBoolean("comments_delete"));
                role.setSettingsCreate(rs.getBoolean("settings_create"));
                role.setSettingsEdit(rs.getBoolean("settings_edit"));
                role.setSettingsDelete(rs.getBoolean("settings_delete"));
                author.setRole(role);

                // add to results list
                authors.add(author);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, pt, conn);
        }

        return authors;
    }

    @Override
    public Author getUser(String username) {

        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pt = conn.prepareStatement(
                    "select u.*, r.name as 'name2', r.description as 'desc2', r.* from users u inner join roles r on r.role_id = u.role where u.username = ?");
            pt.setString(1, username);

            // get the author properties
            Author user = new Author(-1);
            rs = pt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setUriName(rs.getString("uri_name"));
                user.setThumbnail(rs.getString("thumbnail"));
                user.setDescription(rs.getString("description"));
                user.setHtmlContent(rs.getString("html_content"));
                user.setCreateDate(rs.getDate("create_date"));
                user.setModifyDate(rs.getDate("modify_date"));
                user.setLastLoginDate(rs.getDate("last_login_date"));
                user.setEmail(rs.getString("email"));

                // get role properties
                Role role = new Role(rs.getInt("role"));
                role.setName(rs.getString("name2"));
                role.setDescription(rs.getString("desc2"));
                role.setPublic(rs.getBoolean("is_public"));
                role.setPostsCreate(rs.getBoolean("posts_create"));
                role.setPostsEdit(rs.getBoolean("posts_edit_own"));
                role.setPostsEditOthers(rs.getBoolean("posts_edit_others"));
                role.setPostsSeeHidden(rs.getBoolean("posts_see_hidden"));
                role.setPostsDelete(rs.getBoolean("posts_delete"));
                role.setUsersCreate(rs.getBoolean("users_create"));
                role.setUsersEdit(rs.getBoolean("users_edit_own"));
                role.setUsersEditOthers(rs.getBoolean("users_edit_others"));
                role.setUsersDelete(rs.getBoolean("users_delete"));
                role.setRolesCreate(rs.getBoolean("roles_create"));
                role.setRolesEdit(rs.getBoolean("roles_edit"));
                role.setRolesDelete(rs.getBoolean("roles_delete"));
                role.setPagesCreate(rs.getBoolean("pages_create"));
                role.setPagesEdit(rs.getBoolean("pages_edit"));
                role.setPagesDelete(rs.getBoolean("pages_delete"));
                role.setCommentsCreate(rs.getBoolean("comments_create"));
                role.setCommentsEdit(rs.getBoolean("comments_edit_own"));
                role.setCommentsEditOthers(rs.getBoolean("comments_edit_others"));
                role.setCommentsDelete(rs.getBoolean("comments_delete"));
                role.setSettingsCreate(rs.getBoolean("settings_create"));
                role.setSettingsEdit(rs.getBoolean("settings_edit"));
                role.setSettingsDelete(rs.getBoolean("settings_delete"));
                user.setRole(role);
            }
            close(rs, pt, null);

            // get the security properties
            pt = conn.prepareStatement("select * from passwords where user_id = ?");
            pt.setInt(1, user.getId());
            rs = pt.executeQuery();
            if (rs.next()) {
                user.setPassword(rs.getString("pwd"));
                user.setOTPEnabled(rs.getInt("is_otp_enabled") > 0);
                user.setOTPAuthenticated(!user.isOTPEnabled());
                user.setKeySecret(rs.getString("otp_key"));
                user.setKeyReset(rs.getString("reset_key"));
                user.setKeyRecover(rs.getString("recover_key"));
            }

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(rs, pt, conn);
        }
    }

    @Override
    public Author newUser(Author user) {

        Connection conn = null;
        PreparedStatement pt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // insert account
            pt = conn.prepareStatement(
                    "insert into users (username,name,uri_name,email,role,thumbnail) values (?,?,?,?,?,?)");
            pt.setString(1, user.getUsername());
            pt.setString(2, user.getName());
            pt.setString(3, user.getUriName());
            pt.setString(4, user.getEmail());
            pt.setInt(5, user.getRole().getId());
            pt.setString(6, user.getThumbnail());

            if (pt.execute()) {
                // failed to insert
                throw new Exception("Oops. Failed to create new user. Please try again.");
            }
            pt.close();

            // get user ID
            pt = conn.prepareStatement("select user_id from users where email = ?");
            pt.setString(1, user.getEmail());
            ResultSet r1 = pt.executeQuery();

            if (r1.first()) {
                user.setId(r1.getInt("user_id"));
            }
            pt.close();

            // insert password
            pt = conn.prepareStatement("insert into passwords (user_id,pwd) values (?,?)");
            pt.setInt(1, user.getId());
            pt.setString(2, user.getPassword());

            if (pt.execute()) {
                // failed to insert
                throw new Exception(
                        "Oops. Failed to create new user's password hash. Please try again.");
            }
            pt.close();

            // get new user
            pt = conn.prepareStatement(
                    "select u.*, r.name as 'name2', r.description as 'desc2', r.* from users u inner join roles r on r.role_id = u.role where u.user_id = ?");
            pt.setInt(1, user.getId());
            rs = pt.executeQuery();
            if (rs.first()) {
                user = new Author(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setUriName(rs.getString("uri_name"));
                user.setThumbnail(rs.getString("thumbnail"));
                user.setDescription(rs.getString("description"));
                user.setHtmlContent(rs.getString("html_content"));
                user.setCreateDate(rs.getDate("create_date"));
                user.setModifyDate(rs.getDate("modify_date"));
                user.setLastLoginDate(rs.getDate("last_login_date"));
                user.setEmail(rs.getString("email"));

                // get role properties
                Role role = new Role(rs.getInt("role"));
                role.setName(rs.getString("name2"));
                role.setDescription(rs.getString("desc2"));
                role.setPublic(rs.getBoolean("is_public"));
                role.setPostsCreate(rs.getBoolean("posts_create"));
                role.setPostsEdit(rs.getBoolean("posts_edit_own"));
                role.setPostsEditOthers(rs.getBoolean("posts_edit_others"));
                role.setPostsSeeHidden(rs.getBoolean("posts_see_hidden"));
                role.setPostsDelete(rs.getBoolean("posts_delete"));
                role.setUsersCreate(rs.getBoolean("users_create"));
                role.setUsersEdit(rs.getBoolean("users_edit_own"));
                role.setUsersEditOthers(rs.getBoolean("users_edit_others"));
                role.setUsersDelete(rs.getBoolean("users_delete"));
                role.setRolesCreate(rs.getBoolean("roles_create"));
                role.setRolesEdit(rs.getBoolean("roles_edit"));
                role.setRolesDelete(rs.getBoolean("roles_delete"));
                role.setPagesCreate(rs.getBoolean("pages_create"));
                role.setPagesEdit(rs.getBoolean("pages_edit"));
                role.setPagesDelete(rs.getBoolean("pages_delete"));
                role.setCommentsCreate(rs.getBoolean("comments_create"));
                role.setCommentsEdit(rs.getBoolean("comments_edit_own"));
                role.setCommentsEditOthers(rs.getBoolean("comments_edit_others"));
                role.setCommentsDelete(rs.getBoolean("comments_delete"));
                role.setSettingsCreate(rs.getBoolean("settings_create"));
                role.setSettingsEdit(rs.getBoolean("settings_edit"));
                role.setSettingsDelete(rs.getBoolean("settings_delete"));
                user.setRole(role);
            }
            rs.close();
            pt.close();

            // get the security properties
            pt = conn.prepareStatement("select * from passwords where user_id = ?");
            pt.setInt(1, user.getId());
            rs = pt.executeQuery();
            if (rs.first()) {
                user.setPassword(rs.getString("pwd"));
                user.setOTPEnabled(rs.getInt("is_otp_enabled") > 0);
                user.setOTPAuthenticated(!user.isOTPEnabled());
                user.setKeySecret(rs.getString("otp_key"));
                user.setKeyReset(rs.getString("reset_key"));
                user.setKeyRecover(rs.getString("recover_key"));
            }

            // done
            conn.commit();
            return user;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return null;
        } finally {
            close(rs, pt, conn);
        }
    }

    @Override
    public boolean editUser(Author user) {
        // update user information
        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // update user account
            pt = conn.prepareStatement(
                    "update users set username = ?, email = ?, modify_date = ? where user_id = ?");
            pt.setString(1, user.getUsername());
            pt.setString(2, user.getEmail());
            pt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            pt.setInt(4, user.getId());

            if (pt.execute()) {
                // failed to update user account
                throw new Exception("Oops. Failed to update account. Please try again.");
            }
            pt.close();

            // update user security
            pt = conn.prepareStatement("update passwords set "
                    + "pwd = ?, is_otp_enabled = ?, otp_key = ?, recover_key = ? where user_id = ?");
            pt.setString(1, user.getPassword());
            pt.setBoolean(2, user.isOTPEnabled());

            if (user.getKeySecret() != null) {
                pt.setString(3, user.getKeySecret());
            } else {
                pt.setNull(3, Types.VARCHAR);
            }
            if (user.getKeyRecover() != null) {
                pt.setString(4, user.getKeyRecover());
            } else {
                pt.setNull(4, Types.VARCHAR);
            }
            pt.setInt(5, user.getId());

            if (pt.execute()) {
                // failed to update user security
                throw new Exception("Failed to update security. Please try again.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public boolean loginUser(Author user) {

        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // update last login date
            pt = conn.prepareStatement(
                    "UPDATE users SET last_login_date=CURRENT_TIMESTAMP where user_id = ?");
            pt.setInt(1, user.getId());

            if (pt.execute()) {
                throw new Exception("Failed to update last login date.");
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        Connection conn = null;
        PreparedStatement pt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            if (post == null) {
                throw new NullPointerException();
            }

            if (sessionView) {
                pt = conn.prepareStatement(
                        "UPDATE views SET count = count + 1, session = session + 1 where post_id = ?");
                pt.setInt(1, post.getId());
                int r = pt.executeUpdate();
                pt.close();

                if (r == 0) {
                    pt = conn.prepareStatement(
                            "INSERT INTO views (post_id,count,session) VALUES (?,1,1)");
                    pt.setInt(1, post.getId());
                    pt.executeUpdate();
                    pt.close();
                }
            } else {
                pt = conn.prepareStatement("UPDATE views SET count = count + 1 where post_id = ?");
                pt.setInt(1, post.getId());
                int r = pt.executeUpdate();
                pt.close();

                if (r == 0) {
                    pt = conn.prepareStatement(
                            "INSERT INTO views (post_id,count,session) VALUES (?,1,0)");
                    pt.setInt(1, post.getId());
                    pt.executeUpdate();
                    pt.close();
                }
            }

            // done
            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(null, pt, conn);
        }
    }

}
