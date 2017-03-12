package org.rw.model.database;

import org.rw.bean.Author;
import org.rw.bean.Post;

/**
 * An interface to communicate to a Database Service.
 * @author Austin Delamar
 * @date 3/8/2017
 */
public interface DatabaseManager {

	public Post getPost(String uri);
	
	public Author getAuthor(String uri);
	
	public void newPost(Post post);
	
	public void editPost(Post post);
	
	public void newAuthor(Author author);
	
	public void editAuthor(Author author);
	
	// archive
	// years, tags, featured, recent view
	
	// blog - last 10?
	
	// home - last 5? or scratch?
	
	// blog search
	
	// rss.xml
}
