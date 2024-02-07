package it.unipv.ingsfw.JavaBeats.exceptions;
public abstract class RegexException extends Exception implements IJBException{
  public RegexException(String s){
    super(s);
  }
}
