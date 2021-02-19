package com.github.sunproject.org.modularshell;

import xyz.sunproject.modularframework.core.events.RunEvent;

import java.util.ArrayList;

public class ModularCmdArgs {

	private RunEvent eventHandler;
	private static ArrayList<String> cmdArgs = new ArrayList<>();

	public ModularCmdArgs() {}
	
	public ModularCmdArgs(String argName, RunEvent event) {
		this.eventHandler = event;
		
		if (getCmdArgs().contains(argName)) event.runEvent();
	}


	public static ArrayList<String> getCmdArgs() {
		return cmdArgs;
	}
	
	public void setCmdArgs(ArrayList<String> cmdArgs) {
		this.cmdArgs = cmdArgs;
	}
}
