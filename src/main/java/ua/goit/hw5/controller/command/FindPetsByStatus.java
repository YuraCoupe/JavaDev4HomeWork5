package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.util.Set;

import static ua.goit.hw5.controller.command.Commands.FIND_PETS_BY_STATUS;

public class FindPetsByStatus implements Command{

    private final View view;
    private final Service service;

    public FindPetsByStatus(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(FIND_PETS_BY_STATUS.getName());
    }

    @Override
    public void process() {
        String petStatus;
        while (true) {
            view.write("Enter pets status (choose from available, pending, sold)");
            petStatus = view.read();
            boolean isIncorrectCommand = true;
                if (petStatus.equals("available") | petStatus.equals("pending") | petStatus.equals("sold")) {
                    break;
                }
            if (isIncorrectCommand) {
                view.write("Incorrect status. Please, try again");
            }
        }
        Set<Pet> pets = service.findPetsByStatus(petStatus);
        view.write("Pets with status " + petStatus + " list:");
        pets.stream()
                .forEach(pet -> view.write(pet.toString()));
    }
}
