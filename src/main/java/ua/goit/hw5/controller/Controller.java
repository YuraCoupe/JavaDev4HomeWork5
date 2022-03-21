package ua.goit.hw5.controller;

import ua.goit.hw5.Exception.ExitException;
import ua.goit.hw5.controller.command.Command;
import ua.goit.hw5.controller.command.Exit;
import ua.goit.hw5.controller.command.FindPetsByStatus;
import ua.goit.hw5.controller.command.Help;
import ua.goit.hw5.service.OrderService;
import ua.goit.hw5.service.PetService;
import ua.goit.hw5.service.UserService;
import ua.goit.hw5.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private final View view;
    private final List<Command> commands;

    public Controller(View view, PetService petService, OrderService orderService, UserService userService) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(
                new Exit(view),
                new Help(view),
                new FindPetsByStatus(view, petService)
        ));
    }

    public void run() {
        view.write("Welcome to Swagger Petstore request application");
        executeCommand();
    }

    private void executeCommand() {
        try {
            while (true) {
                view.write("Please, enter help to see available commands");
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
