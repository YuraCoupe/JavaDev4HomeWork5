package ua.goit.hw5.controller.command;

import ua.goit.hw5.model.Category;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.model.PetStatus;
import ua.goit.hw5.model.Tag;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.View;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ua.goit.hw5.controller.command.Commands.ADD_PET;

public class PetCreator implements Command {

    private final View view;
    private final Service service;

    public PetCreator(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(ADD_PET.getName());
    }

    @Override
    public void process() {
        Long id = getaLong("Enter pet id");
        Long categoryId = getaLong("Enter pet category id");
        String categoryName = getString("Enter pet category name");
        String name = getString("Enter pet name");
        view.write("Enter pet url links divided with blank");
        Set<String> photoUrls = new HashSet<>();
        Arrays.stream(view.read().split("\\s+")).forEach(url -> photoUrls.add(url));
        Set<Tag> tags = new HashSet<>();
        while (true) {
            Integer tagId = getInteger("Enter pet tag id");
            String tagName = getString("Enter pet tag name");
            tags.add(new Tag(tagId, tagName));
            String answer;
            while (true) {
                answer = getString("Would you like to add one more tag? Write yes or no:");
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
            status = getString("Enter pets status (choose from available, pending, sold)");

            if (status.equals("available") | status.equals("pending") | status.equals("sold")) {
                break;
            }
            view.write("Incorrect status. Please, try again");
        }
        PetStatus petStatus = PetStatus.valueOf(status.toUpperCase());
        Category category = new Category(categoryId, categoryName);
        Pet pet = new Pet(id, category, name, photoUrls, tags, petStatus);
        service.addPet(pet);
        view.write("Pet added.");
    }

    private Integer getInteger(String message) {
        Integer input;
        while (true) {
            String petIdString = getString(message);
            try {
                input = Integer.parseInt(petIdString);
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect number. Please, try again");
            }
        }
        return input;
    }

    String getString(String message) {
        view.write(message);
        String input = view.read();
        return input;
    }

    Long getaLong(String message) {
        Long input;
        while (true) {
            view.write(message);
            String petIdString = view.read();
            try {
                input = Long.parseLong(petIdString);
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect number. Please, try again");
            }
        }
        return input;
    }
}
