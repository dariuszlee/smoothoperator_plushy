package org.plushy.factoryapi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.plushy.factoryapi.exceptions.InvalidDateException;
import org.plushy.factoryapi.exceptions.ParameterUpdateError;
import org.plushy.factoryapi.models.ParameterUpdateEvent;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterAggregation;
import org.plushy.factoryapi.models.ParameterEvent;
import org.plushy.factoryapi.services.MachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public final class MachineController {
    @Autowired
    private MachineService machineService;

    private final Logger logger = LoggerFactory.getLogger(MachineController.class);

    private final int maxPercentageValue = 100;
    private final int minPercentageValue = 0;
    private final int minQuantityValue = 0;

    @GetMapping("/parameters")
    public List<Parameter> all() {
        return machineService.findAll();
    }

    @PostMapping("/parameters")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus add(@RequestBody final ParameterUpdateEvent e) {
        logger.info("/parameter [POST]" + e);
        if (!this.isValidUpdateEvent(e)) {
            throw new ParameterUpdateError(
                    "Machine Key or Parameters may not exist. Ensure that they exist and that the parameters conform to data types and units.");
        }

        machineService.saveParameterEvent(e);
        return HttpStatus.CREATED;
    }

    @GetMapping("/parameters/latest")
    public List<ParameterEvent> getLatest() {
        logger.info("/parameters/latest[GET] request received.");
        return machineService.getLatestForEachParameter();
    }

    @DeleteMapping("/parameters")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        logger.info("/parameters/[DELETE] request received.");
        machineService.deleteAll();
    }

    @GetMapping("/parameters/aggregated/{minutesBeforeNow}")
    public List<ParameterAggregation> allAggregatedBeforeNow(
            @PathVariable("minutesBeforeNow") final long minutesBeforeNow) {
        logger.info("/parameters/aggregated/" + minutesBeforeNow + "[GET] request received.");
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime minutesBeforeLocalTime = LocalDateTime.now().minusMinutes(minutesBeforeNow);
        return this.allAggregated(minutesBeforeLocalTime, now);
    }

    @GetMapping("/parameters/aggregated")
    public List<ParameterAggregation> allAggregated(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endDate) {
        logger.info("/parameters/aggregated/" + "[GET] request received with start and end times as: " + startDate + " "
                + endDate);
        if (!this.isValidDate(startDate, endDate)) {
            throw new InvalidDateException(startDate, endDate);
        }
        return machineService.getAggregation(startDate, endDate);
    }

    private boolean isValidDate(final LocalDateTime startDate, final LocalDateTime endDate) {
        return endDate.isAfter(startDate);
    }

    private boolean isValidUpdateEvent(final ParameterUpdateEvent event) {
        final Map<String, Parameter> validParameters = machineService.findAllByMachineKeyAsMap(event.getMachineKey());
        // logger.info("Params: " + validParameters);
        for (final String eventParameterKey : event.getParameters().keySet()) {
            if (!validParameters.containsKey(eventParameterKey)) {
                return false;
            }
            final Parameter param = validParameters.get(eventParameterKey);
            final String stringVal = event.getParameters().get(eventParameterKey);
            final String unit = param.getUnit();
            switch (param.getType()) {
            case "real":
                final double doubleVal = Double.valueOf(stringVal);
                switch (unit) {
                case "joule":
                    break;
                case "%":
                    if (doubleVal > this.maxPercentageValue || doubleVal < this.minPercentageValue) {
                        return false;
                    }
                    break;
                default:
                    return false;
                }
                break;
            case "int":
                final int intVal = Integer.parseInt(stringVal);
                if (unit != "Quantity" || intVal < this.minQuantityValue) {
                    return false;
                }
                break;
            case "boolean":
                final boolean boolVal = Boolean.parseBoolean(stringVal);
                event.getParameters().put(eventParameterKey, String.valueOf(boolVal));
                break;
            case "string":
                break;
            default:
                return false;
            }
        }
        return true;
    }
}
