package com.bono.zero.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Command {
	
	private String command;
	private String[] params;
	
	public Command(String command) {
		this(command, new String[0]);
	}
	
	public Command(String command, String param) {
		this(command, new String[]{param});
	}
	
	public Command(String command, String[] params) {
		this.command = command;
		this.params = params;
		
	}
	
	public String getCommand() {
		return command;
	}
	
	public String[] getParams() {
		return params;
	}

}
