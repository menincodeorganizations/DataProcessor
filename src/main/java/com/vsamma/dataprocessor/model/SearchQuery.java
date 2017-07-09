package com.vsamma.dataprocessor.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class SearchQuery {
	@NotBlank(message = "Search field cannot be empty!")
	String query;
}
