package org.plushy.factoryapi.models;

import java.util.Map;

public class MachineUpdateEvent {
    private String machineKey;
    private Map<String, String> parameters;

    public MachineUpdateEvent(String machineKey, Map<String, String> parameters) {
        this.machineKey = machineKey;
        this.parameters = parameters;
    }
    
    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }
    public Map<String, String> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

}
