package com.vsamma.dataprocessor.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;
//import liquibase.util.csv.CSVReader;
import com.opencsv.CSVReader;
import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.model.Person;
import com.vsamma.dataprocessor.repository.PersonRepository;
import com.vsamma.dataprocessor.service.PersonAssembler;

import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
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

            String fileName = file.getOriginalFilename();
            System.out.println("Filename:"  + fileName);
            System.out.println("rootLocation:"  + this.rootLocation);
            
            File f1 = new File(fileName);
            File f2 = new File(this.rootLocation + fileName);
            File f3 = new File(this.rootLocation + "/" + fileName);
            
            System.out.println("f1 exists: " + f1.exists());
            System.out.println("f2 exists: " + f2.exists());
            System.out.println("f3 exists: " + f3.exists());
            

//            fis = new FileInputStream(new File(fileName));
//            CSVReader reader = new CSVReader(new InputStreamReader(fis));
//            String[] nextLine;
//            reader.readNext();
//            
//            while ((nextLine = reader.readNext()) != null) {
//
////                Country newCountry = new Country(nextLine[0],
////                        Integer.valueOf(nextLine[1]));
////                countries.add(newCountry);
//            	
//            	createPerson(
//            			Long.valueOf(nextLine[0]),
//            			nextLine[1],
//            			Integer.valueOf(nextLine[2]),
//            			nextLine[3],
//            			nextLine[4]
//            			);
//            }
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
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
//		return personAssembler.toResource(p);
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

}
