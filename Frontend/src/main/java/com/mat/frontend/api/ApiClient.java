/**
 * Organizer and Participant table do not need to be directly fetched from backend.
 * We do not have to add them in ApiClient, since their data is used
 */
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
import java.time.LocalDateTime;
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

    public static UserResponse createUser(String username, String email, String firstName, String lastName, String password) {
        return post("/users", new UserRequest(username, email, firstName, lastName, password), UserResponse.class);
    }

    public static UserResponse updateUser(Long id, String username, String email, String firstName, String lastName, String password) {
        return put("/users/" + id, new UserRequest(username, email, firstName, lastName, password), UserResponse.class);
    }

    public static void deleteUser(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + id)).DELETE().build());
    }

    // Other
    public static List<UserResponse> getUsersNotInEvent(Long eventId) {
        return get("/users/not-in-event/" + eventId, new TypeReference<List<UserResponse>>() {
        });
    }


    /* Events */
    public static List<EventResponse> getEvents() {
        return get("/events", new TypeReference<List<EventResponse>>() {
        });
    }

    public static EventResponse getEvent(Long id) {
        return get("/events/" + id, new TypeReference<EventResponse>() {
        });
    }

    public static EventResponse createEvent(String name, String description, LocalDateTime fromTime, LocalDateTime toTime, Double ticketPrice, String location, Integer capacity, Integer registered) {
        return post("/events", new EventRequest(name, description, fromTime, toTime, ticketPrice, location, capacity, registered), EventResponse.class);
    }

    public static EventResponse updateEvent(Long id, String name, String description, LocalDateTime fromTime, LocalDateTime toTime, Double ticketPrice, String location, Integer capacity, Integer registered) {
        return put("/events/" + id, new EventRequest(name, description, fromTime, toTime, ticketPrice, location, capacity, registered), EventResponse.class);
    }

    public static void deleteEvent(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/events/" + id)).DELETE().build());
    }

    // Other for Events (get users)
    public static List<EventResponse> getEventsWithUserParticipating(Long userId) {
        return get("/events/participating/" + userId, new TypeReference<List<EventResponse>>() {
        });
    }

    public static List<EventResponse> getEventsWithUserOrganizing(Long userId) {
        return get("/events/organizing/" + userId, new TypeReference<List<EventResponse>>() {
        });
    }


    /* Announcements */
    public static List<AnnouncementResponse> getAnnouncements() {
        return get("/announcements", new TypeReference<List<AnnouncementResponse>>() {
        });
    }

    public static AnnouncementResponse getAnnouncement(Long id) {
        return get("/announcements/" + id, new TypeReference<AnnouncementResponse>() {
        });
    }

    public static AnnouncementResponse createAnnouncement(String message, boolean isPublic, Long userId, Long eventId) {
        return post("/announcements", new AnnouncementRequest(message, isPublic, userId, eventId), AnnouncementResponse.class);
    }

    public static AnnouncementResponse updateAnnouncement(Long id, String message, boolean isPublic, Long userId, Long eventId) {
        return put("/announcements/" + id, new AnnouncementRequest(message, isPublic, userId, eventId), AnnouncementResponse.class);
    }

    public static void deleteAnnouncement(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/announcements/" + id)).DELETE().build());
    }

    public static List<AnnouncementResponseDetailed> getAnnouncementsForEvent(Long eventId, boolean onlyPublic) {
        return get("/announcements/event/" + eventId + "?onlyPublic=" + onlyPublic, new TypeReference<List<AnnouncementResponseDetailed>>() {
        });
    }

    /* Organizers */
    public static List<OrganizerResponse> getOrganizers() {
        return get("/organizers", new TypeReference<List<OrganizerResponse>>() {
        });
    }

    public static OrganizerResponse getOrganizer(Long id) {
        return get("/organizers/" + id, new TypeReference<OrganizerResponse>() {
        });
    }

    public static OrganizerResponse createOrganizer(Long userId, Long eventId) {
        return post("/organizers", new OrganizerRequest(userId, eventId), OrganizerResponse.class);
    }

    public static OrganizerResponse updateOrganizer(Long id, Long userId, Long eventId) {
        return put("/organizers/" + id, new OrganizerRequest(userId, eventId), OrganizerResponse.class);
    }

    public static void deleteOrganizer(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/organizers/" + id)).DELETE().build());
    }

    public static boolean userOrganizesEvent(Long userId, Long eventId) {
        return get("/organizers/user-in-event?userId=" + userId + "&eventId=" + eventId, new TypeReference<Boolean>() {
        });
    }

    /* Participants */
    public static List<ParticipantResponse> getParticipants() {
        return get("/participants", new TypeReference<List<ParticipantResponse>>() {
        });
    }

    public static ParticipantResponse getParticipant(Long id) {
        return get("/participants/" + id, new TypeReference<ParticipantResponse>() {
        });
    }

    public static ParticipantResponse createParticipant(Long userId, Long eventId) {
        return post("/participants", new ParticipantRequest(userId, eventId), ParticipantResponse.class);
    }

    public static ParticipantResponse updateParticipant(Long id, Long userId, Long eventId) {
        return put("/participants/" + id, new ParticipantRequest(userId, eventId), ParticipantResponse.class);
    }

    public static void deleteParticipant(Long id) {
        send(HttpRequest.newBuilder(URI.create(BASE_URL + "/participants/" + id)).DELETE().build());
    }

    public static boolean userParticipatesInEvent(Long userId, Long eventId) {
        return get("/participants/user-in-event?userId=" + userId + "&eventId=" + eventId, new TypeReference<Boolean>() {
        });
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
            HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + path)).header("Content-Type", "application/json").header("Accept", "application/json").POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload))).build();
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


    private static <T> T put(String path, Object payload, Class<T> type) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + path)).header("Content-Type", "application/json").header("Accept", "application/json").PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload))).build();
            String body = send(request);
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new ApiException(0, "Could not read server response: " + e.getMessage());
        }
    }

}
