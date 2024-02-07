package it.unipv.ingsfw.JavaBeats.exceptions;

public class SystemErrorException extends Exception implements IJBException{
  public SystemErrorException(){
    super("A system error occurred");
  }
  @Override
  public String suggestAlternative(){
    return "Please try again";
  }
}
