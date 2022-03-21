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
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


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

    public Pet addPet(Pet pet){
        return PetUtil.addPet(URI.create(PET_URL), pet);
    }
}
