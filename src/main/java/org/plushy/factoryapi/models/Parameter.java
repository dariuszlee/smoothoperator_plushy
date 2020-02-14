package org.plushy.factoryapi.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class Parameter {
    @EmbeddedId
    private ParameterEventId parameterId;
    @Column(insertable=false, updatable=false)
    private String machineKey;
    private String name;
    private String type;
    private String unit;

    @OneToMany(mappedBy = "eventId")
    List<ParameterEvent> events;
}
