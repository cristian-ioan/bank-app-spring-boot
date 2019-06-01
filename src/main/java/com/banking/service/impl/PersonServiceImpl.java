package com.banking.service.impl;

import com.banking.model.Person;
import com.banking.repository.PersonRepository;
import com.banking.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personService")
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }
}
