package com.vsamma.dataprocessor.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;
import com.vsamma.dataprocessor.model.Person;
import com.vsamma.dataprocessor.model.QPerson;
import com.vsamma.dataprocessor.repository.CustomRepository;


import org.springframework.beans.factory.annotation.Autowired;

public class PersonRepositoryImpl implements CustomRepository {
    @Autowired
    EntityManager em;
//    
    QPerson person = QPerson.person;
//    
    public List<Person> findMostRelevantPeople(String name){
    	return new JPAQuery<Person>(em)
    			.from(person)
    			.where(person.name.containsIgnoreCase(name))
    			.limit(20)
    			.fetch();
    }
}
