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
@IdClass(ParameterEventId.class)
public class ParameterEvent {
    @Id
    String key;
    @Id
    String machineKey;
    // ParameterEventId eventId;
    private LocalDateTime dateTime;
    private String value;
}
