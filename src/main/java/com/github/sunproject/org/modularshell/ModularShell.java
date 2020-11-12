package com.github.sunproject.org.modularshell;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.github.sunproject.org.modularframework.console.ModularCInputs;
import com.github.sunproject.org.modularframework.events.ModularEventHandler;
import com.github.sunproject.org.modularframework.providers.modulemanager.ModularModule;
import com.github.sunproject.org.modularshell.builtin.ModularAboutShCmd;
import com.github.sunproject.org.modularshell.builtin.ModularHelpCmd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ModularShell extends ModularModule {

	public static final String moduleName = "ModularShell";
	private static final String moduleVersion = "2.0.1";
	private Scanner cliListener;
	private final ModularEventHandler preInitTaskEvent;

	private String prompt = Ansi.colorize("~", new AnsiFormat(Attribute.RED_TEXT()))
			+ Ansi.colorize(">", new AnsiFormat(Attribute.BOLD(), Attribute.ITALIC())) + " ";

	private static Thread shellThread;

	public ModularShell(ModularEventHandler preInitTask) {
		super(moduleName, 1, moduleVersion);
		this.setModuleVersion(moduleVersion);
		this.preInitTaskEvent = preInitTask;
		cliListener = ModularCInputs.getModularConsoleInputs().getScanner();

		shellThread = new Thread(() -> {
			System.out.println("\n");
			callInterpreter(getPrompt());
		});
		shellThread.setName(moduleName + "_MainThread");
	}

	private void callInterpreter(String prompt) {
		while (this.isEnabled()) {
			System.out.print(prompt);
			String[] args = cliListener.nextLine().split(" ");
			sendCommand(args);
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


	@Override
	public void onDisable() {
		ModularCommand.unregisterAllCommands();
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public void onEnable() {
		if (preInitTaskEvent != null) preInitTaskEvent.onEvent();

		// Init built-in commands
		ModularCommand.registerCommand(new ModularHelpCmd());
		ModularCommand.registerCommand(new ModularAboutShCmd());

		shellThread.start();
	}
}
