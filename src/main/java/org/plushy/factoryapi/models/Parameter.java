package org.plushy.factoryapi.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@IdClass(ParameterEventId.class)
public class Parameter implements Serializable {
    // @Id
    // @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @JsonProperty("key")
    @Id
    private String parameterKey;
    @Id
    private String machineKey;
    private String name;
    private String type;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "fk_machine", referencedColumnName = "key")
    private Machine machine;

    public Parameter() {
    }

    public Parameter(final String key, final String machineKey, final String name, final String type,
            final String unit) {
        this.parameterKey = key;
        this.machineKey = machineKey;
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(final String machineKey) {
        this.machineKey = machineKey;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(final Machine machine) {
        this.machine = machine;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(final String parameterKey) {
        this.parameterKey = parameterKey;
    }
}
