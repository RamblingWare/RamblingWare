package com.amdelamar.objects;

import com.amdelamar.config.Utils;

/**
 * This class represents an Post's View statistics.
 * 
 * @author amdelamar
 * @date 7/04/2017
 */
public class View implements Comparable<View> {

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

    public String get_Rev() {
        return _rev;
    }

    public void set_Rev(String _rev) {
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

    @Override
    public int compareTo(View view) {
        return this._id.compareTo(view._id);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  _id: " + get_Id());
        string.append("\n  _rev: " + get_Rev());
        string.append("\n  count: " + getCount());
        string.append("\n  session: " + getSession());
        return string.toString();
    }

}
