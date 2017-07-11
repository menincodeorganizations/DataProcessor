package com.vsamma.dataprocessor.repository;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;
import com.vsamma.dataprocessor.model.Person;
import com.vsamma.dataprocessor.model.QPerson;
import com.vsamma.dataprocessor.repository.CustomRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.transaction.annotation.Transactional;

public class PersonRepositoryImpl implements CustomRepository {
    @Autowired
    EntityManager em;
    
    @Autowired
    JdbcTemplate template;
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
    
    @Override
    @Transactional
    public void bulkPersist(List<Person> entities){
    	template.execute("truncate table person");
    	template.batchUpdate("insert into person (id, name, age, address, team) values (?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {

    	      @Override
    	      public void setValues(PreparedStatement ps, int i) throws SQLException {
    	    	  Person person = entities.get(i);
    	    	  ps.setLong(1, person.getId());
    	    	  ps.setString(2, person.getName());
    	    	  ps.setInt(3, person.getAge());
    	    	  ps.setString(4, person.getAddress());
    	    	  ps.setString(5, person.getTeam());
    	      }

    	      @Override
    	      public int getBatchSize() {
    	        return entities.size();
    	      }
    	    });
    	  }
    
}
