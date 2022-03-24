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
    public void process() {
        view.write("Enter pet ID");
        int id = Integer.parseInt(view.read());
        view.write("Enter pet category ID");
        int categoryId = Integer.parseInt(view.read());
        view.write("Enter pet category name");
        String categoryName = view.read();
        view.write("Enter pet name");
        String name = view.read();
        view.write("Enter pet url links divided with blank");
        Set<String> photoUrls = new HashSet<>();
        Arrays.stream(view.read().split("\s+")).forEach(url -> photoUrls.add(url));
        Set<Tag> tags = new HashSet<>();
        while (true) {
            view.write("Enter pet tag ID");
            int tagId = Integer.parseInt(view.read());
            view.write("Enter pet tag name");
            String tagName = view.read();
            tags.add(new Tag(tagId, tagName));
            String answer;
            while(true) {
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
            view.write("Enter pets status (choose from available, pending, sold)");
            status = view.read();
            boolean isIncorrectCommand = true;
            if (status.equals("available") | status.equals("pending") | status.equals("sold")) {
                break;
            }
            if (isIncorrectCommand) {
                view.write("Incorrect status. Please, try again");
            }
        }
        PetStatus petStatus = PetStatus.valueOf(status.toUpperCase());
        Category category = new Category(categoryId, categoryName);
        Pet pet = new Pet(id, category, name, photoUrls, tags, petStatus);
        service.addPet(pet);

    }
}
