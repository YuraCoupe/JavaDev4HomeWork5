package ua.goit.hw5.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.goit.hw5.controller.util.PetUtil;
import ua.goit.hw5.model.Pet;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

public class PetService {

    private static final String PET_URL = "https://petstore.swagger.io/v2/pet";
    private static final String UPLOAD_PET_PHOTO_URL = "https://petstore.swagger.io/v2/pet/%d/uploadImage";


    public Set<Pet> findPetsByStatus(String petStatus){
        Set<Pet> pets = null;
        try {
            pets = PetUtil.findByStatus(URI.create(PET_URL), petStatus);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  pets;
    }

    public Pet findPetById(Long id){
        Pet pet = null;
        try {
            pet = PetUtil.findById(URI.create(PET_URL), id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  pet;
    }

    public Pet addPet(Pet pet){
        return PetUtil.addPet(URI.create(PET_URL), pet);
    }

    public void uploadPetPhoto (Long id, String metadata, String filename) {
        PetUtil.uploadPetPhoto(URI.create(String.format(UPLOAD_PET_PHOTO_URL, id)), metadata, filename);
    }
}
