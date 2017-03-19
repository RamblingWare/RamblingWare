package org.rw.model.database;

import java.util.ArrayList;

import org.rw.bean.Author;
import org.rw.bean.Database;
import org.rw.bean.Post;

/**
 * A blueprint to communicate to a Database Service.
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class DatabaseSource {
	
	protected static Database database;
	
	public DatabaseSource(Database database) {
		DatabaseSource.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public abstract Post getPost(String uri, boolean includeHidden);
	
	public abstract void newPost(Post post);
	
	public abstract void editPost(Post post);
	
	public abstract Author getAuthor(String uri);
	
	public abstract void newAuthor(Author author);
	
	public abstract void editAuthor(Author author);
	
	public abstract ArrayList<Post> getArchiveFeatured();
	
	public abstract ArrayList<String> getArchiveYears();
	
	public abstract ArrayList<String> getArchiveTags();
	
	public abstract ArrayList<Post> getArchiveRecent(String[] uri);
	
	public abstract ArrayList<Post> getPosts(int page, int limit, boolean includeHidden);
	
	public abstract ArrayList<Post> getPostsByTag(int page, int limit, String tag);
	
	public abstract ArrayList<Author> getAuthors();
}
