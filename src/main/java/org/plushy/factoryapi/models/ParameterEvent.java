package org.plushy.factoryapi.models;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class ParameterEvent {
    @EmbeddedId ParameterEventId eventId;
    private LocalDateTime dateTime;
    private String value;
}
