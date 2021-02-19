package com.github.sunproject.org.modularshell;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.github.sunproject.org.modularshell.builtin.ModularHelpCmd;
import xyz.sunproject.modularframework.core.ModularModule;
import xyz.sunproject.modularframework.core.events.ModuleStatus;
import xyz.sunproject.modularframework.core.events.RunEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ModularShell extends ModularModule {

	public static final String moduleName = "ModularShell";
	private static final String moduleVersion = "3.0.0";
	private Scanner cliListener;
	private final RunEvent preInitTaskEvent;

	private String prompt = Ansi.colorize("~", new AnsiFormat(Attribute.RED_TEXT()))
			+ Ansi.colorize(">", new AnsiFormat(Attribute.BOLD(), Attribute.ITALIC())) + " ";

	private static Thread shellThread;

	public ModularShell(RunEvent preInitTask) throws Exception {
		super(moduleName, "9da15b11", "Sundev79", moduleVersion);
		this.preInitTaskEvent = preInitTask;
		cliListener = new Scanner(System.in);

		shellThread = new Thread(() -> {
			System.out.println("\n");
			callInterpreter(getPrompt());
		});
		shellThread.setName(moduleName + "_MainThread");
	}

	private void callInterpreter(String prompt) {
		while (true) {
			System.out.print(prompt);
			String[] args = cliListener.nextLine().split(" ");
			sendCommand(args);

			if (getModuleState() == ModuleStatus.STOPPING) {
				onDisable();
				break;
			}
		}
	}

	private void sendCommand(String[] cmdInputs) {
		if (!cmdInputs[0].isEmpty()) {
			if (ModularCommand.getCommands().get(cmdInputs[0]) == null) {
				System.err.println("\nCommand " + "\"" + cmdInputs[0] + "\"" + " not found !");
			} else {
				ModularCommand command = ModularCommand.getCommands().get(cmdInputs[0]);
				if (cmdInputs.length > 1) {
					ArrayList<String> cmdArgs = new ArrayList<>(Arrays.asList(cmdInputs));
					cmdArgs.remove(0);
					command.setCommandArgs(cmdArgs);
				}
				command.launchCommand();
			}
		}
	}


	private void onDisable() {
		ModularCommand.unregisterAllCommands();
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void runEvent() {
		if (preInitTaskEvent != null) preInitTaskEvent.runEvent();

		// Init built-in commands
		ModularCommand.registerCommand(new ModularHelpCmd());

		shellThread.start();

	}
}
