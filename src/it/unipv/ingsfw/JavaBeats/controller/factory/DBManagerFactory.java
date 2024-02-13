package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.DBManager;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;

public class DBManagerFactory {
    //Attributes
    private static DBManager dbManager;
    private static final String DBMANAGER_PROPERTYNAME = "dbmanager.class.name";
    private static DBManagerFactory instance = null;

    private DBManagerFactory() {
    }

    //Singleton
    public static DBManagerFactory getInstance() {
        if (instance == null) {
            instance = new DBManagerFactory();
        }//end-if
        return instance;
    }

    //Method to get dbmanager
    public DBManager getDBManager() {
        if (dbManager == null) {
            String dbManagerClassName;
            try {
                //Obtaining path for dbmanager
                Properties p = new Properties(System.getProperties());
                p.load(new FileInputStream("Properties/Properties"));
                dbManagerClassName = p.getProperty(DBMANAGER_PROPERTYNAME);

                //JavaReflection
                Constructor c = Class.forName(dbManagerClassName).getConstructor();
                dbManager = (DBManager) c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }//end-try
        }//end-if
        return dbManager;
    }
}