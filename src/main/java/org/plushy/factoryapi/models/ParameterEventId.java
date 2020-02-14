package org.plushy.factoryapi.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ParameterEventId implements Serializable {
    private String key;
    private String machineKey;
}
