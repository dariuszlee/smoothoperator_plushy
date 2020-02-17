package org.plushy.factoryapi.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterUpdateEvent {
    private String machineKey;
    private Map<String, String> parameters;

    @JsonCreator
    public ParameterUpdateEvent(@JsonProperty(required = true) final String machineKey,
            @JsonProperty(required = true) final Map<String, String> parameters) {
        this.machineKey = machineKey;
        this.parameters = parameters;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(final String machineKey) {
        this.machineKey = machineKey;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ParameterUpdateEvent [machineKey=" + machineKey + ", parameters=" + parameters + "]";
    }

}
