package ua.goit.hw5.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.goit.hw5.controller.util.PetUtil;
import ua.goit.hw5.model.Pet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PetService {

    private static final String PET_URL = "https://petstore.swagger.io/v2/pet";
    private static final String FIND_BY_STATUS = "https://petstore.swagger.io/v2/pet/findByStatus?status=";
    private static final String UPLOAD_PET_PHOTO_URL = "https://petstore.swagger.io/v2/pet/%d/uploadImage";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public Set<Pet> findPetsByStatus(String petStatus) {
        URI uri = URI.create(String.format("%s%s", FIND_BY_STATUS, petStatus));

        Set<Pet> pets = null;
        HttpResponse<String> response = null;
        try {
            response = PetUtil.findByStatus(uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Pet> petsList = GSON.fromJson(response.body(), new TypeToken<List<Pet>>() {
        }.getType());
        pets = petsList.stream().collect(Collectors.toSet());
        return pets;
    }

    public Pet findPetById(Long id) {
        URI uri = URI.create(PET_URL + "/" + id);

        Pet pet = null;
        HttpResponse<String> response = null;
        try {
            response = PetUtil.findById(uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pet = GSON.fromJson(response.body(), Pet.class);
        return pet;
    }

    public Pet addPet(Pet pet) {
        URI uri = URI.create(String.format("%s%s", PET_URL, "?"));
        HttpResponse<String> response = PetUtil.addPet(uri, pet);
        Pet addedPet = GSON.fromJson(response.body(), Pet.class);
        return addedPet;
    }

    public Pet updatePet(Pet pet) {
        URI uri = URI.create(String.format("%s%s", PET_URL, "?"));
        HttpResponse<String> response = PetUtil.updatePet(uri, pet);
        Pet updatedPet = GSON.fromJson(response.body(), Pet.class);
        return updatedPet;
    }

    public void uploadPetPhoto(Long id, String metadata, String filename) {
        URI uri = URI.create(String.format(UPLOAD_PET_PHOTO_URL, id));
        PetUtil.uploadPetPhoto(uri, metadata, filename);
    }

    public void updatePetWithFormData(Long id, String name, String status) {
        URI uri = URI.create(String.format("%s%s%d", PET_URL, "/", id));
        PetUtil.updatePetWithFormData(uri, id, name, status);
    }

    public void deletePet(Long id) {
        URI uri = URI.create(String.format("%s%s%d", PET_URL, "/", id));
        PetUtil.deletePet(uri, id);
    }
}
