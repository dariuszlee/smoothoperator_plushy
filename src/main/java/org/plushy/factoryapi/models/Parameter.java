package org.plushy.factoryapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class Parameter {
    @Id
    private String key;
    private String machineKey;
    private String name;
    private String type;
    private String unit;
}
