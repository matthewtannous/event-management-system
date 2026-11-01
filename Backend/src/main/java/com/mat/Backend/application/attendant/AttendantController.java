package com.mat.Backend.application.attendant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendants")
public class AttendantController {
    private final AttendantService service;

    public AttendantController(AttendantService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendantResponse create(@Valid @RequestBody AttendantRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<AttendantResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AttendantResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

//    @PutMapping("/{id}")
//    public AttendantResponse update(@PathVariable Long id, @Valid @RequestBody AttendantRequest request) {
//        return service.update(id, request);
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
