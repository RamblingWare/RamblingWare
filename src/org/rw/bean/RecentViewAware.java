package org.rw.bean;

import java.util.ArrayList;

/**
 * RecentView Aware class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public interface RecentViewAware {
	 
    public void setRecent_view(ArrayList<Post> recent_view);
}
