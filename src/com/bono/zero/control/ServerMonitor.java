package com.bono.zero.control;

/**
 * Class ServerStatusMonitorr creates a worker thread
 * and sets up an idle connection with the MPD Server
 * to monitor to changes in the server.
 */
import java.net.SocketException;
import java.net.Socket;
import java.io.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bono.zero.model.Settings;


public class ServerMonitor {
	
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
	
	//private UpdateModel updateModel;
	private Settings settings;

    private UpdaterController updaterController;

    private Object lock = new Object();
		
	public ServerMonitor(UpdaterController updaterController) {
		this.updaterController = updaterController;
        updaterController.initModels();
		settings = Settings.getSettings();
		setupServerMonitor();
	}
	
	/*
	 * Method that instantiates the thread pool that
	 * this class uses for monitoring the server and
	 * running for updates when changes occur in the
	 * server. The updates are Runnable objects that
	 * are run in the update() method.
	 */
	private void setupServerMonitor() {
		// instantiate the executors
		executor = Executors.newSingleThreadExecutor();
		// instantiate the runnables.
		// serverWorker = new ServerWorker();
		// init the models
        // updaterController.initModels();
        // TODO make so that after a restart the models don't reload
		
	}
	
	/*
	 * Start method creates the thread for the
	 * worker class to run in.
	 */
	public void start() {
        serverWorker = new ServerWorker();
        executor.execute(serverWorker);
    }

    /**
     * Stops the worker thread of the monitor.
     * TODO shit
     */
    public void stop() {
        System.out.println("stop called begin");
        closed = true;
        System.out.println("closed true");
        reader = null;
        System.out.println("reader closed");
        serverWorker.setRunning(false);
        System.out.println("stop called");
    }

    /**
     * Initiates a new worker and starts it.
     * TODO shit
     */
    public void restart() {
        serverWorker = new ServerWorker();
        start();
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
				// TODO tries++;
			
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
					updaterController.update(read);
					//System.out.println(read);
				}
			}
			
		} catch (SocketException s) {
			closed = true;
		} catch (IOException e) {
			e.printStackTrace();
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
            System.out.println("stopped");
		}

		public void goWait() {
			waiting = true;
			
			synchronized (lock) {
				while(waiting) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

        public void setRunning(boolean run) {
            this.run = run;
        }
	}
}
