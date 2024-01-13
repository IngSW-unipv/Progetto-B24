package it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.ScrollPaneSkin;

public class ScrollPaneSkinPreset extends ScrollPaneSkin{

  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ScrollPaneSkinPreset(ScrollPane scroll){
    super(scroll);
    registerChangeListener(scroll.hbarPolicyProperty(), p -> {
      getHorizontalScrollBar().setVisible(false);
    });
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  @Override
  protected double computePrefHeight(double x, double topInset, double rightInset, double bottomInset, double leftInset){
    double computed=super.computePrefHeight(x, topInset, rightInset, bottomInset, leftInset);
    if(getSkinnable().getHbarPolicy()==ScrollPane.ScrollBarPolicy.AS_NEEDED && getHorizontalScrollBar().isVisible()){
      // this is fine when horizontal bar is shown/hidden due to resizing
      // not quite okay while toggling the policy
      // the actual visibilty is updated in layoutChildren?
      computed+=getHorizontalScrollBar().prefHeight(-1);
    }
    return computed;
  }
  /*---------------------------------------*/
}
