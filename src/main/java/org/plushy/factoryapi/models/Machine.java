package org.plushy.factoryapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    private String key;
    private String name;

    public Machine(Long id, String key, String name) {
        Id = id;
        this.key = key;
        this.name = name;
    }

    public Machine(){}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
