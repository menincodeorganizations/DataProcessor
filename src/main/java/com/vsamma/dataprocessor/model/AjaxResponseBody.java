package com.vsamma.dataprocessor.model;

import java.util.List;

import javax.persistence.Entity;

import com.vsamma.dataprocessor.dto.PersonDTO;

import lombok.*;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
public class AjaxResponseBody {
	List<PersonDTO> results;

}
