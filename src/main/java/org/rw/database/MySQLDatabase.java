package org.rw.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.rw.action.model.Author;
import org.rw.action.model.Database;
import org.rw.action.model.Post;
import org.rw.config.Application;
import org.rw.config.Utils;

public class MySQLDatabase extends DatabaseSource {

	public MySQLDatabase(Database database) {
		super(database);
	}

	/**
	 * Obtains a connection to the MySQL DB if possible.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(Application.getSetting("driver"));
		return DriverManager.getConnection(
				"jdbc:mysql://" + database.getHost() + ":" + database.getPort() + "/" + database.getName(),
				database.getUsername(), database.getPassword());
	}

	@Override
	public Post getPost(String uri, boolean includeHidden, boolean increaseHitCounter) {
		
		// search in db for post by title
		Post post = null;
		Connection conn = null;
		try {
			String query = "select p.*, u.name, u.uri_name as 'authorUri', u.description as 'authorDesc', u.thumbnail as 'authorThumbnail' "
					+ "from posts p " + "left join users u on p.user_id = u.user_id " + "where p.uri_name = '" + uri
					+ "'";

			if (!includeHidden)
				query += " and p.is_visible <> 0";

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

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

				// get post tags - there could be more than 1
				ResultSet rs2 = st.executeQuery("select * from tags where post_id = " + post.getId());
				ArrayList<String> tags = new ArrayList<String>();
				while (rs2.next()) {
					tags.add(rs2.getString("tag_name"));
				}
				post.setTags(tags);
				
				// get post view count
				ResultSet rs3 = st.executeQuery("select count from views where post_id = " + post.getId());
				if(rs3.next()) {
					post.setViews(rs3.getLong("count"));
				} else {
					post.setViews(0);
				}
			}
			
			if(post != null && increaseHitCounter) {
				String sql = "UPDATE views SET count = count + 1 where post_id = " + post.getId();
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            int r = stmt.executeUpdate();
	            
	            if(r == 0) {
	            	sql = "INSERT INTO views (post_id,count) VALUES (" + post.getId()+",1)";
		            PreparedStatement stmt2 = conn.prepareStatement(sql);
		            stmt2.executeUpdate();
	            }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}

		return post;
	}

	@Override
	public Post newPost(Post post) {
		
		// insert new post into db
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			
			// save fields into database
			PreparedStatement pt = conn.prepareStatement(
					"insert into posts (user_id,title,uri_name,publish_date,is_visible,is_featured,category,thumbnail,banner,banner_caption,description,html_content) values (?,?,?,?,?,?,?,?,?,?,?,?)");
			pt.setInt(1, post.getAuthor().getId());
			pt.setString(2, post.getTitle());
			pt.setString(3, post.getUriName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(post.getPublishDate());
			pt.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
			pt.setInt(5, post.isVisible()?1:0);
			pt.setInt(6, post.isFeatured()?1:0);
			pt.setString(7, post.getCategory());
			pt.setString(8, post.getThumbnail());
			pt.setString(9, post.getBanner());
			pt.setString(10, post.getBannerCaption());
			pt.setString(11, post.getDescription());
			pt.setString(12, post.getHtmlContent());
			
			if(pt.execute())
			{
				// failed to insert
				throw new Exception("Oops. Failed to create new post. Please try again.");
			}
			
			// get the post_id and add the tags
			ResultSet rs1 = st.executeQuery("select post_id from posts where uri_name = '"+post.getUriName()+"'");
			rs1.next();
			post.setId(rs1.getInt(1));
			
			String qry = "insert into tags (post_id,tag_name) values ";
			for(String tag : post.getTags()) {
				qry+="("+post.getId()+",'"+tag+"'),";
			}
			qry = qry.substring(0, qry.length()-1); // remove last comma
			
			// insert tags
			int r = st.executeUpdate(qry);
			
			if(r == 0) {
				// error inserting tags
				throw new Exception("Oops. Failed to add tags to the new post. Please try adding them later.");
			}
			
			// done		
			conn.commit();
			return post;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return null;
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {}
		}
	}

	@Override
	public boolean editPost(Post post) {
		// they've submitted an edit on a post
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			
			String update = "update posts set "
					+ "user_id = ?,"
					+ "title = ?,"
					+ "uri_name = ?,"
					+ "modify_date = CURRENT_TIMESTAMP,"
					+ "publish_date = ?,"
					+ "is_visible = ?,"
					+ "is_featured = ?,"
					+ "category = ?,"
					+ "thumbnail = ?,"
					+ "description = ?,"
					+ "banner = ?,"
					+ "banner_caption = ?,"
					+ "html_content = ? where post_id = "+post.getId();
			PreparedStatement pt = conn.prepareStatement(update);
			pt.setInt(1, post.getAuthor().getId());
			pt.setString(2, post.getTitle());
			pt.setString(3, post.getUriName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(post.getPublishDate());
			pt.setTimestamp(4, new java.sql.Timestamp(cal.getTimeInMillis()));
			pt.setInt(5, post.isVisible()?1:0);
			pt.setInt(6, post.isFeatured()?1:0);
			pt.setString(7, post.getCategory());
			pt.setString(8, post.getThumbnail());
			pt.setString(9, post.getDescription());
			pt.setString(10, post.getBanner());
			pt.setString(11, post.getBannerCaption());
			pt.setString(12, post.getHtmlContent());
			
			if(pt.execute()) {
				// failed to update post
				throw new Exception("Oops. Failed to update the post. Please try again.");
			}
			
			// remove old tags
			int rt = st.executeUpdate("delete from tags where post_id = "+post.getId());
			
			if(rt == 0) {
				throw new Exception("Oops. Failed to delete the post tags. Please try again.");
			}
			
			// insert new tags
			String qry = "insert into tags (post_id,tag_name) values ";
			for(String tag : post.getTags()) {
				qry+="("+post.getId()+",'"+tag+"'),";
			}
			qry = qry.substring(0, qry.length()-1); // remove last comma
			
			// insert tags
			int r = st.executeUpdate(qry);
			
			if(r == 0) {
				// error inserting tags
				throw new Exception("Oops. Failed to add tags to the post. Please try adding them later.");
			}
			
			// done
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {}
		}
	}
	
	@Override
	public boolean deletePost(Post post) {
		// they've requested to delete a post
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			
			int r = st.executeUpdate("delete from posts where post_id = "+post.getId());
			
			if(r == 0) {
				throw new Exception("Oops. Failed to delete the post. Please try again.");
			}
			
			int t = st.executeUpdate("delete from tags where post_id = "+post.getId());
			
			if(t == 0) {
				throw new Exception("Oops. Failed to delete the post tags. Please try again.");
			}
			
			// done
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {}
		}
	}

	@Override
	public Author getAuthor(String uri) {

		Author author = null;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from users where uri_name = '" + uri + "'");

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
				author.setAdmin(rs.getInt("role") > 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}

		return author;
	}

	@Override
	public boolean editAuthor(Author author) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			
			String update = "update users set "
					+ "name = ?,"
					+ "uri_name = ?,"
					+ "modify_date = CURRENT_TIMESTAMP,"
					+ "thumbnail = ?,"
					+ "description = ?,"
					+ "html_content = ? where user_id = "+author.getId();
			PreparedStatement pt = conn.prepareStatement(update);
			pt.setString(1, author.getName());
			pt.setString(2, author.getUriName());
			pt.setString(3, author.getThumbnail());
			pt.setString(4, author.getDescription());
			pt.setString(5, author.getHtmlContent());
			
			if(pt.execute()) {
				// failed to update user
				throw new Exception("Oops. Failed to update the user. Please try again.");
			}
			
			// done
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {}
		}
	}
	
	@Override
	public boolean deleteAuthor(Author author) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			
			int r = st.executeUpdate("delete from users where user_id = "+author.getId());
			
			if(r == 0) {
				throw new Exception("Oops. Failed to delete the user. Please try again.");
			}
			
			int r2 = st.executeUpdate("delete from passwords where user_id = "+author.getId());
			
			if(r2 == 0) {
				throw new Exception("Oops. Failed to delete the user. Please try again.");
			}
			
			// done
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {}
		}
	}

	@Override
	public ArrayList<Post> getArchiveFeatured() {
		ArrayList<Post> archive_featured = new ArrayList<Post>();

		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();

			// search in db for featured posts
			ResultSet rs = st.executeQuery(
					"select p.post_id, p.title, p.uri_name, p.publish_date, p.thumbnail, p.description from posts p where p.is_visible <> 0 and p.is_featured <> 0 order by p.create_date desc limit 2");

			while (rs.next()) {
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setDescription(rs.getString("description"));
				post.setThumbnail(rs.getString("thumbnail"));

				// add to results list
				archive_featured.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return archive_featured;
	}

	@Override
	public ArrayList<String> getArchiveYears() {
		ArrayList<String> archive_years = new ArrayList<String>();

		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			// search in db for total posts by each year
			ResultSet rs = st.executeQuery(
					"select YEAR(create_date) as year, COUNT(*) as count from posts where is_visible <> 0 group by YEAR(create_date) order by YEAR(create_date) desc");

			while (rs.next()) {
				// get year and count
				int year = rs.getInt("year");
				int count = rs.getInt("count");
				archive_years.add(year + " (" + count + ")");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return archive_years;
	}

	@Override
	public ArrayList<String> getArchiveCategories() {
		ArrayList<String> archive_categories = new ArrayList<String>();
		
		Connection conn = null;
		try {
			String query = "select p.category, COUNT(*) as count  from posts p where is_visible <> 0 group by category order by p.category asc";

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				// add to results list
				int count = rs.getInt("count");
				archive_categories.add(rs.getString("category") + " (" + count + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return archive_categories;
	}

	@Override
	public ArrayList<String> getArchiveTags() {
		ArrayList<String> archive_tags = new ArrayList<String>();

		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			// search in db for total tags by name
			ResultSet rs = st.executeQuery(
					"select t.tag_name, COUNT(*) as count from tags t inner join posts p on t.post_id = p.post_id where p.is_visible <> 0 group by t.tag_name order by COUNT(*) desc, t.tag_name");

			while (rs.next()) {
				// get tag name and count
				String tag = rs.getString("tag_name");
				int count = rs.getInt("count");
				archive_tags.add(tag + " (" + count + ")");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return archive_tags;
	}

	@Override
	public ArrayList<String> getPostUris() {
		ArrayList<String> uris = new ArrayList<String>();
		
		Connection conn = null;
		try {
			String query = "select p.uri_name from posts p order by p.uri_name desc";

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				// add to results list
				uris.add(rs.getString("uri_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return uris;
	}

	@Override
	public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select p.* from posts p ";

			if (!includeHidden)
				query += "where is_visible <> 0 ";

			query += "order by p.create_date desc limit " + limit + " offset " + offset;

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
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

			// gather tags, author, and view count
			for (Post p : posts) {
				ResultSet rs2 = st.executeQuery("select t.* from tags t where t.post_id = " + p.getId());

				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while (rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);

				// get post's author
				ResultSet rs3 = st.executeQuery(
						"select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = "
								+ p.getAuthor().getId());
				if (rs3.next()) {
					// get the user properties
					Author author = new Author(rs3.getInt("user_id"));
					author.setUriName(rs3.getString("uri_name"));
					author.setName(rs3.getString("name"));
					author.setCreateDate(rs3.getDate("create_date"));
					author.setDescription(rs3.getString("description"));
					author.setModifyDate(rs3.getDate("modify_date"));
					author.setThumbnail(rs3.getString("thumbnail"));
					p.setAuthor(author);
				} else {
					p.getAuthor().setName("Anonymous");
					p.getAuthor().setUriName("anonymous");
				}
				
				// get post's view count
				ResultSet rs4 = st.executeQuery("select count from views where post_id = " + p.getId());
				if(rs4.next()) {
					p.setViews(rs4.getLong("count"));
				} else {
					p.setViews(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return posts;
	}

	@Override
	public ArrayList<Post> getPostsByCategory(int page, int limit, String category, boolean includeHidden) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select p.* from posts p where category = '" + category + "'";

			if (!includeHidden)
				query += " and p.is_visible <> 0 ";

			query += "order by p.create_date desc limit " + limit + " offset " + offset;

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
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
			
			// gather tags, author, and view count
			for (Post p : posts) {
				ResultSet rs2 = st.executeQuery("select t.* from tags t where t.post_id = " + p.getId());

				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while (rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);

				// get post's author
				ResultSet rs3 = st.executeQuery(
						"select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = "
								+ p.getAuthor().getId());
				if (rs3.next()) {
					// get the user properties
					Author author = new Author(rs3.getInt("user_id"));
					author.setUriName(rs3.getString("uri_name"));
					author.setName(rs3.getString("name"));
					author.setCreateDate(rs3.getDate("create_date"));
					author.setDescription(rs3.getString("description"));
					author.setModifyDate(rs3.getDate("modify_date"));
					author.setThumbnail(rs3.getString("thumbnail"));
					p.setAuthor(author);
				} else {
					p.getAuthor().setName("Anonymous");
					p.getAuthor().setUriName("anonymous");
				}
				
				// get post's view count
				ResultSet rs4 = st.executeQuery("select count from views where post_id = " + p.getId());
				if(rs4.next()) {
					p.setViews(rs4.getLong("count"));
				} else {
					p.setViews(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return posts;
	}

	@Override
	public ArrayList<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select distinct t.tag_name, p.* from tags t inner join posts p on t.post_id = p.post_id "
					+ "where t.tag_name = '" + tag + "'";

			if (!includeHidden)
				query += " and p.is_visible <> 0 ";

			query += "order by p.create_date desc limit " + limit + " offset " + offset;

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
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

			// gather tags, author, and view count
			for (Post p : posts) {
				ResultSet rs2 = st.executeQuery("select t.* from tags t where t.post_id = " + p.getId());

				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while (rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);

				// get post's author
				ResultSet rs3 = st.executeQuery(
						"select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = "
								+ p.getAuthor().getId());
				if (rs3.next()) {
					// get the user properties
					Author author = new Author(rs3.getInt("user_id"));
					author.setUriName(rs3.getString("uri_name"));
					author.setName(rs3.getString("name"));
					author.setCreateDate(rs3.getDate("create_date"));
					author.setDescription(rs3.getString("description"));
					author.setModifyDate(rs3.getDate("modify_date"));
					author.setThumbnail(rs3.getString("thumbnail"));
					p.setAuthor(author);
				} else {
					p.getAuthor().setName("Anonymous");
					p.getAuthor().setUriName("anonymous");
				}
				
				// get post's view count
				ResultSet rs4 = st.executeQuery("select count from views where post_id = " + p.getId());
				if(rs4.next()) {
					p.setViews(rs4.getLong("count"));
				} else {
					p.setViews(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return posts;
	}

	@Override
	public ArrayList<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
		ArrayList<Post> posts = new ArrayList<Post>();

		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select p.* from posts p where YEAR(p.publish_date) = " + year + "";

			if (!includeHidden)
				query += " and p.is_visible <> 0 ";

			query += "order by p.create_date desc limit " + limit + " offset " + offset;

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
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
			
			// gather tags, author, and view count
			for (Post p : posts) {
				ResultSet rs2 = st.executeQuery("select t.* from tags t where t.post_id = " + p.getId());

				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while (rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);

				// get post's author
				ResultSet rs3 = st.executeQuery(
						"select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = "
								+ p.getAuthor().getId());
				if (rs3.next()) {
					// get the user properties
					Author author = new Author(rs3.getInt("user_id"));
					author.setUriName(rs3.getString("uri_name"));
					author.setName(rs3.getString("name"));
					author.setCreateDate(rs3.getDate("create_date"));
					author.setDescription(rs3.getString("description"));
					author.setModifyDate(rs3.getDate("modify_date"));
					author.setThumbnail(rs3.getString("thumbnail"));
					p.setAuthor(author);
				} else {
					p.getAuthor().setName("Anonymous");
					p.getAuthor().setUriName("anonymous");
				}
				
				// get post's view count
				ResultSet rs4 = st.executeQuery("select count from views where post_id = " + p.getId());
				if(rs4.next()) {
					p.setViews(rs4.getLong("count"));
				} else {
					p.setViews(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
		return posts;
	}

	@Override
	public ArrayList<Author> getAuthors(int page, int limit, boolean includeAdmins) {
		ArrayList<Author> authors = new ArrayList<Author>();

		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select * from users a ";

			if (!includeAdmins)
				query += "where role <> 1 ";

			query += "order by a.create_date desc limit " + limit + " offset " + offset;

			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

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
				author.setAdmin(rs.getInt("role") > 0);

				// add to results list
				authors.add(author);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}

		return authors;
	}

	@Override
	public Author getUser(String username) {

		Author user = new Author(-1);
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();

			// get the author properties
			ResultSet rs = st.executeQuery("select * from users where username = '" + username + "'");
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
				user.setAdmin(rs.getInt("role") > 0);
			}

			// get the security properties
			ResultSet rs2 = st.executeQuery("select * from passwords where user_id = " + user.getId());
			if (rs2.next()) {
				user.setPassword(rs2.getString("pwd"));
				user.setOTPEnabled(rs2.getInt("is_otp_enabled") > 0);
				user.setOTPAuthenticated(!user.isOTPEnabled());
				user.setKeySecret(rs2.getString("otp_key"));
				user.setKeyReset(rs2.getString("reset_key"));
				user.setKeyRecover(rs2.getString("recover_key"));
			}
			
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public Author newUser(Author user) {
		
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			conn.setAutoCommit(false);
			
			// insert account 
			int r = st.executeUpdate(
					"insert into users (username,name,uri_name,email,create_date) values " + "('"
							+ user.getUsername() + "','" + user.getName() + "','" + user.getUriName() + "','" + user.getEmail() + "',"
							+ "CURRENT_TIMESTAMP)");
			
			if(r == 0) {
				throw new Exception("Failed to create new user's account.");
			}

			// get user ID
			ResultSet r1 = st.executeQuery("select user_id from users where email = '" + user.getEmail() + "'");
			
			if (r1.first())
				user.setId(r1.getInt("user_id"));
			
			// insert password
			int r2 = st.executeUpdate("insert into passwords (user_id,pwd) values " + "('" + user.getId()
					+ "','" + user.getPassword() + "')");
			
			if(r2 == 0) {
				throw new Exception("Failed to create new user's security.");
			}

			// get new user
			ResultSet rs = st.executeQuery("select * from users where user_id = " + user.getId());
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
				user.setAdmin(rs.getInt("role") > 0);
			}

			// get the security properties
			ResultSet rs2 = st.executeQuery("select * from passwords where user_id = '" + user.getId() + "'");
			if (rs2.first()) {
				user.setPassword(rs2.getString("pwd"));
				user.setOTPEnabled(rs2.getInt("is_otp_enabled") > 0);
				user.setOTPAuthenticated(!user.isOTPEnabled());
				user.setKeySecret(rs2.getString("otp_key"));
				user.setKeyReset(rs2.getString("reset_key"));
				user.setKeyRecover(rs2.getString("recover_key"));
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
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean editUser(Author user) {
		// update user information
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);

			// update user account
			String updateAccount = "update users set " + "username = ?," + "email = ?,"
					+ "modify_date = ? where user_id = " + user.getId();
			PreparedStatement pt = conn.prepareStatement(updateAccount);
			pt.setString(1, user.getUsername());
			pt.setString(2, user.getEmail());
			pt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

			if (pt.execute()) {
				// failed to update user account
				throw new Exception("Failed to update account. Please try again.");
			}

			// update user security
			String updateSecurity = "update passwords set " + "pwd = ?," + "is_otp_enabled = ?," + "otp_key = ?,"
					+ "recover_key = ? where user_id = " + user.getId();

			PreparedStatement pt2 = conn.prepareStatement(updateSecurity);
			pt2.setString(1, user.getPassword());
			pt2.setBoolean(2, user.isOTPEnabled());

			if (user.getKeySecret() != null)
				pt2.setString(3, user.getKeySecret());
			else
				pt2.setNull(3, Types.VARCHAR);

			if (user.getKeyRecover() != null)
				pt2.setString(4, user.getKeyRecover());
			else
				pt2.setNull(4, Types.VARCHAR);

			if (pt2.execute()) {
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
			try {
				st.close();
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean loginUser(Author user) {

		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			conn.setAutoCommit(false);

			// update last login date
			int r = st.executeUpdate("update users set last_login_date='"
					+ Utils.formatMySQLDate(new Date(System.currentTimeMillis())) + "'" + "where user_id = '"
					+ user.getId() + "'");

			if (r <= 0) {
				throw new Exception("Failed to update last login date.");
			}

			// done
			conn.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				/* Do Nothing */}
		}
	}

}
