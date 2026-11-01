package com.mat.Backend.application.participant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantService service;

    public ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResponse create(@Valid @RequestBody ParticipantRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ParticipantResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ParticipantResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

//    @PutMapping("/{id}")
//    public ParticipantResponse update(@PathVariable Long id, @Valid @RequestBody ParticipantRequest request) {
//        return service.update(id, request);
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
