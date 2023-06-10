package com.mrodrigochaves.employees.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrodrigochaves.employees.dto.EmployeesDTO;
import com.mrodrigochaves.employees.service.EmployeesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesService service;

    public EmployeesController(EmployeesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmployeesDTO>> getAll() {
        List<EmployeesDTO> employees = service.getAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeesDTO> getById(@PathVariable("id") Long id) {
        Optional<EmployeesDTO> response = service.getById(id);
        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lastname/{name}")
    public ResponseEntity<List<EmployeesDTO>> getByLastName(@PathVariable("name") String name) {
        List<EmployeesDTO> employees = service.getByLastName(name);
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/departament/{name}")
    public ResponseEntity<List<EmployeesDTO>> getByDepartament(@PathVariable("name") String name) {
        List<EmployeesDTO> employees = service.getByDepartament(name);
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title/{name}")
    public ResponseEntity<List<EmployeesDTO>> getByTitle(@PathVariable("name") String name) {
        List<EmployeesDTO> employees = service.getByTitle(name);
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeesDTO> update(@PathVariable("id") Long id, @RequestBody @Valid EmployeesDTO request) {
        Optional<EmployeesDTO> response = service.update(id, request);
        return response.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeesDTO> create(@RequestBody @Valid EmployeesDTO request) {
        Optional<EmployeesDTO> response = service.create(request);
        return response.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }
}
