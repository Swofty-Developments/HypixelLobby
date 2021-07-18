package net.swofty.lobby.command.exception;

public class CommandFailException extends RuntimeException {
    private final String msg;

    public CommandFailException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
