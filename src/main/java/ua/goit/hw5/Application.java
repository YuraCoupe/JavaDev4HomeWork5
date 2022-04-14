package ua.goit.hw5;


import ua.goit.hw5.controller.Controller;
import ua.goit.hw5.service.Service;
import ua.goit.hw5.view.Console;
import ua.goit.hw5.view.View;

public class Application {

    public static void main(String[] args){

        Service service = new Service();

        View view = new Console();

        Controller pmsController = new Controller(view, service);
        pmsController.run();
    }
}
