package com.bono.zero.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.bono.zero.model.Command;

/**
 * <p>Title: Server.java</p>
 * 
 * <p>Description: Class Server establishes the connection
 * to the MPDserver. It send command objects to the server
 * and reads its response.
 * @author bono
 *
 */
public class Server {
	
	/**
	 * Connection commands:
	 */
	public static final String CLEAR_ERROR     = "clearerror";
	public static final String CLOSE           = "close";
	public static final String STATUS          = "status";
	public static final String STATISTICS      = "stats";
	public static final String START_COM_LIST  = "command_list_ok_begin";
	public static final String END_COM_LIST    = "command_list_end";
	public static final String PASSWORD        = "password";
	public static final String PING            = "ping";
	
	/**
	 * Responses:
	 */
	public static final String OK              = "OK";
	public static final String ERROR           = "ACK";
	
	/**
	 * Admin commands:
	 */
	public static final String DISABLE_OUT     = "disableoutput";
	public static final String ENABLE_OUT      = "enableoutput";
	public static final	String KILL            = "kill";
	public static final String OUTPUTS         = "outputs";
	public static final String REFRESH         = "update";
	public static final String COMMANDS        = "commands";
	public static final String NOT_COMMANDS    = "notcommands";
	
	/**
	 * Database commands:
	 */
	public static final String FIND            = "find";
	public static final String LIST_TAG        = "list";
	public static final	String LIST_ALL        = "listall";
	public static final String LIST_ALL_INFO   = "listallinfo";
	public static final String LIST_INFO       = "lsinfo";
	public static final String SEARCH          = "search";
	public static final String LIST_SONGS      = "listplaylist";

	/**
	 * Playback commands:
	 */
	public static final String CROSSFADE       = "crossfade";
	public static final String CURRENTSONG     = "currentsong";
	public static final String CONSUME         = "consume";
	public static final String NEXT            = "next";
	public static final String PAUSE           = "pause";
	public static final String PLAY            = "play";
	public static final String PLAY_ID         = "playid";
	public static final String PREVIOUS        = "previous";
	public static final String REPEAT          = "repeat";
	public static final String RANDOM          = "random";
	public static final String SEEK            = "seek";
	public static final String SEEK_ID         = "seekid";
	public static final String SINGLE          = "single";
	public static final String STOP            = "stop";
	public static final String VOLUME          = "setvol";
	
	/**
	 * Playlist commands:
	 */
	public static final String ADD             = "add";
	public static final String CLEAR           = "clear";
	public static final String CURRSONG        = "currentsong";
	public static final String DELETE          = "rm";
	public static final String CHANGES         = "plchanges";
	public static final String LIST_ID         = "playlistid";
	public static final String LIST            = "playlistinfo";
	public static final String LOAD            = "load";
	public static final String MOVE            = "move";
	public static final String MOVE_ID         = "moveid";
	public static final String REMOVE          = "delete";
	public static final String REMOVE_ID       = "deleteid";
	public static final String SAVE            = "save";
	public static final String SHUFFLE         = "shuffle";
	public static final String SWAP            = "swap";
	public static final String SWAP_ID         = "swapid";
	
	public static final String ADD_PLAYLIST    = "playlistadd";
	
	private final int TRIES          = 3;
	
	private InetSocketAddress address;
	private Socket socket;
	
	private String version;
		
	public Server() {}
	
	/**
	 * Creates an instance with the given address and port
	 * as endpoint to connect to.
	 * 
	 * @param address String, the endpoint to connect with. 
	 * @param port integer, the port to use with this connection.
	 */
	public Server(String address, int port)  {
		setAddress(address, port);
	}
	
	/**
	 * Creates an instance with the given InetSocketAddress
	 * as endpoint to connect with.
	 * 
	 * @param address InetSocketAddress, the endpoint to connect with. 
	 
	 */
	public Server(InetSocketAddress address) {
		this.address = address;
	}
	
	/**
	 * The version is given by the MPDserver after a successful connection.
	 *  
	 * @return String, the version of the MPD server
	 */
	public String getVersion() {
		return version;
	}
	
	/*
	 * A connection is attempted to make with the server the version 
	 * of the MPDserver is returned at success.
	 *
	 * @return version of the MPDserver.
	 * @throws Exception 
	 */
	private synchronized String connect() throws IllegalArgumentException, IOException {
		BufferedReader in;
		String line;
		InetSocketAddress customAddress = new InetSocketAddress("192.168.2.10", 6600);
		this.socket = new Socket();
		try {
            socket.connect(customAddress, 5000);    // connect
            // System.out.println("pasted socket.connect");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = in.readLine();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("this is not valid!");
		} catch (IOException e) {
			throw new IOException("io is not ok");
		}
		// GEEFT SOMS NULL POINTER EXCEPTIONS
		if (isOk(line)) {
			return line;
		} else if (isError(line)){
			sendCommand(new Command(CLEAR_ERROR));
		//} else if (line == null) {
		//	sendCommand(new Command(CLEAR_ERROR));
		} else {
            throw new IOException();
        }
	    return line;
	}

    /**
     *
     * @return String, the response of the server if successful connection is made.
     * @throws IOException
     */
    public String checkConnectionSettings() throws IllegalArgumentException, IOException {
        return connect();
    }



	public synchronized String[] sendCommand(List<Command> commands) throws IOException {
		byte[] bytesToSend = null;
		
		List<String> response = new ArrayList<String>();
		OutputStream out = null;
		BufferedReader in;
		
		String send = "";
		for (Command command : commands) {
			send = send+this.convertCommand(command.getCommand(), command.getParams());
		}
						
		try {
			version = connect();
		} catch (IOException e) {
			throw new SocketException("Unable to connect!");
		}
		
		try {
			bytesToSend = send.getBytes("utf-8");
			
			out = socket.getOutputStream();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			out.write(bytesToSend);
			out.flush();
			
			String line = null;
			
			while ((line = in.readLine()) != null) {
				
				if (isOk(line))	{
					break;
				}
				if (isError(line)) {
					throw new IOException("Response ERROR");
				}
				
				// System.out.println(line);
				response.add(line);
			}
			
			out.close();
			in.close();
			//count = TRIES + 1;
		
			
		} catch (IOException e) {
			System.out.println(bytesToSend.toString());
			throw new IOException("Can't write");
			
		}	
		return null;
	}
	
	
	/**
	 * Send a command to the server and retrieves the servers response
	 * if there is any. The response is returned as an array of Strings.
	 * 
	 * @param command the command that is send.
	 * @return array of Strings given by the server.
	 * @throws IOException 
	 */
	public synchronized String[] sendCommand(Command command) throws IOException {
		byte[] bytesToSend = null;
		List<String> response = new ArrayList<String>();
		OutputStream out = null;
		BufferedReader in;
				
		try {
			version = connect();
		} catch (IOException e) {
			throw new SocketException("Unable to connect!");
		}
				
		int count = 0;
		
		while (count < TRIES) {
			
			try {
				bytesToSend = this.convertCommand(command.getCommand(), command.getParams()).getBytes("utf-8");
				
				out = socket.getOutputStream();
				out.write(bytesToSend);
				out.flush();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String line = null;
				
				while ((line = in.readLine()) != null) {
					
					if (isOk(line))	{
						break;
					}
					if (isError(line)) {
						throw new IOException("Response ERROR");
					}

					response.add(line);
				}
			
				out.close();
				in.close();
				count = TRIES + 1;
			
				
			} catch (IOException e) {
				System.out.println(bytesToSend.toString());
				throw new IOException("Can't write");
				
			}	
				// TODO ------> Code what to do when errors occur!! <---------
		}
		return response.toArray(new String[response.size()]);
	}
	
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
		
	private void setAddress(String address, int port) {
		this.address = new InetSocketAddress(address, port);
	}
		
	/*
	 * Converts the String command and the array of Strings
	 * parameters of a command object to an array of bytes.
	 * The returned bytes can be written to the MPDserver.
	 * 
	 * @param command is the command String of the command object.
	 * @param params is an array of Strings parameters of the command object.
	 * @return array of bytes.
	 */
	private String convertCommand(String command, String[] params) {
		StringBuilder sb = new StringBuilder(command);
		if (params != null) {
			if (params.length > 0) {
				for (String param : params) {
					sb.append(" \""+param+"\"");
				}
			}
		}
		sb.append(System.lineSeparator());
		return String.valueOf(sb);
		
	}
		
	/*
	 * Testing if the line starts with
	 * "OK" is true, else false	.
	 */
	private boolean isOk(String line) {
		
		if (line.startsWith(OK)) {
			return true;
		}
		return false;
		
	}
	
	/*
	 * Testing if the line starts with
	 * "ACK" is true, else false.
	 */
	private boolean isError(String line) {
		if (line.startsWith(ERROR)) {
			return true;
		}
		return false;
	}
}
