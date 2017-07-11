package com.vsamma.dataprocessor.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;
import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.model.Person;
import com.vsamma.dataprocessor.repository.PersonRepository;

import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
public class FileSystemStorageService implements StorageService {
	
	private final Path rootLocation;
	
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    ModelMapper modelMapper;
	
	@Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.modelMapper = new ModelMapper();
    }
	
	@Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            
            //Insert file data to DB
            this.storeContentsToDb(file);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
	
	@Override
	public void storeContentsToDb(MultipartFile file){
		//empty file check was in "store" function
		FileInputStream fis = null;
		
        try {
        	//Get imported file path
            String filePath = this.rootLocation + "/" + file.getOriginalFilename();
            //Create a new FileReader
            Reader in = new FileReader(filePath);
            //Parse file lines to CSV records
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            //Create an empty list for entities
            List<Person> personList = new ArrayList<>();

            for (CSVRecord record : records) {
            	personList.add(Person.of(Long.valueOf(record.get(0)), record.get(1), Integer.valueOf(record.get(2)), record.get(3), record.get(4)));
            }
            
            //Bulk insert persons' data to DB
            personRepository.bulkPersist(personList);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
	}
	
	@Override
	public PersonDTO createPerson(Long id, String name, Integer age, String address, String team){
		Person p = Person.of(id, name, age, address, team);
		personRepository.saveAndFlush(p);
		PersonDTO pDTO = modelMapper.map(p, PersonDTO.class);
		System.out.println("Person added:" + pDTO.getName());
		return pDTO;
	}
	
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }
    
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    
    @Override
    public List<PersonDTO> findAllPersons(){
    	List<Person> persons = personRepository.findAll();
    	java.lang.reflect.Type targetListType = new TypeToken<List<PersonDTO>>() {}.getType();
    	List<PersonDTO> personDTOs = modelMapper.map(persons, targetListType);
    	return personDTOs;
    }
    
    @Override
    public List<PersonDTO> findByNameContaining(String query){
    	List<Person> persons = personRepository.findByNameContaining(query);
    	java.lang.reflect.Type targetListType = new TypeToken<List<PersonDTO>>() {}.getType();
    	List<PersonDTO> personDTOs = modelMapper.map(persons, targetListType);
    	return personDTOs;
    }
    
    @Override
    public List<PersonDTO> findMostRelevantPeople(String name){
    	List<Person> persons = personRepository.findMostRelevantPeople(name);
    	java.lang.reflect.Type targetListType = new TypeToken<List<PersonDTO>>() {}.getType();
    	List<PersonDTO> personDTOs = modelMapper.map(persons, targetListType);
    	return personDTOs;
    }
}
