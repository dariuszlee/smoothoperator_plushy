package org.plushy.factoryapi.exceptions;

public class MachineNotFoundException extends RuntimeException {
	MachineNotFoundException(String id) {
		super("Could not find machine" + id);
	}
}
