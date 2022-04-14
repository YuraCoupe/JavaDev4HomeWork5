package ua.goit.hw5.service;

import ua.goit.hw5.model.Pet;

import java.io.File;
import java.util.Set;

public class Service {
    private PetService petService;

    public Service() {
        this.petService = new PetService();
    }

    public Set<Pet> findPetsByStatus(String status) {
        return petService.findPetsByStatus(status);
    }

    public Pet findPetById(Long id) {
        return petService.findPetById(id);
    }

    public Pet addPet(Pet pet){
        return petService.addPet(pet);
    }

    public Pet updatePet(Pet pet) {
        return petService.updatePet(pet);
    }

    public void uploadPetPhoto(Long id, String metadata, File file) {
        petService.uploadPetPhoto(id, metadata, file);
    }

    public void updatePetWithFormData (Long id, String name, String status) {
        petService.updatePetWithFormData(id, name, status);
    }

    public void deletePet(Long id) {
        petService.deletePet(id);
    }
}
