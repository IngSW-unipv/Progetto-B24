package it.unipv.ingsfw.JavaBeats.controller.factory;


import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.ProfileManager;
import it.unipv.ingsfw.JavaBeats.controller.manager.SearchManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SearchManagerFactory{


  //Attributes
  private static SearchManager searchManager;
  private static final String SEARCHMANAGER_PROPERTYNAME="searchmanager.class.name";
  private static SearchManagerFactory instance=null;

  private SearchManagerFactory(){

  }

  //Singleton
  public static SearchManagerFactory getInstance(){
    if(instance==null){
      instance=new SearchManagerFactory();
    }//end-if
    return instance;
  }


    //Method to get profilemanager
    public SearchManager getSearchManager() {
        if (searchManager == null) {
            String searchManagerClassName;

      try{

                //Obtaining path playermanager
                Properties p = new Properties(System.getProperties());
                p.load(new FileInputStream("Properties/Properties"));
                searchManagerClassName = p.getProperty(SEARCHMANAGER_PROPERTYNAME);


        //JavaReflection
        Constructor c=Class.forName(searchManagerClassName).getConstructor();
        searchManager=(SearchManager)c.newInstance();
      }catch(Exception e){

        e.printStackTrace();
      }
    }

    return searchManager;
  }


}

