package it.unipv.ingsfw.javabeats.model.user;

import javax.sql.rowset.serial.SerialBlob;

public interface IJBProfile {
	public String username, mail, password, name, surname, biography;
	public SerialBlob profilePicture;
}
