package com.github.sunproject.org.modularshell.builtin;

import com.github.sunproject.org.modularframework.init.ModularInit;
import com.github.sunproject.org.modularshell.ModularCommand;
import com.github.sunproject.org.modularshell.ModularShell;

public class ModularAboutShCmd extends ModularCommand {
    public ModularAboutShCmd() {
        super("aboutShell");
        this.setEventHandler(this::about);
    }

    private void about() {
        System.out.println(ModularShell.moduleName + " ver. " + ModularInit.getModuleManager().getModuleByName(ModularShell.moduleName).getModuleVersion());
    }
}
