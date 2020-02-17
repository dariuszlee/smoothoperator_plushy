package org.plushy.factoryapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
class MachineControllerExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ParameterUpdateError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String parameterUpdateErrorHandler(final ParameterUpdateError ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String parameterUpdateErrorHandler(final NumberFormatException ex) {
        return "A number is incorrectly formatted in the request.";
    }

    @ResponseBody
    @ExceptionHandler(MachineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String machineNotFoundHandler(final MachineNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String internalServerError(final ServerErrorException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidDateHandler(final InvalidDateException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        final String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        if (type.contains("LocalDateTime")) {
            type = "ISO DateTime";
        }
        final Object value = ex.getValue();
        final String message = String.format("'%s' should be a valid %s and '%s' isn't", name, type, value);

        return message;
    }
}
