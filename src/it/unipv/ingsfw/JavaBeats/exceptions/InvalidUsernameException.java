package it.unipv.ingsfw.JavaBeats.exceptions;
public class InvalidUsernameException extends RegexException{
  public InvalidUsernameException(){
    super("Inserter invalid username field");
  }
  @Override
  public String suggestAlternative(){
    return "Allowed characters in Username are a-z, A-Z, 0-9, ., _, +, -.";
  }
}
