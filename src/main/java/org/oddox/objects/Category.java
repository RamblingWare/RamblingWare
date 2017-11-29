package org.oddox.objects;

/**
 * Category class
 * 
 * @author Austin Delamar
 * @created 7/13/2017
 */
public class Category implements Comparable<Category> {

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
    public int compareTo(Category cat) {
        return this.name.compareTo(cat.name);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  name: " + getName());
        string.append("\n  count: " + getCount());
        return string.toString();
    }
}
