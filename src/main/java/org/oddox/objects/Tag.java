package org.oddox.objects;

/**
 * Tag class
 * 
 * @author Austin Delamar
 * @created 7/13/2017
 */
public class Tag implements Comparable<Tag> {

    private String name;
    private int count;

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
        string.append("  name: " + getName());
        string.append("\n  count: " + getCount());
        return string.toString();
    }

}
