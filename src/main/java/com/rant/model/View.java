package com.rant.model;

import com.rant.config.Utils;

/**
 * This class represents an Post's View statistics.
 * 
 * @author Austin Delamar
 * @created 7/04/2017
 */
public class View {

    private String _id;
    private String _rev;
    private long count = 0l;
    private long session = 0l;

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

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
