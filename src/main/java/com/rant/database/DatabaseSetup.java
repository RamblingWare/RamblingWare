package com.rant.database;

/**
 * A blueprint to communicate to a Database Setup.
 * 
 * @author Austin Delamar
 * @date 9/03/2017
 */
public abstract class DatabaseSetup {

    protected com.rant.database.Database database;

    public DatabaseSetup(com.rant.database.Database database) {
        this.database = database;
    }

    public void setDatabase(com.rant.database.Database database) {
        this.database = database;
    }

    public com.rant.database.Database getDatabase() {
        return database;
    }

    public abstract boolean setup();

}
