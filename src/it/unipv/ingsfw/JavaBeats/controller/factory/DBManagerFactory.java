package it.unipv.ingsfw.JavaBeats.controller.factory;
import java.util.Properties;
import it.unipv.ingsfw.JavaBeats.controller.manager.DBManager;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
public class DBManagerFactory {
	//Attributi
	private static DBManager dbManager;
	private static final String DBMANAGER_PROPERTYNAME="dbmanager.class.name";
	private static DBManagerFactory instance = null;
	private DBManagerFactory() {}

	//Singleton
	public static DBManagerFactory getInstance() {
		if(instance == null) {
			instance = new DBManagerFactory();
			//System.out.println("Create new instance");
		}
		else {
			//System.out.println("Instance already available");
			}
		return instance;
	}

	//Metodo per avere dbmanager
	public DBManager getDBManager() {
		if(dbManager==null) {
			String dbManagerClassName;
			try {
				//Ottengo path dbmanager
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("Properties/Properties"));
				dbManagerClassName=p.getProperty(DBMANAGER_PROPERTYNAME);

				//JavaReflection
				Constructor c = Class.forName(dbManagerClassName).getConstructor();
				dbManager=(DBManager) c.newInstance();
			}catch (Exception e) {
				e.printStackTrace();
			}//end-try
		}//end-if
		return dbManager;
	}
}