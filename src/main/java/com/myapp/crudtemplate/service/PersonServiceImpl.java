package com.myapp.crudtemplate.service;

import com.myapp.crudtemplate.dto.PersonDTO;
import com.myapp.crudtemplate.entity.Person;
import com.myapp.crudtemplate.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = Person.builder()
                .username(personDTO.getUsername())
                .email(personDTO.getEmail())
                .mobile(personDTO.getMobile())
                .address(personDTO.getAddress())
                .password(passwordEncoder.encode(personDTO.getPassword())) // Encode password
                .build();

        person = personRepository.save(person);
        return mapToDTO(person); // Assuming mapToDTO is a helper method to convert Entity to DTO
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return mapToDTO(person);
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        Person updatedPerson = Person.builder()
                .id(person.getId())
                .username(personDTO.getUsername())
                .email(personDTO.getEmail())
                .mobile(personDTO.getMobile())
                .address(personDTO.getAddress())
                .password(person.getPassword() != null && !personDTO.getPassword().isEmpty()
                                  ? passwordEncoder.encode(personDTO.getPassword())
                                  : person.getPassword())
                .build();

        updatedPerson = personRepository.save(updatedPerson);
        return mapToDTO(updatedPerson);
    }


    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    // Map Person entity to PersonDTO
    private PersonDTO mapToDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .username(person.getUsername())
                .email(person.getEmail())
                .mobile(person.getMobile())
                .address(person.getAddress())
                .build();
    }

    @Override
    public boolean validateLogin(String username, String password) {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            return false;
        }
        return passwordEncoder.matches(password, person.getPassword());
    }

}