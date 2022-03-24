package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Category;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.model.PetStatus;
import ua.goit.hw5.model.Tag;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ua.goit.hw5.controller.command.Commands.ADD_PET;
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
        view.write("Enter pet ID");
        Long id = Long.parseLong(view.read());
        service.findPetById(id);
        view.write("Enter metadata");
        String metadata = view.read();

        File file = new File("/");
        /*Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        service.uploadPetPhoto(id, metadata, file.getName());

    }
}
