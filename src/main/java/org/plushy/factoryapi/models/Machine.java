package org.plushy.factoryapi.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Machine {
    @Id
    private String key;
    private String name;

    public Machine(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Machine() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
