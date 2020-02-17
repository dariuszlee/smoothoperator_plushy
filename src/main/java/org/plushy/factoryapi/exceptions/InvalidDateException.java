package org.plushy.factoryapi.exceptions;

import java.time.LocalDateTime;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(final LocalDateTime start, final LocalDateTime end) {
        super("Start date: " + start + " and end date: " + end + " are invalid.");
    }
}
