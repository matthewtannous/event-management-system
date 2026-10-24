package com.mat.frontend.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mat.frontend.api.dto.*;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ApiClient {
    public static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    /* Auth */
    public static LoginResponse login(String username, String password) throws ApiException {
        return post("/auth/login", new LoginRequest(username, password), LoginResponse.class);
    }

    public static RegistrationResponse register(String username, String email, String firstName, String lastName, String password) throws ApiException {
        return post("/auth/register", new RegistrationRequest(username, password, email, firstName, lastName), RegistrationResponse.class);
    }


    /* Users */
    public static List<UserResponse> getUsers() {
        return get("/users", new TypeReference<List<UserResponse>>() {
        });
    }

    public static UserResponse getUser(Long id) {
        return get("/users/" + id, new TypeReference<UserResponse>() {
        });
    }

    public static void deleteUser(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + id)).DELETE().build());
    }




    /* Generic Helpers */

    private static <T> T get(String path, TypeReference<T> typeReference) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + path)).header("Content-Type", "application/json").GET().build();

        String body = send(request);
        try {
            return mapper.readValue(body, typeReference);
        } catch (IOException e) {
            throw new ApiException(0, "Could not read server response: " + e.getMessage());
        }
    }

    private static <T> T post(String path, Object payload, Class<T> type) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create(BASE_URL + path))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                    .build();
            String body = send(request);
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new ApiException(0, "Could not read server response: " + e.getMessage());
        }
    }

    private static String send(HttpRequest request) {
        try {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status >= 200 && status < 300) {
                return response.body();
            }
            throw new ApiException(status, extractErrorMessage(response.body(), status));
        } catch (ConnectException e) {
            throw new ApiException(0, "Could not connect to server: " + e.getMessage());
        } catch (IOException e) {
            throw new ApiException(0, "Network error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(0, "Request interrupted: " + e.getMessage());
        }
    }

    private static String extractErrorMessage(String body, int status) {
        try {
            System.out.println(body.chars());
            JsonNode node = mapper.readTree(body);
            if (node.hasNonNull("detail")) {
                return node.get("detail").asText();
            }
        } catch (IOException ignored) {
            // Ignore
        }

        return "Server error with status code: " + status;
    }


}
