package ua.goit.hw5;


import ua.goit.hw5.controller.Controller;
import ua.goit.hw5.controller.util.PetUtil;
import ua.goit.hw5.service.OrderService;
import ua.goit.hw5.service.PetService;
import ua.goit.hw5.service.UserService;
import ua.goit.hw5.view.Console;
import ua.goit.hw5.view.View;

public class Application {
    private static final String STORE_URL = "https://petstore.swagger.io/v2/store/";
    private static final String USER_URL = "https://petstore.swagger.io/v2/user/";

    public static void main(String[] args){
        PetUtil petUtil = new PetUtil();

        PetService petService = new PetService();
        OrderService orderService = new OrderService();
        UserService userService = new UserService();

        View view = new Console();

        Controller pmsController = new Controller(view, petService, orderService, userService);
        pmsController.run();
    }
}
