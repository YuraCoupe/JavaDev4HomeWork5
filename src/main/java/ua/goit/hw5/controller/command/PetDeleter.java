package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.util.Objects;

import static ua.goit.hw5.controller.command.Commands.DELETE_PET;

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
        Long id;
        Pet pet;
        while (true) {
            view.write("Enter pet id");
            String petIdString = view.read();
            try {
                id = Long.parseLong(petIdString);
                pet = service.findPetById(id);
                if (!Objects.isNull(pet)) {
                    break;
                } else {
                    view.write("Pet with ID " + id + " doesn't exist.");
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect number. Please, try again");
            }
        }
        service.findPetById(id);

        service.deletePet(id);
        view.write("Pet with ID " + id + " deleted.");
    }
}
