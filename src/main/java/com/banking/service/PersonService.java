package com.banking.service;

import com.banking.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    Person findById(Long id);
    List<Person> findAll();
    Person createPerson(Person person);
    Person updatePerson(Person person);
    void deletePerson(Person person);

}
