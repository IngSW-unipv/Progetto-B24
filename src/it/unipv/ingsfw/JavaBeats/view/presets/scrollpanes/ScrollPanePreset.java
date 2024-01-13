package it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;

public class ScrollPanePreset extends ScrollPane{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ScrollPanePreset(){
    super();
  }

  public ScrollPanePreset(Node node){
    super(node);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  @Override
  protected Skin<?> createDefaultSkin(){
    return new ScrollPaneSkinPreset(this);
  }
  /*---------------------------------------*/
}
