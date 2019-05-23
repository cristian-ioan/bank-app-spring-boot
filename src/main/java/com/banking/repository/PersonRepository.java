package com.banking.repository;

import com.banking.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GenericRepository<Person> {
    Person findPersonById(long id);
}
