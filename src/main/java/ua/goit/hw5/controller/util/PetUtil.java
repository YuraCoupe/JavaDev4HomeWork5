package ua.goit.hw5.controller.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.goit.hw5.model.Pet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PetUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FIND_BY_STATUS = "findByStatus?status=";

    public static Set<Pet> findByStatus(URI uri, String status) throws IOException, InterruptedException {

        URI newUri = URI.create(String.format("%s%s%s", uri.toString(),FIND_BY_STATUS,status));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        List<Pet> pets = GSON.fromJson(response.body(), new TypeToken<List<Pet>>() {
        }.getType());
        Set<Pet> petsSet = pets.stream().collect(Collectors.toSet());
        return petsSet;
    }



}
