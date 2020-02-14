package org.plushy.factoryapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class Parameters {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    private String key;
    private String machineKey;
    private String name;
    private String type;
    private String unit;

}
