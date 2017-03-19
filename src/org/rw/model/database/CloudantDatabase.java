package org.rw.model.database;

import java.util.ArrayList;

import org.rw.bean.Author;
import org.rw.bean.Database;
import org.rw.bean.Post;

public class CloudantDatabase extends DatabaseSource {

	public CloudantDatabase(Database database) {
		super(database);
	}

	@Override
	public Post getPost(String uri, boolean includeHidden) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getArchiveYears() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getArchiveTags() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Post> getArchiveRecent(String[] uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPostsByTag(int page, int limit, String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Author> getAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

}
