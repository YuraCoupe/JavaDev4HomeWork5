package ua.goit.hw5.controller.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ua.goit.hw5.Exception.PetNotFoundException;
import ua.goit.hw5.model.Pet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private static final String FIND_BY_STATUS = "/findByStatus?status=";
    private static final String ADD_PET = "?";


    public static Set<Pet> findByStatus(URI uri, String status) throws IOException, InterruptedException {

        URI newUri = URI.create(String.format("%s%s%s", uri.toString(), FIND_BY_STATUS, status));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new PetNotFoundException(String.format("Pet with %s status not found", status));
        }
        List<Pet> pets = GSON.fromJson(response.body(), new TypeToken<List<Pet>>() {
        }.getType());
        Set<Pet> petsSet = pets.stream().collect(Collectors.toSet());
        return petsSet;
    }

    public static Pet findById(URI uri, Long id) throws IOException, InterruptedException {

        URI newUri = URI.create(String.format("%s%s%d", uri.toString(), "/", id));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new PetNotFoundException(String.format("Pet with id %d not found", id));
        }
        Pet pet = GSON.fromJson(response.body(), Pet.class);
        return pet;
    }

    public static Pet addPet(URI uri, Pet pet) {
        String petGson = GSON.toJson(pet);
        URI newUri = URI.create(String.format("%s%s", uri.toString(), ADD_PET));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .POST(HttpRequest.BodyPublishers.ofString(petGson))
                .header("Content-type", "application/json")
                .build();
        try {
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            return GSON.fromJson(response.body(), Pet.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse<String> updatePet(URI uri, Pet pet) {
        String petGson = GSON.toJson(pet);
        URI newUri = URI.create(String.format("%s%s", uri.toString(), ADD_PET));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .POST(HttpRequest.BodyPublishers.ofString(petGson))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void uploadPetPhoto(URI uri, String metadata, String filename) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(uri);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("additionalMetadata", "blablabla", ContentType.TEXT_PLAIN);

        File f = new File("src/main/resources/photo/tiger.jpeg");
        try {
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(f.getName());

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = response.getEntity();
        System.out.println(response.getStatusLine());
    }
}
