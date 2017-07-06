package com.vsamma.dataprocessor.repository;

import java.util.List;

import com.vsamma.dataprocessor.dto.PersonDTO;

public interface CustomRepository {
	List<PersonDTO> findSimilarResults(String query);
}
