package ua.goit.hw5.model;

import com.google.gson.annotations.SerializedName;

public enum PetStatus {
    @SerializedName ("available") AVAILABLE ("available"),
    @SerializedName ("pending") PENDING ("pending"),
    @SerializedName ("sold") SOLD ("sold)");

    private final String status;

    public String getStatus() {
        return status;
    }

    PetStatus(String status) {
        this.status = status;
    }
}
