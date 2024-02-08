package it.unipv.ingsfw.JavaBeats.exceptions;
public class InvalidAudioException extends Exception implements IJBException{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Constructors
  /*---------------------------------------*/
  public InvalidAudioException(){
    super("An error occurred with your audio!");
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  @Override
  public String suggestAlternative(){
    return "Check your metadata and try again";
  }
  /*---------------------------------------*/
}
