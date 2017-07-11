package com.vsamma.dataprocessor.repository;

import java.util.List;

import com.vsamma.dataprocessor.model.Person;

public interface CustomRepository {
	List<Person> findMostRelevantPeople(String name);
	public void bulkPersist(List<Person> entities);
}
