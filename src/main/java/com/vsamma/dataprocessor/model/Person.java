package com.vsamma.dataprocessor.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(staticName = "of")
public class Person {
//	@EmbeddedId
//	PersonID id;
    @Id
    @GeneratedValue
    Long id;
	String name;
	Integer age;
	String address;
	String team;
}
