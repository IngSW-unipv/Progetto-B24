package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.PlayerManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PlayerManagerFactory {


	//Attributi
	private static PlayerManager playerManager;
	private static final String PLAYERMANAGER_PROPERTYNAME="playermanager.class.name";
	private static PlayerManagerFactory instance = null;


	private PlayerManagerFactory() {

	}

	//Singleton
	public static PlayerManagerFactory getInstance() {
		if(instance == null) {
			instance = new PlayerManagerFactory();
			System.out.println("Create new instance");
		}
		else
			System.out.println("Instance already available");
		return instance;
	}


	//Metodo per avere playermanager
	public static PlayerManager getPlayerManager() {
		if(playerManager==null) {
			String playerManagerClassName;

			try {

				//Ottengo path playermanager
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("Properties/Properties"));
				playerManagerClassName=p.getProperty(PLAYERMANAGER_PROPERTYNAME);


				//JavaReflection
				Constructor c = Class.forName(playerManagerClassName).getConstructor();
				playerManager=(PlayerManager) c.newInstance();
			}


			catch (Exception e) {

				e.printStackTrace();
			} 
		}

		return playerManager;
	}





}
