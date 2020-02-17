package org.plushy.factoryapi.models;

import java.io.Serializable;

public class ParameterEventId implements Serializable {
    private String parameterKey;
    private String machineKey;

    public ParameterEventId() {
    }

    public ParameterEventId(final String parameterKey, final String machineKey) {
        this.parameterKey = parameterKey;
        this.machineKey = machineKey;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(final String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(final String machineKey) {
        this.machineKey = machineKey;
    }
}
