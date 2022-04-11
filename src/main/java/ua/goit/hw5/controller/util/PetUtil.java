package ua.goit.hw5.controller.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ua.goit.hw5.model.Pet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class PetUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final CloseableHttpClient CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static HttpResponse<String> findByStatus(URI uri) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> findById(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static HttpResponse<String> addPet(URI uri, Pet pet) {
        String petGson = GSON.toJson(pet);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(petGson))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            // if we have the same behavior for several catch statements we can use || and check the both exeptions
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse<String> updatePet(URI uri, Pet pet) {
        String petGson = GSON.toJson(pet);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
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

    public static CloseableHttpResponse uploadPetPhoto(URI uri, String metadata, File file) {
        HttpPost uploadFile = new HttpPost(uri);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("additionalMetadata", "blablabla", ContentType.TEXT_PLAIN);

        try {
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(file),
                    ContentType.APPLICATION_OCTET_STREAM,
                    file.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = CLOSEABLE_HTTP_CLIENT.execute(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CloseableHttpResponse updatePetWithFormData(URI uri, Long id, String name, String status) {
        HttpPost updatePet = new HttpPost(uri);
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
            response = CLOSEABLE_HTTP_CLIENT.execute(updatePet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CloseableHttpResponse deletePet(URI uri, Long id) {
        HttpDelete deletePet = new HttpDelete(uri);

        CloseableHttpResponse response = null;
        try {
            response = CLOSEABLE_HTTP_CLIENT.execute(deletePet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
