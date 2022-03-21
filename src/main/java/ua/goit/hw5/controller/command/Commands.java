package ua.goit.hw5.controller.command;

public enum Commands {
    FIND_PETS_BY_STATUS("findpetsbystatus"),
    EXIT("exit"),
    HELP("help");

    private String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
