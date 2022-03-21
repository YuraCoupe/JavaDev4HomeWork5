package ua.goit.hw5.service;

import ua.goit.hw5.model.Pet;

import java.util.Set;

public class Service {
    private PetService petService;
    private OrderService orderService;
    private UserService userService;

    public Service() {
        this.petService = new PetService();
        this.orderService = new OrderService();
        this.userService = new UserService();
    }

    public Set<Pet> findPetsByStatus(String status) {
        return petService.findPetsByStatus(status);
    }

    public Pet addPet(Pet pet){
        return petService.addPet(pet);
    }
}
