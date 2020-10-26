package com.github.sunproject.org.modularshell.builtin;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.github.sunproject.org.modularshell.ModularCommand;
import com.github.sunproject.org.modularshell.ModularShell;

import java.util.Map;

public class ModularHelpCmd extends ModularCommand {

	public ModularHelpCmd() {
		super("help");
		this.setEventHandler(this::help);
		this.setCommandDetails("Display help");
		System.out.println();
		System.out.println("\nWelcome to " + ModularShell.moduleName + " !");
		System.out.println("Type \"" + getCommandName() + "\" for help.");
	}

	private void help() {
		System.out.println("\n" + ModularShell.moduleName + " Commands : \n");

		for (Map.Entry<String, ModularCommand> cmd : getCommands().entrySet()) {
			System.out.println(Ansi.colorize(cmd.getValue().getCommandName(), new AnsiFormat(Attribute.BRIGHT_RED_TEXT())) + " - " + cmd.getValue().getCommandDetails());
		}
	}
}
