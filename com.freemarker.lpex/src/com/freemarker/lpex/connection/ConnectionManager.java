package com.freemarker.lpex.connection;

public final class ConnectionManager {

    /**
     * The instance of this Singleton class.
     */
    private static ConnectionManager instance;

    /**
     * Private constructor to ensure the Singleton pattern.
     */
    private ConnectionManager() {
    }

    /**
     * Thread-safe method that returns the instance of this Singleton class.
     */
    public synchronized static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

}