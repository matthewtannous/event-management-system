package com.mat.Backend.application.organizer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {
    private final OrganizerService service;

    public OrganizerController(OrganizerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizerResponse create(@Valid @RequestBody OrganizerRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<OrganizerResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrganizerResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

//    @PutMapping("/{id}")
//    public OrganizerResponse update(@PathVariable Long id, @Valid @RequestBody OrganizerRequest request) {
//        return service.update(id, request);
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
