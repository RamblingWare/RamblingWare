package org.rw.database;

import java.util.ArrayList;

import org.rw.action.model.Author;
import org.rw.action.model.Database;
import org.rw.action.model.Post;

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
	public Post newPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editPost(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePost(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Author getAuthor(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editAuthor(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAuthor(Author author) {
		// TODO Auto-generated method stub
		return false;
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
	public ArrayList<String> getPostUris() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPosts(int page, int limit, boolean includeHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Author> getAuthors(int page, int limit, boolean includeAdmins) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author newUser(Author user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editUser(Author user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loginUser(Author user) {
		// TODO Auto-generated method stub
		return false;
	}
}