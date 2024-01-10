package it.unipv.ingsfw.JavaBeats.view.primary.profile;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
public class ProfileViewGUI{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private ProfileHeader profileHeader;
  private ProfileDefault profileDefault;
  private GridPane gp;
  private Scene scene;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileViewGUI(JBProfile activeProfile, JBProfile searchedProfile){
    initComponents(activeProfile, searchedProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Scene getScene(){
    return scene;
  }
  public ProfileHeader getProfileHeader(){
    return profileHeader;
  }
  public ProfileDefault getProfileDefault(){
    return profileDefault;
  }
  public GridPane getGp(){
    return gp;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, JBProfile searchedProfile){
    /* Header and main components */
    profileHeader=new ProfileHeader(searchedProfile);
    profileDefault=new ProfileDefault(activeProfile, searchedProfile);

    ScrollPane mainContent=new ScrollPane(new VBox(profileHeader, profileDefault));
    mainContent.setPadding(new Insets(20, 20, 0, 20));
    mainContent.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF;");
    mainContent.setFitToWidth(true);

    gp=new GridPane();
    gp.addRow(0, Sidebar.getInstance(), mainContent);
    gp.add(Songbar.getInstance(), 0, 1, 2, 1);

    ColumnConstraints ccSidebar=new ColumnConstraints();
    ColumnConstraints ccHome=new ColumnConstraints();
    ccSidebar.setPercentWidth(20);
    ccHome.setPercentWidth(80);
    gp.getColumnConstraints().addAll(ccSidebar, ccHome);

    RowConstraints rcSongbar=new RowConstraints();
    RowConstraints rcSideHome=new RowConstraints();
    rcSongbar.setPercentHeight(12);
    rcSideHome.setPercentHeight(88);
    gp.getRowConstraints().addAll(rcSideHome, rcSongbar);

    scene=new Scene(gp, clientWidth, clientHeight);
    scene.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/Profile.css");
  }
  /*---------------------------------------*/
}
