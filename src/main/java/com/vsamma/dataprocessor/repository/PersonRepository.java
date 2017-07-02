package com.vsamma.dataprocessor.repository;

import com.vsamma.dataprocessor.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
