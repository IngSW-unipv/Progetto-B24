package it.unipv.ingsfw.JavaBeats.model.user;

import javax.sql.rowset.serial.SerialBlob;

public interface IJBProfile {
	public String username = null, mail = null, password = null, name = null, surname = null, biography = null;
	public SerialBlob profilePicture = null;
}
