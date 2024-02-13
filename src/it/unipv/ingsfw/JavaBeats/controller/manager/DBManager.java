package it.unipv.ingsfw.JavaBeats.controller.manager;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBManager {

    private final String PROPERTYDBDRIVER = "DBDRIVER";
    private final String PROPERTYDBURL = "DBURL";
    private final String PROPERTYNAME = "db_usn";
    private final String PROPERTYPSW = "db_psw";
    private String username;
    private String password;
    private String dbDriver;
    private String dbURL;


    public DBManager() {
        Properties p = new Properties(System.getProperties());
        try {
            p.load(new FileInputStream("Properties/Properties"));
            username = p.getProperty(PROPERTYNAME);
            password = p.getProperty(PROPERTYPSW);
            dbDriver = p.getProperty(PROPERTYDBDRIVER);
            dbURL = p.getProperty(PROPERTYDBURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection startConnection(Connection c, String schema) {

        //Checking if the connection is open, if so it is closed
        if (isOpen(c)) {
            closeConnection(c);
        }

        try {

            dbURL = String.format(dbURL, schema);
            Class.forName(dbDriver);

            //Opening connection
            c = DriverManager.getConnection(dbURL, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }

    //Checking if connection is open
    public boolean isOpen(Connection c) {
        if (c == null)
            return false;
        else
            return true;
    }

    //Closing the connection
    public Connection closeConnection(Connection c) {
        if (!isOpen(c)) {
            return null;
        }

        try {

            c.close();
            c = null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }
}
