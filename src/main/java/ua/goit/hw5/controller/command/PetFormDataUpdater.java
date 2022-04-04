package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.util.Objects;

import static ua.goit.hw5.controller.command.Commands.UPDATE_PET_FORM;

public class PetFormDataUpdater implements Command {

    private final View view;
    private final Service service;

    public PetFormDataUpdater(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(UPDATE_PET_FORM.getName());
    }

    @Override
    public void process() {
        Long id;
        Pet pet;

        while (true) {
            view.write("Enter pet id to update");
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
        view.write(String.format("Enter new pet name. Actual pet name is %s", pet.getName()));
        String name = view.read();

        view.write(String.format("Enter new pet status. Actual pet status is %s", pet.getStatus()));
        String status = view.read();

        service.updatePetWithFormData(id, name, status);
        view.write("Pet data updated");
    }
}
