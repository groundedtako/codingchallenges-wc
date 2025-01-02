package com.groundedtako.coreutils.api;

/**
 * Base interface for all command implementations.
 */
public interface Command {
    /**
     * Execute the command with the given arguments.
     *
     * @param args Command line arguments
     * @return Exit code (0 for success, non-zero for error)
     */
    int execute(String[] args);

    /**
     * Get a brief description of what the command does.
     *
     * @return Command description
     */
    String getDescription();

    /**
     * Get detailed usage instructions for the command.
     *
     * @return Command usage instructions
     */
    String getUsage();
}
