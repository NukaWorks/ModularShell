package com.github.sunproject.org.modularshell;

import java.util.ArrayList;

import com.github.sunproject.org.modularframework.events.ModularEventHandler;

public class ModularCmdArgs {

	private ModularEventHandler eventHandler;
	private static ArrayList<String> cmdArgs = new ArrayList<>();

	public ModularCmdArgs() {}
	
	public ModularCmdArgs(String argName, ModularEventHandler event) {
		this.eventHandler = event;
		
		if (getCmdArgs().contains(argName)) event.onEvent();
	}


	public static ArrayList<String> getCmdArgs() {
		return cmdArgs;
	}
	
	public void setCmdArgs(ArrayList<String> cmdArgs) {
		this.cmdArgs = cmdArgs;
	}
}
