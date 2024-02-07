package it.unipv.ingsfw.JavaBeats.exceptions;
public class InvalidNameException extends RegexException{
  public InvalidNameException(){
    super("Invalid Name or Surname fields.");
  }
  @Override
  public String suggestAlternative(){
    return "Name and Surname fields must start with capital letter.";
  }
}
