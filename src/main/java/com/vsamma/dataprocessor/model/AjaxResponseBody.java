package com.vsamma.dataprocessor.model;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.vsamma.dataprocessor.dto.PersonDTO;

import lombok.*;


@Embeddable 
@Getter @Setter @ToString
@NoArgsConstructor
public class AjaxResponseBody {
	
	List<PersonDTO> results;
}
