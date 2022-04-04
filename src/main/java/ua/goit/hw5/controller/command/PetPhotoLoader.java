package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Pet;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.io.File;
import java.util.Objects;

import static ua.goit.hw5.controller.command.Commands.UPLOAD_PET_IMAGE;

public class PetPhotoLoader implements Command {

    private final View view;
    private final Service service;

    public PetPhotoLoader(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(UPLOAD_PET_IMAGE.getName());
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
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect number. Please, try again");
            }
        }



        service.findPetById(id);
        view.write("Enter metadata");
        String metadata = view.read();
        File file = null;

        while (true) {
            view.write("Enter file path and name");
            String filename = view.read();
            file = new File(filename);
            if(file.exists()) {
                break;
            } else {
                view.write("File doesn't exist. Please, try again");
            }
        }

        service.uploadPetPhoto(id, metadata, file);
        view.write("Photo successfully added");

    }
}
