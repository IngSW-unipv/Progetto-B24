package it.unipv.ingsfw.JavaBeats.controller.factory;

import java.util.Properties;

import it.unipv.ingsfw.JavaBeats.controller.manager.PlayerManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PlayerManagerFactory{


  //Attributes
  private static PlayerManager playerManager;
  private static final String PLAYERMANAGER_PROPERTYNAME="playermanager.class.name";
  private static PlayerManagerFactory instance=null;


  private PlayerManagerFactory(){

  }

  //Singleton
  public static PlayerManagerFactory getInstance(){
    if(instance==null){
      instance=new PlayerManagerFactory();
    }//end-if
    return instance;
  }


    //Method to get playermanager
    public PlayerManager getPlayerManager() {
        if (playerManager == null) {
            String playerManagerClassName;

      try{

                //Obtaining path for playermanager
                Properties p = new Properties(System.getProperties());
                p.load(new FileInputStream("Properties/Properties"));
                playerManagerClassName = p.getProperty(PLAYERMANAGER_PROPERTYNAME);


        //JavaReflection
        Constructor c=Class.forName(playerManagerClassName).getConstructor();
        playerManager=(PlayerManager)c.newInstance();
      }catch(Exception e){

        e.printStackTrace();
      }
    }

    return playerManager;
  }


}
