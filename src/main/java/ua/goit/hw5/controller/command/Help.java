package ua.goit.hw5.controller.command;

import ua.goit.hw5.view.View;

import static ua.goit.hw5.controller.command.Commands.*;

public class Help implements Command{

    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(HELP.getName());
    }

    @Override
    public void process() {
        view.write("Enter " + HELP.getName() + " to see available commands.");
        view.write("Enter " + EXIT.getName() + " to exit.");
        view.write("Enter " + FIND_PETS_BY_STATUS.getName() + " to find pets by status.");
        view.write("Enter " + ADD_PET.getName() + " to add pet.");
    }
}
