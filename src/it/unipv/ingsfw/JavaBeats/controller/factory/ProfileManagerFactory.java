package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.ProfileManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProfileManagerFactory {



	//Attributi
	private static ProfileManager profileManager;
	private static final String PROFILEMANAGER_PROPERTYNAME="profilemanager.class.name";
	private static ProfileManagerFactory instance = null;

	private ProfileManagerFactory() {

	}

	//Singleton
	public static ProfileManagerFactory getInstance() {
		if(instance == null) {
			instance = new ProfileManagerFactory();
			System.out.println("Create new instance");
		}
		else
			System.out.println("Instance already available");
		return instance;
	}


	//Metodo per avere profilemanager
	public ProfileManager getProfileManager() {
		if(profileManager==null) {
			String profileManagerClassName;

			try {

				//Ottengo path playermanager
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("Properties/Properties"));
				profileManagerClassName=p.getProperty(PROFILEMANAGER_PROPERTYNAME);


				//JavaReflection
				Constructor c = Class.forName(profileManagerClassName).getConstructor();
				profileManager=(ProfileManager) c.newInstance();
			}


			catch (Exception e) {

				e.printStackTrace();
			} 
		}

		return profileManager;
	}


}
