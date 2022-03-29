package ua.goit.hw5.controller.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PetUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FIND_BY_STATUS = "/findByStatus?status=";
    private static final String ADD_PET = "?";


    public static HttpResponse<String> findByStatus(URI uri, String status) throws IOException, InterruptedException {

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

        return response;
    }

    public static HttpResponse<String> findById(URI uri, Long id) throws IOException, InterruptedException {

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
        return response;
    }

    public static HttpResponse<String> addPet(URI uri, Pet pet) {
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

    public static CloseableHttpResponse uploadPetPhoto(URI uri, String metadata, String filename) {
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

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CloseableHttpResponse updatePetWithFormData(URI uri, Long id, String name, String status) {
        URI newUri = URI.create(String.format("%s%s%d", uri.toString(), "/", id));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost updatePet = new HttpPost(newUri);
        EntityBuilder builder = EntityBuilder.create();

        NameValuePair namePair = new NameValuePair() {
            @Override
            public String getName() {
                return "name";
            }

            @Override
            public String getValue() {
                return name;
            }
        };NameValuePair statusPair = new NameValuePair() {
            @Override
            public String getName() {
                return "status";
            }

            @Override
            public String getValue() {
                return status;
            }
        };

        builder.setParameters(namePair, statusPair);

        HttpEntity entity = builder.build();
        updatePet.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(updatePet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CloseableHttpResponse deletePet(URI uri, Long id) {
        URI newUri = URI.create(String.format("%s%s%d", uri.toString(), "/", id));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete deletePet = new HttpDelete(newUri);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(deletePet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
