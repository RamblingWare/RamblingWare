package com.rant.model;

import com.rant.config.Utils;

/**
 * This class represents an Post's View statistics.
 * 
 * @author Austin Delamar
 * @created 7/04/2017
 */
public class View {

    private long count;
    private long session;
    
    public long getCount() {
        return count;
    }
    
    public String getCountReadable() {
        return Utils.formatLong(count);
    }
    
    public void setCount(long count) {
        this.count = count;
    }
    
    public long getSession() {
        return session;
    }
    
    public String getSessionReadable() {
        return Utils.formatLong(session);
    }
    
    public void setSession(long session) {
        this.session = session;
    }

}
