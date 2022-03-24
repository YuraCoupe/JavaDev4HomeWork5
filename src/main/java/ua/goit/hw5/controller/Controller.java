package ua.goit.hw5.controller;

import ua.goit.hw5.Exception.ExitException;
import ua.goit.hw5.controller.command.*;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private final View view;
    private final List<Command> commands;

    public Controller(View view, Service service) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(
                new Exit(view),
                new Help(view),
                new PetsByStatusFinder(view, service),
                new PetsByIdFinder(view, service),
                new PetCreator(view, service),
                new PetPhotoLoader(view, service)
        ));
    }

    public void run() {
        view.write("Welcome to Swagger Petstore request application");
        executeCommand();
    }

    private void executeCommand() {
        try {
            while (true) {
                view.write("Please, enter command or enter help to see available commands");
                String input = view.read();
                boolean isIncorrectCommand = true;
                for (Command command : commands) {
                    if (command.canProccess(input)) {
                        command.process();
                        isIncorrectCommand = false;
                    }
                }
                if (isIncorrectCommand) {
                    view.write("Incorrect command. Please, try again");
                }
            }
        } catch (ExitException e) {
            view.write("Good bye!");
        }
    }
}
