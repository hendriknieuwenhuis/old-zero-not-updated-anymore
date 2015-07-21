package backup;

/**
 * Class ServerStatusMonitorr creates a worker thread
 * and sets up an idle connection with the MPD Server
 * to monitor to changes in the server.
 */
import java.net.SocketException;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bono.zero.model.Command;
import com.bono.zero.model.Settings;
import com.bono.zero.view.Zero;

public class ServerMonitor {
	
	private final String PLAYER   = "changed: player";
	private final String PLAYLIST = "changed: playlist";
	private final String MIXER    = "changed: mixer";
	private final String OPTIONS  = "changed: options";		
	private final byte[] IDLE = "idle\n".getBytes();
	private final byte[] CLEAR_ERROR = "clearerror\n".getBytes();
	private final String OK               = "OK";
	private final String ACK              = "ACK";
	
	private boolean closed;
	
	private boolean waiting;
	private int tries;
	
	private ExecutorService executor;
	private ServerWorker serverWorker;

	private Socket socket;
	private OutputStream writer;
	private BufferedReader reader;
	private InputStreamReader input;
	private String version;
	private String line;
	
	private BlockingQueue<Command> queue;
	private Settings settings;
	

	public ServerMonitor(Zero zero, BlockingQueue queue) {
		this.settings = zero.getSettings();
		this.queue = queue;
		setupServerMonitor();
	}

	private void setupServerMonitor() {
		executor = Executors.newSingleThreadExecutor();
		serverWorker = new ServerWorker();
	}
	/*
	 * Start method creates the thread for the
	 * worker class to run in.
	 */
	public void start() {
		executor.execute(serverWorker);
	}
	
	// connect to the server.
	private String connect() {
				
		tries = 0;
		
		while (tries <3) {
			try {
				socket = new Socket();
				socket.connect(settings.getAddress());
				socket.setKeepAlive(true);
				input = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(input);
				line = reader.readLine();
				if (socket.isConnected()) {
					//closed = false;
					if (line.startsWith(ACK)) {
						writeCommand(CLEAR_ERROR);
						return line;
					} else {
						closed = false;
						//System.out.println(line+" "+closed);
						return line;
					}
				}
				//tries++;
			
			} catch (SocketException s) {
				tries++;
				writer = null;
				System.out.println("socket exception!");
				if (tries == 2) {
					serverWorker.goWait();
				} else {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException i) {
						i.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// write message to the server
	private void writeCommand(byte[] command) {
		if (socket == null) {
			closed = true;
		}
		try {
			if (writer == null) {
				writer = socket.getOutputStream();
			}
			writer.write(command);
			writer.flush();
		} catch (SocketException s) {
			closed = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// reads the servers messages.
	private void listen() {

		String read;

		try {
			if ((input == null) || (reader == null)) {
				input = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(input);
			}
			while ((read = reader.readLine()) != null) {
				if (read.startsWith("ACK")) {
					System.out.println(read);
					writeCommand(CLEAR_ERROR);
					closed = true;
					break;
				} else if (read.equals(OK)) {
					break;
				} else {
					try {
						putCommand(read);
						//System.out.println(read);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (SocketException s) {
			closed = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Method putCommand. This method creates a new command object and
	 * puts it in the queue. The command object is created according to
	 * the read out of the server. 
	 * The method throws an InterruptedException when the command
	 * object can't be put.
	 * @param String read, the server message.  
	 */
	private void putCommand(String read) throws InterruptedException {
		
		if ((read.equals(PLAYER)) || (read.equals(MIXER)) || (read.equals(OPTIONS))) {
			queue.put(new Command("status"));
		} else if (read.equals(PLAYLIST)) {
			queue.put(new Command("playlistinfo"));
		}
	}

	
	/*
	 * Inner class, the worker class. This class
	 * runs as long as the program runs. It listens
	 * to an idle connection with the server and responds
	 * to its changes.
	 * */
	private class ServerWorker implements Runnable {
		
		private boolean run;

		@Override
		public void run() {
			run = true;
			
			version = connect();
			System.out.println(version);
			
			while (run) {
				if (closed) {
					version = connect();
				}
				
				while (!closed) {
					
					writeCommand(IDLE);

					listen();

					
				}
			}
		}

		public void goWait() {
			waiting = true;
			
			synchronized (this) {
				while(waiting) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
