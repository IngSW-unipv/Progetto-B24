package it.unipv.ingsfw.JavaBeats.model.playable;

import javax.sql.rowset.serial.SerialBlob;
import it.unipv.ingsfw.JavaBeats.model.EJBMODE;
import it.unipv.ingsfw.JavaBeats.model.user.IJBProfile;

public interface IJBCollection extends IJBPlayable {
	public int itemAmount = 0;
	public SerialBlob picture = null;
	
	public void addItem(IJBProfile activeJBProfile, IJBItem item);
	public void removeItem(IJBProfile activeJBProfile, IJBItem item);
	public void play(EJBMODE mode);
	public void play();
}
