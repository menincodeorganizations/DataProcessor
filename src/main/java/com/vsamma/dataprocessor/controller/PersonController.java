package com.vsamma.dataprocessor.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.storage.StorageService;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    StorageService storageService;
    
    @RequestMapping(method = GET, path ="")
    public List<PersonDTO> getPersons() {
    	return storageService.findAllPersons();
    }
}
