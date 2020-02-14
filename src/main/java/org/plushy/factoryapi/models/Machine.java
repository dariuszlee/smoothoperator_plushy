package org.plushy.factoryapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    private String name;

    public Machine(){}

    public Machine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
