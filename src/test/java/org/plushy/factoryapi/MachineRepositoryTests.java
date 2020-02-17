package org.plushy.factoryapi;

import static org.mockito.Mockito.when;
import org.plushy.factoryapi.controller.MachineController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.plushy.factoryapi.controller.MachineController;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterEvent;
import org.plushy.factoryapi.models.ParameterEventId;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterEventRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {MachineController.class, MachineRepository.class})
@SpringBootTest
public class MachineRepositoryTests {
	@Autowired
	private ParameterRepository parameterRepo;
	@Autowired
	private ParameterEventRepository parameterEventRepo;

	@Test
	public void testGetAllReals() throws Exception {
        List<Parameter> realTypes = parameterRepo.findAllByType("real");
    }

	@Test
	public void testBasicAggregateQuery() throws Exception {
        LocalDateTime start = LocalDateTime.of(2019, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2019, 1, 1, 0, 50);
        LocalDateTime past = LocalDateTime.of(2019, 1, 1, 0, 59);

        String paramKey = "moisture";
        String machineKey = "flufferizer";
        String paramKey2 = "heat";
        Parameter param = parameterRepo.findByParameterKeyAndMachineKey(paramKey, machineKey).orElse(null);
        Parameter param2 = parameterRepo.findByParameterKeyAndMachineKey(paramKey2, machineKey).orElse(null);

        ParameterEvent event = new ParameterEvent(start, "99");
        event.setParameter(param);
        this.parameterEventRepo.save(event);

        event = new ParameterEvent(past, "96");
        event.setParameter(param);
        this.parameterEventRepo.save(event);

        event = new ParameterEvent(end, "93");
        event.setParameter(param2);
        this.parameterEventRepo.save(event);

        List<ParameterEvent> allEvents = parameterEventRepo.findAllParameterEvents(machineKey, paramKey, start, end);
        Assert.isTrue(allEvents.size() == 1);
        Assert.isTrue(allEvents.get(0).getValue() == "99");
        
        List<ParameterEvent> latestEvent = parameterEventRepo.getLatest(machineKey, paramKey, PageRequest.of(0, 1));
        Assert.isTrue(latestEvent.size() == 1);
        Assert.isTrue(latestEvent.get(0).getValue() == "96");
	}
}
