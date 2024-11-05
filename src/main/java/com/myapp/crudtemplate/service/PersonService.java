package com.myapp.crudtemplate.service;

import com.myapp.crudtemplate.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    PersonDTO createPerson(PersonDTO personDTO);
    List<PersonDTO> getAllPersons();
    PersonDTO getPersonById(Long id);
    PersonDTO updatePerson(Long id, PersonDTO personDTO);
    void deletePerson(Long id);
}