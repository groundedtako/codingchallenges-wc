package com.groundedtako.coreutils.api;

/**
 * Represents the result of a command execution.
 */
public class Result {
    private final int exitCode;
    private final String output;
    private final String error;

    public Result(int exitCode, String output, String error) {
        this.exitCode = exitCode;
        this.output = output;
        this.error = error;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }

    public static Result success(String output) {
        return new Result(0, output, "");
    }

    public static Result error(int code, String error) {
        return new Result(code, "", error);
    }
}
