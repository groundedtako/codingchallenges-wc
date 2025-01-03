package com.groundedtako.coreutils;

import com.groundedtako.coreutils.api.Command;
import com.groundedtako.coreutils.commands.WcCommand;

import java.util.Arrays;

public class Main {
    private static final CommandRegistry registry = new CommandRegistry();

    static {
        // Register all available commands
        registry.register("wc", new WcCommand());
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            System.exit(0);
        }

        String cmdName = args[0];
        Command cmd = registry.get(cmdName);
        
        if (cmd == null) {
            System.err.println("Unknown command: " + cmdName);
            showUsage();
            System.exit(1);
        }

        String[] cmdArgs = Arrays.copyOfRange(args, 1, args.length);
        int exitCode = cmd.execute(cmdArgs);
        System.exit(exitCode);
    }

    private static void showUsage() {
        System.out.println("Available commands:");
        for (String name : registry.getCommandNames()) {
            Command cmd = registry.get(name);
            System.out.printf("  %-10s %s%n", name, cmd.getDescription());
        }
        System.out.println("\nUse '[command] --help' for more information about a command.");
    }
}
