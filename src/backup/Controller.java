package backup;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.bono.zero.model.Command;
import com.bono.zero.view.Zero;

public class Controller implements Observer {
	
	private final String PLAYER = "changed: player";
	private final String PLAYLIST = "changed: playlist";
	
	private BlockingQueue<Command> updates;
		
	private Zero zero;
	
	private Updater updater;
	
	private ServerMonitor serverMonitor;
	
	private Communicate communicate;
	
	public Controller(Zero zero) {
		this.zero = zero;
		communicate = zero.getCommunicate();
		updates = new ArrayBlockingQueue<Command>(10);
		updater = new Updater(updates, zero);
		updater.initialize();
		
		serverMonitor = new ServerMonitor(zero, updates);
		serverMonitor.start();
		
		
	}
	
	public void sendCommand(Command command) {
		try {
			communicate.noreturnCommand(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		sendCommand((Command) arg);
	}
		
	public Observer getObserver() {
		return this;
	}

}
