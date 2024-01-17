package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.playable.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.playable.IAudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileManager{

  //Attributi

  private static JBProfile activeProfile;


  //Getters and Setters

  public static JBProfile getActiveProfile(){
    return activeProfile;
  }


  //Metodi


  //Login
  //Propagates exception from dao
  public JBProfile login(JBProfile profile) throws IllegalArgumentException{
    ProfileDAO p=new ProfileDAO();
    activeProfile=p.get(profile);
    return activeProfile;
  }

  //Registration
  //Propagates exception from dao
  public JBProfile registration(JBProfile profile){
    ProfileDAO p=new ProfileDAO();
    /* Default profile image when inserting */
    try{
      BufferedImage bufferedImage=ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png"));
      ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
      byte[] image=byteArrayOutputStream.toByteArray();
      profile.setProfilePicture(new SerialBlob(image));
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }
    profile.setBiography("");

    p.insert(profile);
    activeProfile=p.get(profile);
    return activeProfile;
  }

  public Artist switchUser(User user) throws ClassCastException{
    ProfileDAO profileDAO=new ProfileDAO();
    Artist artist=new Artist(user.getUsername(), user.getMail(), user.getPassword(), user.getName(), user.getSurname(), user.getBiography(), user.getProfilePicture(), 0, user.getListeningHistory(), user.getFavorites());
    profileDAO.remove(user);
    profileDAO.insert(artist);

    AudioDAO audioDAO=new AudioDAO();
    audioDAO.updateIsFavorite(artist);
    for(JBAudio jbAudio: artist.getListeningHistory()){
      audioDAO.addToListeningHistory(jbAudio, artist);
    }//end-foreach

    activeProfile=profileDAO.get(artist);

    Sidebar.setInstanceNull();
    SidebarHandler.setInstanceNull();
    Songbar.setInstanceNull();


    return (Artist)activeProfile;
  }
}
