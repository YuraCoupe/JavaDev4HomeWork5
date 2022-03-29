package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import static ua.goit.hw5.controller.command.Commands.DELETE_PET;
import static ua.goit.hw5.controller.command.Commands.FIND_PETS_BY_ID;

public class PetDeleter implements Command{

    private final View view;
    private final Service service;

    public PetDeleter(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(DELETE_PET.getName());
    }

    @Override
    public void process() {
        Long petId;
        while (true) {
            view.write("Enter pet id");
            String petIdString = view.read();
            try {
                petId = Long.parseLong(petIdString);
                break;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                view.write("Incorrect number. Please, try again");
            }
        }
        service.findPetById(petId);

        service.deletePet(petId);
        view.write("Pet with ID " + petId + " deleted.");
    }
}
