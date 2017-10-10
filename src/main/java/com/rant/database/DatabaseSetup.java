package com.rant.database;

/**
 * A blueprint to communicate to a Database Setup.
 * 
 * @author Austin Delamar
 * @date 9/03/2017
 */
public abstract class DatabaseSetup {
    
    protected com.rant.objects.Database database;

    public DatabaseSetup(com.rant.objects.Database database) {
        this.database = database;
    }

    public void setDatabase(com.rant.objects.Database database) {
        this.database = database;
    }

    public com.rant.objects.Database getDatabase() {
        return database;
    }

    public abstract boolean test();

    public abstract boolean verify();

    public abstract boolean install();
    
    public abstract boolean securityCheck();

}
