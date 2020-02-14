package org.plushy.factoryapi.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
@IdClass(ParameterEventId.class)
public class Parameter {
    // @EmbeddedId
    // private ParameterEventId parameterId;
    @Id
    private String key;
    @Id
    @Column(insertable=false, updatable=false)
    private String machineKey;
    private String name;
    private String type;
    private String unit;

    @OneToMany
    @OrderBy("dateTime")
    List<ParameterEvent> events;

    public Parameter(){}

    public Parameter(String key, String machineKey, String name, String type, String unit){
        this.key = key;
        this.machineKey = machineKey;
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<ParameterEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ParameterEvent> events) {
        this.events = events;
    }
}
