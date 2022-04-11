package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Category;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.model.PetStatus;
import ua.goit.hw5.model.Tag;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static ua.goit.hw5.controller.command.Commands.UPDATE_PET;

public class PetUpdater implements Command {

    private final View view;
    private final Service service;

    public PetUpdater(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(UPDATE_PET.getName());
    }

    @Override
    //try to separate the code in more methods and reuse the code if possible
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
        view.write("Enter new data to update pet or leave blank to leave previous data");
        Long categoryId;
        String categoryIdString;
        while (true) {
            view.write(String.format("Actual pet category ID is %d. Enter pet category ID", pet.getCategory().getId()));
            categoryIdString = view.read();
            if (categoryIdString.equals("")) {
                categoryId = pet.getCategory().getId();
                break;
            } else {
                try {
                    categoryId = Long.parseLong(categoryIdString);
                    break;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    view.write("Incorrect number. Please, try again");
                }
            }
        }

        view.write(String.format("Actual pet category name is %s. Enter pet category name", pet.getCategory().getName()));
        String categoryName = view.read();
        if (categoryName.equals("")) {
            categoryName = pet.getCategory().getName();
        }

        view.write(String.format("Actual pet name is %s. Enter pet name", pet.getName()));
        String name = view.read();
        if (name.equals("")) {
            name = pet.getName();
        }

        view.write(String.format("Actual pet urls are %s. Enter pet urls divided with spaces", pet.getPhotoUrls()));
        String urls = view.read();
        Set<String> photoUrls = new HashSet<>();
        if (urls.equals("")) {
            photoUrls.addAll(pet.getPhotoUrls());
        } else {
            Arrays.stream(urls.split("\\s+")).forEach(url -> photoUrls.add(url));
        }


        Set<Tag> tags = new HashSet<>();
        view.write(String.format("Actual pet tags are %s.", pet.getTags()));

        boolean leaveExistedTags = false;
        int tagId = 0;
        String tagIdString;

        while (true) {

            while (true) {
                view.write("Enter pet tag ID");
                tagIdString = view.read();
                if (tagIdString.equals("") & tags.isEmpty()) {
                    tags.addAll(pet.getTags());
                    leaveExistedTags = true;
                    break;
                }
                try {
                    tagId = Integer.parseInt(tagIdString);
                    break;
                } catch (NumberFormatException e) {
                    view.write("Incorrect number. Please, try again");
                }
            }
            if (leaveExistedTags == true) {
                break;
            }
            view.write("Enter pet tag name");
            String tagName = view.read();
            tags.add(new Tag(tagId, tagName));
            String answer;
            while (true) {
                view.write("Would you like to add one more tag? Write yes or no:");
                answer = view.read();
                if (answer.equals("no") | answer.equals("yes")) {
                    break;
                }
                view.write("Incorrect command. Write yes or no, please");
            }
            if (answer.equals("no")) {
                break;
            }
        }

        String status;
        while (true) {
            view.write(String.format("Actual pet status is %s. Enter pets status (choose from available, pending, sold)", pet.getStatus()));
            status = view.read();
            if (status.equals("")) {
                status = pet.getStatus().name();
                break;
            } else {
                boolean isIncorrectCommand = true;
                if (status.equals("available") | status.equals("pending") | status.equals("sold")) {
                    break;
                }
                if (isIncorrectCommand) {
                    view.write("Incorrect status. Please, try again");
                }
            }
        }
        PetStatus petStatus = PetStatus.valueOf(status.toUpperCase());
        Category category = new Category(categoryId, categoryName);
        Pet updatedPet = new Pet(id, category, name, photoUrls, tags, petStatus);
        service.updatePet(updatedPet);
        view.write("Pet data updated.");
    }
}
