package org.rw.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.rw.bean.Author;
import org.rw.bean.Database;
import org.rw.bean.Post;
import org.rw.model.ApplicationStore;

public class MySQLDatabase extends DatabaseSource {
	
	public MySQLDatabase(Database database) {
		super(database);
	}

	/**
	 * Obtains a connection to the MySQL DB if possible.
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(ApplicationStore.getSetting("driver"));
		return DriverManager.getConnection("jdbc:mysql://"+database.getHost()+":"+database.getPort()+"/"+database.getName(),database.getUsername(),database.getPassword());
	}

	@Override
	public Post getPost(String uri, boolean includeHidden) {
		Post post = null;
		
		// search in db for post by title
		Connection conn = null;
		try {
			String query = "select p.*, u.name, u.uri_name as 'authorUri', u.description as 'authorDesc', u.thumbnail as 'authorThumbnail' "+
					"from posts p "+
					"left join users u on p.user_id = u.user_id "+
					"where p.uri_name = '"+uri+"'";
			
			if(!includeHidden)
				query += " and p.is_visible <> 0";
			
			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()) {
				// get the post properties
				post = new Post(rs.getInt("post_id"));
				post.setUriName(rs.getString("uri_name"));
				post.setTitle(rs.getString("title"));
				post.setCreateDate(rs.getDate("create_date"));
				post.setModifyDate(rs.getDate("modify_date"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setIs_visible(rs.getInt("is_visible") > 0);
				post.setIsFeatured(rs.getInt("is_featured") > 0);
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
				
				ResultSet rs2 = st.executeQuery("select * from tags where post_id = "+post.getId());
				
				// get post tags - there could be more than 1
				ArrayList<String> tags = new ArrayList<String>();
				while(rs2.next()) {
					tags.add(rs2.getString("tag_name"));
				}
				post.setTags(tags);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		
		return post;
	}

	@Override
	public void newPost(Post post) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editPost(Post post) {
		// TODO Auto-generated method stub

	}

	@Override
	public Author getAuthor(String uri) {
		
		Author author = null;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from users where uri_name = '"+uri+"'");
			
			if(rs.first()) {
				// get the author properties
				author = new Author(rs.getInt("user_id"));
				author.setName(rs.getString("name"));
				author.setUriName(rs.getString("uri_name"));
				author.setThumbnail(rs.getString("thumbnail"));
				author.setDescription(rs.getString("description"));
				author.setHtmlContent(rs.getString("html_content"));
				author.setCreateDate(rs.getDate("create_date"));
				author.setModifyDate(rs.getDate("modify_date"));
				author.setLastLoginDate(rs.getDate("last_login_date"));
				author.setEmail(rs.getString("email"));
				author.setAdmin(rs.getInt("role")==1);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		
		return author;
	}

	@Override
	public void newAuthor(Author author) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editAuthor(Author author) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Post> getArchiveFeatured() {
		ArrayList<Post> archive_featured = new ArrayList<Post>();
		
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			
			// search in db for featured posts
			ResultSet rs = st.executeQuery("select p.post_id, p.title, p.uri_name, p.publish_date, p.thumbnail, p.description from posts p where p.is_visible <> 0 and p.is_featured <> 0 order by p.create_date desc limit 2");
			
			while(rs.next()) {					
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
			} catch (SQLException e) {/*Do Nothing*/}
		}
		return archive_featured;
	}

	@Override
	public ArrayList<String> getArchiveYears() {
		ArrayList<String>archive_years = new ArrayList<String>();
		
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			// search in db for total posts by each year
			ResultSet rs = st.executeQuery("select YEAR(create_date) as year, COUNT(*) as count from posts where is_visible <> 0 group by YEAR(create_date) order by YEAR(create_date) desc");
			
			while(rs.next()) {
				// get year and  count
				int year = rs.getInt("year");
				int count = rs.getInt("count");
				archive_years.add(year+" ("+count+")");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		return archive_years;
	}

	@Override
	public ArrayList<String> getArchiveTags() {
		ArrayList<String> archive_tags = new ArrayList<String>();
		
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			// search in db for total tags by name
			ResultSet rs = st.executeQuery("select t.tag_name, COUNT(*) as count from tags t inner join posts p on t.post_id = p.post_id where p.is_visible <> 0 group by t.tag_name order by COUNT(*) desc, t.tag_name");
			
			while(rs.next()) {
				// get tag name and  count
				String tag = rs.getString("tag_name");
				int count = rs.getInt("count");
				archive_tags.add(tag+" ("+count+")");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		return archive_tags;
	}

	@Override
	public ArrayList<Post> getArchiveRecent(String[] uri) {
		ArrayList<Post> archive_recent = new ArrayList<Post>();
		
		String condition = "";
		for(String u : uri)
		{
			if(!u.trim().isEmpty())
				condition += "'"+uri+"',";
		}
		condition = condition.substring(0,condition.length()-1);
		
		// get the recently viewed posts
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			
			// search in db for recently viewed posts
			String query = "select p.post_id, p.title, p.uri_name, p.description, p.publish_date from posts p where p.uri_name IN ("+
					condition+") AND p.is_visible <> 0 order by p.create_date desc limit 3";
			//System.out.println(query);
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setDescription(rs.getString("description"));
				
				// add to results list
				archive_recent.add(post);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		return archive_recent;
	}

	@Override
	public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {
		ArrayList<Post> posts = new ArrayList<Post>();
		
		Connection conn = null;
		try {
			int offset = (page - 1) * limit;
			String query = "select p.* from posts p ";
			
			if(!includeHidden)
				query += "where is_visible <> 0 ";
			
			query += "order by p.create_date desc limit "+limit+" offset "+offset;
			
			conn = getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				post.setCreateDate(rs.getDate("create_date"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setDescription(rs.getString("description"));
				//post.setHtmlContent(rs.getString("html_content"));
				post.setIs_visible(rs.getInt("is_visible")==1);
				post.setIsFeatured(rs.getInt("is_featured")==1);
				post.setModifyDate(rs.getDate("modify_date"));
				post.setThumbnail(rs.getString("thumbnail"));
				post.setBanner(rs.getString("banner"));
				post.setBannerCaption(rs.getString("banner_caption"));
				post.setAuthor(new Author(rs.getInt("user_id")));
				
				// add to results list
				posts.add(post);
			}
			
			// gather tags
			for(Post p : posts)
			{
				ResultSet rs2 = st.executeQuery("select t.* from tags t where t.post_id = "+p.getId());
				
				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while(rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);
				
				ResultSet rs3 = st.executeQuery("select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a where a.user_id = "+p.getAuthor().getId());
				if(rs3.next())
				{
					// get the user properties
					Author author = new Author(rs3.getInt("user_id"));
					author.setUriName(rs3.getString("uri_name"));
					author.setName(rs3.getString("name"));
					author.setCreateDate(rs3.getDate("create_date"));
					author.setDescription(rs3.getString("description"));
					author.setModifyDate(rs3.getDate("modify_date"));
					author.setThumbnail(rs3.getString("thumbnail"));
					p.setAuthor(author);
				}
				else
				{
					p.getAuthor().setName("Anonymous");
					p.getAuthor().setUriName("anonymous");
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		return posts;
	}

	@Override
	public ArrayList<Post> getPostsByTag(int page, int limit, String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Author> getAuthors() {
		ArrayList<Author> authors = new ArrayList<Author>();
		
		Connection conn = null;
		try {
			conn = getConnection();
			Statement st = conn.createStatement();
			
			// gather authors
			ResultSet rs = st.executeQuery("select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a order by a.create_date desc limit 3");
			
			while(rs.next()) {
				
				// get the user properties
				Author author = new Author(rs.getInt("user_id"));
				author.setUriName(rs.getString("uri_name"));
				author.setName(rs.getString("name"));
				author.setCreateDate(rs.getDate("create_date"));
				author.setDescription(rs.getString("description"));
				author.setModifyDate(rs.getDate("modify_date"));
				author.setThumbnail(rs.getString("thumbnail"));
				
				// add to results list
				authors.add(author);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
		}
		
		return authors;
	}

}
