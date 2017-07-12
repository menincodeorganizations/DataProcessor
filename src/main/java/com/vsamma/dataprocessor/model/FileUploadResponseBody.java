package com.vsamma.dataprocessor.model;

import java.util.List;

import javax.persistence.Embeddable;

import com.vsamma.dataprocessor.dto.PersonDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable 
@Getter @Setter @ToString
@NoArgsConstructor
public class FileUploadResponseBody {
	String fileId;
	String message;
}
