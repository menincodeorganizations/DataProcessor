package com.vsamma.dataprocessor.dto;

//import org.springframework.hateoas.ResourceSupport;

//import com.vsamma.dataprocessor.model.PersonID;

import lombok.Data;

@Data
public class PersonDTO {
	Long id;
	String name;
	Integer age;
	String address;
	String team;
}
