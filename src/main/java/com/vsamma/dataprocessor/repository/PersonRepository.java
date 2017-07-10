package com.vsamma.dataprocessor.repository;

import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.model.*;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QueryDslPredicateExecutor<Person>, CustomRepository{
//public interface PersonRepository extends JpaRepository<Person, Long>, QueryDslPredicateExecutor<Person>{
	List<Person> findByNameContaining(String name);
}
