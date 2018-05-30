package org.oddox.database;

/**
 * A blueprint to communicate to a Database Setup.
 * 
 * @author amdelamar
 * @date 9/03/2017
 */
public abstract class DatabaseSetup {

    protected org.oddox.database.Database database;

    public DatabaseSetup(org.oddox.database.Database database) {
        this.database = database;
    }

    public void setDatabase(org.oddox.database.Database database) {
        this.database = database;
    }

    public org.oddox.database.Database getDatabase() {
        return database;
    }

    public abstract boolean setup();

}
