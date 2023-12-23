package it.unipv.ingsfw.javabeats.model.playable;

import javax.sql.rowset.serial.SerialBlob;

import it.unipv.ingsfw.javabeats.model.EJBMODE;
import it.unipv.ingsfw.javabeats.model.user.IJBProfile;

public interface IJBCollection extends IJBPlayable {
	public int itemAmount;
	public SerialBlob picture;
	
	public void addItem(IJBProfile activeJBProfile, IJBItem item);
	public void removeItem(IJBProfile activeJBProfile, IJBItem item);
	public void play(EJBMODE mode);
	public void play();
}
