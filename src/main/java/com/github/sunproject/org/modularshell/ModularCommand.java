package com.github.sunproject.org.modularshell;


import java.util.ArrayList;
import java.util.HashMap;
import com.github.sunproject.org.modularframework.events.ModularEventHandler;

@SuppressWarnings("unused")
public class ModularCommand {
    private String commandName;
    private String commandDetails;
    private static final HashMap<String, ModularCommand> commandsMap = new HashMap<>();
    private ModularEventHandler eventHandler;
    private ArrayList<String> commandArgs = new ArrayList<>();

    public ModularCommand(String cmdName) {
        this.commandName = cmdName;
    }


    public void launchCommand() {
        eventHandler.onEvent();
        if (!getCommandArgs().isEmpty()) getCommandArgs().clear(); // For Resolve a bug with cmd args, force reset args
    }

	public String getCommandDetails() {
		if (commandDetails == null || commandDetails.isEmpty()) commandDetails = "No Details provided.";
		return commandDetails;
	}


	public void setCommandArgs(ArrayList<String> args) {
		commandArgs = args;
	}

	public ArrayList<String> getCommandArgs() {
		return commandArgs;
	}

	public void setCommandDetails(String details) {
		commandDetails = details;
	}

	public static void unregisterCommand(ModularCommand command) {
		getCommands().remove(command.getCommandName());
	}

	public static void unregisterAllCommands() {
		System.out.println("Unregistering All Commands ...");
		getCommands().clear();
		System.out.println("...done !");
	}

	public static void registerCommand(ModularCommand command) {
		getCommands().put(command.getCommandName(), command);
	}

	public static HashMap<String, ModularCommand> getCommands() {
		return commandsMap;
	}

	public ModularEventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(ModularEventHandler eventHandler) {
		this.eventHandler = eventHandler;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
