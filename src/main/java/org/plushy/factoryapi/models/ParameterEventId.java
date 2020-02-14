package org.plushy.factoryapi.models;

import javax.persistence.Embeddable;

@Embeddable
public class ParameterEventId {
    private String parameterKey;
    private String machineKey;
}
