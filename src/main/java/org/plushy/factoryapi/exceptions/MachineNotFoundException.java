package org.plushy.factoryapi.exceptions;

public class MachineNotFoundException extends RuntimeException {
    public MachineNotFoundException(String id) {
        super("Could not find machine id: " + id);
    }
}
