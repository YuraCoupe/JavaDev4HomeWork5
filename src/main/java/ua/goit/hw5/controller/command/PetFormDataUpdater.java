package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

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
        while (true) {
            view.write("Enter pet id to update");
            String petIdString = view.read();
            try {
                id = Long.parseLong(petIdString);
                break;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                view.write("Incorrect number. Please, try again");
            }
        }
        Pet pet = service.findPetById(id);
        view.write(String.format("Enter new pet name. Actual pet name is %s", pet.getName()));
        String name = view.read();

        view.write(String.format("Enter new pet status. Actual pet status is %s", pet.getStatus()));
        String status = view.read();

        service.updatePetWithFormData(id, name, status);
        view.write("Pet data updated");
    }
}
