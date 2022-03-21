package ua.goit.hw5.controller.command;

import ua.goit.hw5.Exception.ExitException;
import ua.goit.hw5.view.View;

import static ua.goit.hw5.controller.command.Commands.EXIT;

public class Exit implements Command {
    private final View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(EXIT.getName());
    }

    @Override
    public void process() {
        throw new ExitException();
    }
}
