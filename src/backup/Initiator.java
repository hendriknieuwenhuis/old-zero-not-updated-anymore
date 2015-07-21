package backup;

import java.io.IOException;

import com.bono.zero.model.Settings;
import com.bono.zero.view.SettingsDialog;


/**
 * <p>Title: Initiator.java</p>
 * 
 * <p>Description: This class sets up the communication. It contains a
 * sequence of methods that loads and tests the settings for setting up 
 * a connection to the server. If there are  no settings it will ask 
 * the user for the settings. When found settings are incorrect it 
 * will ask the user for the correct settings.</p>
 * <p>If a working connection to the server is established the class
 * will return the settings object and save the settings.</p> 
 * 
 * @author bono
 * 
 * @version 1.0
 */
public class Initiator {
	
	private Settings settings;
	private Communicate communicate;
	
	boolean wait;
	
	public Initiator(Communicate communicate) {
		this.communicate = communicate;
		init();
		
	}
	
	private void init() {
		
		try {
			settings = Settings.loadSettings();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		} catch (IOException e) {
			newSettings();
		}
		testSettings();
	}
	
	private void newSettings() {
		if (settings == null) {
			settings = Settings.getSettings();
		}
		new SettingsDialog(settings, this);
		wait = true;
		while (wait) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		testSettings();
	}
	
	private void testSettings() {
		communicate.setAddress(settings.getAddress());
		if (!communicate.ping()) {
			newSettings();
			//return;
		}
		saveSettings();
	}
	
	private void saveSettings() {
		try {
			settings.saveSettings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Communicate getCommunicate() {
		return communicate;
	}
	
	public void stopWaiting() {
		synchronized (this) {
			wait = false;
			notify();
		}
	}
	
	

}
