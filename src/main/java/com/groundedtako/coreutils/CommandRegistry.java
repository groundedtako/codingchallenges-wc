package com.groundedtako.coreutils;

import com.groundedtako.coreutils.api.Command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registry for all available commands.
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command) {
        commands.put(name.toLowerCase(), command);
    }

    public Command get(String name) {
        return commands.get(name.toLowerCase());
    }

    public Set<String> getCommandNames() {
        return Collections.unmodifiableSet(commands.keySet());
    }

    public boolean hasCommand(String name) {
        return commands.containsKey(name.toLowerCase());
    }
}
