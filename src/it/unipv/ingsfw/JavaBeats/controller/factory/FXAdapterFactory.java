package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.adapter.IAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FXAdapterFactory {

	private static FXAdapterFactory instance = null;

	//Attributi
	private static FXAdapter fxAdapter;
	private static final String FXADAPTER_PROPERTYNAME="fxadapter.class.name";

	private FXAdapterFactory() {

	}

	//Singleton
	public static FXAdapterFactory getInstance() {
		if(instance == null) {
			instance = new FXAdapterFactory();
			System.out.println("Create new instance");
		}
		else
			System.out.println("Instance already available");
		return instance;
	}

	//Metodo per avere fxadapter
	public FXAdapter getFXAdapter() {
		if(fxAdapter==null) {
			String fxAdapterClassName;

			try {

				//Ottengo path fxadapter
				Properties p = new Properties(System.getProperties());
				p.load(new FileInputStream("Properties/Properties"));
				fxAdapterClassName=p.getProperty(FXADAPTER_PROPERTYNAME);

				//JavaReflection
				Constructor c = Class.forName(fxAdapterClassName).getConstructor();
				fxAdapter=(FXAdapter) c.newInstance();
			}


			catch (Exception e) {

				e.printStackTrace();
			} 
		}

		return fxAdapter;
	}



}
