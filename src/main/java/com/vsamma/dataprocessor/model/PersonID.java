package com.vsamma.dataprocessor.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class PersonID implements Serializable {
    Long id;
}

