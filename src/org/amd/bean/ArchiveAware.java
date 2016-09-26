package org.amd.bean;

import java.util.ArrayList;

/**
 * Archive Aware class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public interface ArchiveAware {
	 
    public void setArchive_years(ArrayList<String> archive_years);
    public void setArchive_tags(ArrayList<String> archive_tags);
}
