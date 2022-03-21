package ua.goit.hw5.controller.command;

public interface Command {

    boolean canProccess (String input);

    void process();
}
