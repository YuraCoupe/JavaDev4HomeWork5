package ua.goit.hw5.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.goit.hw5.Exception.PetNotFoundException;
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
    private static final String UPLOAD_PET_PHOTO_URL = "https://petstore.swagger.io/v2/pet/%d/uploadImage";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public Set<Pet> findPetsByStatus(String petStatus) {
        Set<Pet> pets = null;
        HttpResponse<String> response = null;
        try {
            response = PetUtil.findByStatus(URI.create(PET_URL), petStatus);
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
        Pet pet = null;
        HttpResponse<String> response = null;
        try {
            response = PetUtil.findById(URI.create(PET_URL), id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pet = GSON.fromJson(response.body(), Pet.class);
        return pet;
    }

    public Pet addPet(Pet pet) {
        HttpResponse<String> response = PetUtil.addPet(URI.create(PET_URL), pet);
        Pet addedPet = GSON.fromJson(response.body(), Pet.class);
        return addedPet;
    }

    public Pet updatePet(Pet pet) {
        HttpResponse<String> response = PetUtil.updatePet(URI.create(PET_URL), pet);
        Pet updatedPet = GSON.fromJson(response.body(), Pet.class);
        return updatedPet;
    }

    public void uploadPetPhoto(Long id, String metadata, String filename) {
        PetUtil.uploadPetPhoto(URI.create(String.format(UPLOAD_PET_PHOTO_URL, id)), metadata, filename);
    }
}
