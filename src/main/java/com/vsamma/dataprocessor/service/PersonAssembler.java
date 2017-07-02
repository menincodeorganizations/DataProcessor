package com.vsamma.dataprocessor.service;

import com.vsamma.dataprocessor.model.*;
import com.vsamma.dataprocessor.controller.PersonController;
import com.vsamma.dataprocessor.dto.*;

import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriTemplate;

@Service
//public class PersonAssembler extends ResourceAssemblerSupport<Person, PersonDTO> {
public class PersonAssembler {
//
//    UriTemplate uriTemplate;
//	public PersonAssembler(Class<?> controllerClass, Class<PersonDTO> resourceType) {
//		super(controllerClass, resourceType);
//		// TODO Auto-generated constructor stub
//	}
//	
//    // Replace the original constructor with the following code
//    public PersonAssembler() {
//        super(PersonController.class, PersonDTO.class);
//
//        AnnotationMappingDiscoverer discoverer = new AnnotationMappingDiscoverer(RequestMapping.class);
//        try {
//            String mapping = discoverer.getMapping(PersonController.class,
//            		PersonController.class.getMethod("getPerson", Long.class)); 
//
//            uriTemplate = new UriTemplate(mapping);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
	
}
