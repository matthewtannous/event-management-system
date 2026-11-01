package com.mat.Backend.application.announcement;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    private final AnnouncementService service;

    public AnnouncementController(AnnouncementService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnnouncementResponse create(@Valid @RequestBody AnnouncementRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<AnnouncementResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AnnouncementResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public AnnouncementResponse update(@PathVariable Long id, @Valid @RequestBody AnnouncementRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
