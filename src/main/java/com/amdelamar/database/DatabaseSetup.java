package com.amdelamar.database;

/**
 * A blueprint to communicate to a Database Setup.
 * 
 * @author amdelamar
 * @date 9/03/2017
 */
public abstract class DatabaseSetup {

    protected Database database;

    public DatabaseSetup(Database database) {
        this.database = database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public abstract boolean setup();

}
