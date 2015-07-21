package backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.bono.zero.model.Command;

public class Communicate {
		
	private InetSocketAddress address;
	private Socket socket;
	private BufferedReader reader;
	private InputStreamReader input_reader;
	private OutputStream output;
	
	private String version;
	
	public enum DataBaseCommand {
		
		FIND("find"),
		
		LIST("list"),
		
		LIST_ALL("listall"),
		
		LIST_ALL_INFO("listallinfo");
		
		private final String command;
		
		DataBaseCommand(String command) {
			this.command = command;
		}
		
		public byte[] getCommand() {
			return (command+"\n").getBytes();
		}
		
		
	}
	
	public enum ReturnMessage {
		
		OK("OK"),
		
		ERROR("ACK");
				
		private final String message;
		
		ReturnMessage(String message) {
			this.message = message;
		}
		public String getReturnMessage() {
			return message;
		}
	}
	
	public enum ServerCommand {
		
		CLEAR_ERROR("clearerror"),
		
		CLOSE("close"),
		
        COMMAND_LIST_BEGIN("command_list_ok_begin"),
		
		COMMAND_LIST_END("command_list_end"),
		
		CURRENT_SONG("currentsong"),
		
		STATUS("status"),
		
		STATISTICS("stats"),
						
		PASSWORD("password"),
		
		PING("ping");
		
		private final String command;
		
		ServerCommand(String command) {
			this.command = command;
		}
		
		public byte[] getCommand() {
			return (command+"\n").getBytes();
		}
	}
	
	public enum PlaybackCommand {
		
		CRASSFADE("crossfade"),
		
		NEXT("next"),
		
		PAUSE("pause"),
		
		PLAY("play"),
		
		PLAYID("playid"),
		
		PREVIOUS("previous"),
		
		RANDOM("random"),
		
		REPEAT("repeat"),
		
		SEEK_CUR("seekcur"),
		
		STOP("stop"),
		
		VOLUME("setvol");
				
		private final String command;
		
		PlaybackCommand(String command) {
			this.command = command;
		}
		
		public byte[] getCommand() {
			return (command+"\n").getBytes();
		}
		
		public byte[] getCommand(int value) {
			return (command+" "+value+"\n").getBytes();
		}
	}
	
	public enum PlaylistCommand {
		
		ADD("add"),
		
		PLAYLIST("playlistinfo");
		
		private final String command;
		
		PlaylistCommand(String command) {
			this.command = command;
		}
		
		public byte[] getCommand() {
			return (command+"\n").getBytes();
		}
	}
	
	public Communicate() {
		//setAddress(Settings.settings.getHost(), Settings.settings.getPort());
	}
	
	public Communicate(String address, int port)  {
		setAddress(address, port);
	}
	
	public Communicate(InetSocketAddress address) {
		this.address = address;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
		
	private void setAddress(String address, int port) {
		this.address = new InetSocketAddress(address, port);
	}
	
	private String singleRead() throws IOException {
		input_reader = new InputStreamReader(socket.getInputStream(),"utf-8");
		reader = new BufferedReader(input_reader);
		String in;
		return in = reader.readLine();
	}
	
	private void commandWriter(byte[] send) throws IOException {
			output = socket.getOutputStream();
			output.write(send);
			output.flush();
	}
	
	private String connect() throws IOException {
		//System.out.println("connect method");
		socket =  new Socket();
		socket.connect(address);
		/*
		// ***** experimental stuff!!!!!!! ******
		//if (!socket.isConnected()) {
			try {
				int tries = 0;
				while (tries < 3) {
					socket =  new Socket();
					socket.connect(Settings.settings.getAddress());
					tries++;
					if (socket.isConnected()) {
						break;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				// when endpoint is wrong give message.
			} catch (IOException e) {
				//e.printStackTrace();
				// when a connection cannot be made give message.
				throw new IOException();
			}
		//}
		// ************************************** */
		return singleRead();
	}
	
	public boolean ping() {
		boolean result = false;
		try {
			connect();
			commandWriter(ServerCommand.PING.getCommand());
			if (isOk(singleRead())) {
				result = true;
			} else if (isError(singleRead())) {
				result = false;
			}
		} catch (NullPointerException e) {
			
			result = false;
		} catch (IOException e) {
			result = false;
		}
		return result;
	}
	
	private byte[] convertCommand(String command, String[] params) {
		StringBuilder sb = new StringBuilder(command);
		if (params.length > 0) {
			for (String param : params) {
				sb.append(" \""+param+"\"");
			}
			sb.append(System.lineSeparator());
		}
		sb.append(System.lineSeparator());
		return String.valueOf(sb).getBytes();
		
	}
	
	public  String[] sendCommand(Command command) throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		String line;
		synchronized (this) {
			if (!ping()) {
				try {
					connect();
				} catch (IOException e) {
					throw new IOException("Unable to connect!");
				}
			}
			try {
				commandWriter(convertCommand(command.getCommand(), command.getParams()));
			} catch (IOException e) {
				throw new IOException("Can't write");
			}
			
			input_reader = new InputStreamReader(socket.getInputStream(),"utf-8");
			reader = new BufferedReader(input_reader);
			while ((line = reader.readLine()) != null) {
				if (isOk(line) || isError(line)) {
					break;
				}
				files.add(line);
			}
			// added just
			try {
				output.close();
				input_reader.close();
			} catch (IOException e) {
				throw new IOException("can't close");
			}
			files.trimToSize();
			String[] output = files.toArray(new String[files.size()]);
			files = null;
			return output;
			
		}
	}
	
	public String[] sendForFiles(byte[] command) throws IOException {
		//SimpleTextArray files = new SimpleTextArray();
		ArrayList<String> files = new ArrayList<String>();
		String line;
		synchronized (this) {
			if (!ping()) {
				try {
					connect();
				} catch (IOException e) {
					throw new IOException("Unable to connect!");
				}
			}
			try {
			
				commandWriter(command);
			} catch (IOException e) {
				throw new IOException("Can't write");
			}
			input_reader = new InputStreamReader(socket.getInputStream(),"utf-8");
			reader = new BufferedReader(input_reader);
			while ((line = reader.readLine()) != null) {
				if (isOk(line) || isError(line)) {
					break;
				}
				files.add(line);
			}
			// added just
			try {
				output.close();
				input_reader.close();
			} catch (IOException e) {
				throw new IOException("can't close");
			}
			files.trimToSize();
			String[] output = files.toArray(new String[files.size()]);
			files = null;
			return output;
		}
	}
	
	public void noreturnCommand(Command command) throws IOException {
		try {
			connect();
			output = socket.getOutputStream();
			output.write(convertCommand(command.getCommand(), command.getParams()));
			output.flush();
			output.close();
		} catch (IOException e) {
			throw new IOException("Unable to connect!");
		}
	}
	
	public void singleCommand(byte[] command) throws IOException {
		try {
			connect();
			output = socket.getOutputStream();
			output.write(command);
			output.flush();
			output.close();
		} catch (IOException e) {
			throw new IOException("Unable to connect!");
		}
		
	}
		
	private boolean isOk(String line) {
		if (line.startsWith(ReturnMessage.OK.getReturnMessage())) {
			return true;
		}
		return false;
	}
	
	private boolean isError(String line) {
		if (line.startsWith(ReturnMessage.ERROR.getReturnMessage())) {
			return true;
		}
		return false;
	}
}
