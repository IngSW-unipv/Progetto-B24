package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

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
    try {
      BufferedImage bufferedImage = ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png"));
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
      byte[] image = byteArrayOutputStream.toByteArray();
      profile.setProfilePicture(new SerialBlob(image));
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }
    profile.setBiography("");

    p.insert(profile);
    activeProfile=profile;
    return activeProfile;
  }
}
