package org.plushy.factoryapi.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class Machine {
    @Id
    private String key;
    private String name;
    @OneToMany(mappedBy = "machineKey")
    private List<Parameter> parameters;

    public Machine(){}

    public Machine(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
