package com.rant.objects;

/**
 * Tag class
 * 
 * @author Austin Delamar
 * @created 7/13/2017
 */
public class Tag implements Comparable<Tag> {

    private String _id;
    private String _rev;
    private String name;
    private int count;

    public String get_Id() {
        return _id;
    }

    public void set_Id(String id) {
        this._id = id;
    }

    public String get_Rev() {
        return _rev;
    }

    public void set_Rev(String _rev) {
        this._rev = _rev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(Tag tag) {
        return this.name.compareTo(tag.name);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  _id: " + get_Id());
        string.append("\n  _rev: " + get_Rev());
        string.append("\n  name: " + getName());
        string.append("\n  count: " + getCount());
        return string.toString();
    }

}
