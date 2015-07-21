package backup;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


import com.bono.zero.model.Command;
import com.bono.zero.view.Zero;



/**
 * Class to update the ServerStatus by using a seperate Thread
 **/
public class Updater {
	
	// String prefixes messages send by server, property
	// changed in the server.
	private final String PLAYER   = "changed: player";
	//private final String PLAYLIST = "changed: playlist";
	private final String MIXER    = "changed: mixer";
	private final String OPTIONS  = "changed: options";
	private final String STATUS   = "status";
	private final String PLAYLIST = "playlistinfo";

	private Communicate communicate;
	private ExecutorService executor;
	
	
	private BlockingQueue<Command> queue;
	
	private Zero zero;
	
	public Updater(BlockingQueue<Command> queue, Zero zero) {
		this.queue = queue;
		this.communicate = zero.getCommunicate();
		this.zero = zero;
		initThreads();
		
	}
	
	public void initialize() {
		update(new Command(STATUS));
		update(new Command(PLAYLIST));
	}
	
	/*
	 * Method creates a fixed threadpool that run the Runnable
	 * inner classes run the updates for the DataModel.
	 */
	private void initThreads() {
		executor = Executors.newFixedThreadPool(4);
		executor.execute(new Consumer(queue));
	}
	
	/*
	 * Method that updates the DataModel when
	 * a change in the server is monitored by
	 * the ServerMonitor Object.
	 */
	public void update(Command command) {
		// When player is changed update the server status.
		if (command.getCommand().equals(STATUS)) {
			executor.execute(new StatusUpdater(command));
			
		// When mixer is changed update the server status.
		//} else if (changed.equals(MIXER)) {
		//	executor.execute(new StatusUpdater());
			
		// When playlist is changed update playlist and server status.	
		} else if (command.getCommand().equals(PLAYLIST)) {
			executor.execute(new PlaylistUpdater(command));
			executor.execute(new StatusUpdater(new Command(STATUS)));
			
		// When options are changed update the server status.	
		//} else if (changed.equals(OPTIONS)) {
		//	executor.execute(new StatusUpdater());
		}
		
	}
	
	/**
	 * Inner class StatusUpdater implements Runnable. This class
	 * runs connects to the server and asks for the status of the 
	 * server. Then it updates the ServerStatus of the DataModel
	 * with the current status of the server.
	 **/
	private class StatusUpdater implements Runnable {
		
		private Command command;
		
		public StatusUpdater(Command command) {
			this.command = command;
		}
		@Override
		public void run() {
			// code to connect to server and update 
			// the ServerStatus in the DataModel.
			String[] status = null;
			try {
				status = communicate.sendCommand(command);
				
				zero.getServerStatus().populate(status);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			status = null;
		}

	}

	/**
	 * Inner class PlaylistUpdater implements Runnable. This class
	 * runs connects to the server and asks for the current Playlist
	 * that is used by the server. Then it updates the playlist of the 
	 * DataModel with the current playlist played by the server.
	 **/
	private class PlaylistUpdater implements Runnable {
		
		private Command command;
		
		public PlaylistUpdater(Command command) {
			this.command = command;
		}

		@Override
		public void run() {
			// code to connect to server and update
			// the playlist in the DataModel.
			String[] list = null;
			try {
				list = communicate.sendCommand(command);
				if (list.length == 0) {
					zero.getPlaylist().emptyPlaylist();
				}
				
				zero.getPlaylist().populatePlaylist(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
			list = null;
		}
	}
	
	private class Consumer implements Runnable {
		
		private BlockingQueue<Command> queue;

		public Consumer(BlockingQueue<Command> queue) {
			this.queue =queue;
		}
		@Override
		public void run() {
			
			while (true) {
				try {
					
					consume(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		private void consume(Command command) {
			update(command);
		}
	}
}
