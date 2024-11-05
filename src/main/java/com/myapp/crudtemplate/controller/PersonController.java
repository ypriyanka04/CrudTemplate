package com.myapp.crudtemplate.controller;

import com.myapp.crudtemplate.dto.LoginRequestDTO;
import com.myapp.crudtemplate.dto.PersonDTO;
import com.myapp.crudtemplate.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController{

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @Operation(summary = "Create a new person", description = "Adds a new person to the database")
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO){
        PersonDTO createdPerson = personService.createPerson(personDTO);
        return ResponseEntity.ok(createdPerson);
    }

    @Operation(summary = "Get all persons", description = "Fetches all persons from the database")
    @GetMapping
    public List<PersonDTO> getAllPersons(){
        return personService.getAllPersons();
    }

    @Operation(summary = "Get person by ID", description = "Fetches a person by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id){
        PersonDTO personDTO = personService.getPersonById(id);
        return ResponseEntity.ok(personDTO);
    }

    @Operation(summary = "Update person by ID", description = "Updates a person's details by their ID")
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id,@RequestBody PersonDTO personDTO){
        PersonDTO updatedPerson = personService.updatePerson(id,personDTO);
        return ResponseEntity.ok(updatedPerson);
    }

    @Operation(summary = "Delete person by ID", description = "Deletes a person by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User login", description = "Authenticate user with username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        boolean valid = personService.validateLogin(loginRequestDTO.getUsername(),loginRequestDTO.getPassword());
        if(valid){
            return ResponseEntity.ok("Login successful");
        } else{
            return ResponseEntity.status(401).body("Invalid username or password");
        }

    }

}