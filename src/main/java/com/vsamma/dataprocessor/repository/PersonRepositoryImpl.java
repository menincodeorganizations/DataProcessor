package com.vsamma.dataprocessor.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;
import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.model.QPerson;

import org.springframework.beans.factory.annotation.Autowired;

public class PersonRepositoryImpl implements CustomRepository {
	@Autowired
	EntityManager em;
	
	QPerson person = QPerson.person;
//	QPersonDTO person = QPersonDTO.personDTO;
	
//	@Override
//	public List<PersonDTO> findSimilarResults(String query){
//		return new JPAQuery(em)
//				.from(person)
//				.where(person.name.)
//				.distinct().list(person);
////		return null;
//	}
}
