package com.vsamma.dataprocessor.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.vsamma.dataprocessor.dto.PersonDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);
    
    void storeContentsToDb(MultipartFile file);
    
    public PersonDTO createPerson(Long id, String name, Integer age, String address, String team);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
    
	public List<PersonDTO> findAllPersons();
	
	public List<PersonDTO> findByNameContaining(String query);
}