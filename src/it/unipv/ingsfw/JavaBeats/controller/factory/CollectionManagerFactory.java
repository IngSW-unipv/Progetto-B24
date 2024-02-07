package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.CollectionManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CollectionManagerFactory{


  //Attributi
  private static CollectionManager collectionManager;
  private static final String COLLECTIONMANAGER_PROPERTYNAME="collectionmanager.class.name";
  private static CollectionManagerFactory instance=null;

  private CollectionManagerFactory(){

  }

  //Singleton
  public static CollectionManagerFactory getInstance(){
    if(instance==null){
      instance=new CollectionManagerFactory();
    }//end-if
    return instance;
  }


  //Metodo per avere collectionmanager
  public CollectionManager getCollectionManager(){
    if(collectionManager==null){
      String collectionManagerClassName;

      try{

        //Ottengo path playermanager
        Properties p=new Properties(System.getProperties());
        p.load(new FileInputStream("Properties/Properties"));
        collectionManagerClassName=p.getProperty(COLLECTIONMANAGER_PROPERTYNAME);


        //JavaReflection
        Constructor c=Class.forName(collectionManagerClassName).getConstructor();
        collectionManager=(CollectionManager)c.newInstance();
      }catch(Exception e){

        e.printStackTrace();
      }
    }

    return collectionManager;
  }


}
