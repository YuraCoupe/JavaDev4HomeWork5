package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.PetService;
import ua.goit.hw5.view.View;

import java.util.Set;

import static ua.goit.hw5.controller.command.Commands.FIND_PETS_BY_STATUS;

public class FindPetsByStatus implements Command{

    private final View view;
    private final PetService petService;

    public FindPetsByStatus(View view, PetService petService) {
        this.view = view;
        this.petService = petService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(FIND_PETS_BY_STATUS.getName());
    }

    @Override
    public void process() {
        String petStatus = new String();
        while (true) {
            view.write("Enter pets status");
            petStatus = view.read();
            boolean isIncorrectCommand = true;
                if (petStatus.equals("available") | petStatus.equals("pending") | petStatus.equals("sold")) {
                    isIncorrectCommand = false;
                    break;
                }
            if (isIncorrectCommand) {
                view.write("Incorrect status. Please, try again");
            }
        }
        Set<Pet> pets = petService.findPetsByStatus(petStatus);
        view.write("Pets with status " + petStatus + " list:");
        pets.stream()
                .forEach(pet -> view.write(pet.toString()));
    }
}
