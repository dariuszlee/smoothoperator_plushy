package org.plushy.factoryapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.plushy.factoryapi.exceptions.ServerErrorException;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterAggregation;
import org.plushy.factoryapi.models.ParameterEvent;
import org.plushy.factoryapi.models.ParameterUpdateEvent;
import org.plushy.factoryapi.repositories.ParameterEventRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class MachineService {
    @Autowired
    private ParameterEventRepository parameterEventRepository;
    @Autowired
    private ParameterRepository parameterRepository;

    public List<ParameterEvent> getLatestForEachParameter() {
        final List<Parameter> allParameters = parameterRepository.findAll();

        final List<ParameterEvent> latestEvents = new ArrayList<ParameterEvent>();
        for (final Parameter p : allParameters) {
            final List<ParameterEvent> topEvent = parameterEventRepository.getLatest(p.getMachineKey(), p.getParameterKey(),
                    PageRequest.of(0, 1));
            if (!topEvent.isEmpty()) {
                latestEvents.add(topEvent.get(0));
            }
        }
        return latestEvents;
    }

    public List<ParameterAggregation> getAggregation(final LocalDateTime start, final LocalDateTime end) {
        final String aggregatableTypeReal = "real";
        final String aggregatableTypeInt = "int";
        final List<Parameter> aggregatableParams = parameterRepository.findAllByType(aggregatableTypeReal);
        aggregatableParams.addAll(parameterRepository.findAllByType(aggregatableTypeInt));

        final List<ParameterAggregation> allParametersAggregated = new ArrayList<ParameterAggregation>();
        for (final Parameter parameter : aggregatableParams) {
            final List<ParameterEvent> inTimeWindow = parameterEventRepository
                    .findAllParameterEvents(parameter.getMachineKey(), parameter.getParameterKey(), start, end);
            if (!inTimeWindow.isEmpty()) {
                final ParameterAggregation paramAggregated = this.getParameterAggregation(parameter, inTimeWindow);
                allParametersAggregated.add(paramAggregated);
            }
        }

        return allParametersAggregated;
    }

    private ParameterAggregation getParameterAggregation(final Parameter param, final List<ParameterEvent> events) {
        final double min = Double.valueOf(events.get(0).getValue());
        final double max = Double.valueOf(events.get(events.size() - 1).getValue());
        double median = 0;
        final int middleEvent = (int) events.size() / 2;
        if (events.size() % 2 == 1) {
            median = Double.valueOf(events.get(middleEvent).getValue());
        } else {
            median = (Double.valueOf(events.get(middleEvent).getValue())
                    + Double.valueOf(events.get(middleEvent - 1).getValue())) / 2;
        }

        double average = 0;
        for (final ParameterEvent event : events) {
            average += Double.valueOf(event.getValue());
        }
        average /= events.size();

        final ParameterAggregation result = new ParameterAggregation(param, min, max, median, average);
        return result;
    }

    public void saveParameterEvent(final ParameterUpdateEvent event) {
        final String machineKey = event.getMachineKey();
        try {
            for (final String parameterKey : event.getParameters().keySet()) {
                final String eventValue = event.getParameters().get(parameterKey);
                final Parameter paramForEvent = parameterRepository.findByParameterKeyAndMachineKey(parameterKey, machineKey)
                        .orElseThrow(() -> new ServerErrorException("Cannot save event " + event + " because parameter "
                                + parameterKey + " with machine " + machineKey + " doesn't exist."));

                final ParameterEvent paramEvent = new ParameterEvent(LocalDateTime.now(), eventValue);
                paramEvent.setParameter(paramForEvent);
                parameterEventRepository.save(paramEvent);
            }
        } catch (final Exception e) {
            throw new ServerErrorException("Cannot save event: " + event);
        }
    }

    public List<Parameter> findAll() {
        return parameterRepository.findAll();
    }

    public void deleteAll() {
        parameterEventRepository.deleteAll();
    }

    public Map<String, Parameter> findAllByMachineKeyAsMap(final String machineKey) {
        final Map<String, Parameter> parameters = new HashMap<String, Parameter>();
        parameterRepository.findAllByMachineKey(machineKey).forEach(x -> parameters.put(x.getParameterKey(), x));
        return parameters;
    }
}
