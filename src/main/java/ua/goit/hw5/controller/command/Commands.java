package ua.goit.hw5.controller.command;

public enum Commands {
    UPLOAD_PET_IMAGE("1"),
    ADD_PET("2"),
    UPDATE_PET("3"),
    FIND_PETS_BY_STATUS("4"),
    FIND_PETS_BY_ID("5"),
    UPDATE_PET_FORM("6"),
    DELETE_PET("7"),
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
