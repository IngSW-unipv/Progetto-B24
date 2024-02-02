package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;

public class InvalidJBEntityException extends Exception {

    //CONSTRUCTOR:
    public InvalidJBEntityException() {
        super("Invalid or not recognized JB object type.");
    }


    //METHODS:
    public String getClassName(IJBResearchable JBEntity) {
        return JBEntity.getClass().getName();
    }

}
