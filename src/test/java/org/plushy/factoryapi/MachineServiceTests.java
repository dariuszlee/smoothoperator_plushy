package org.plushy.factoryapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterEvent;
import org.plushy.factoryapi.repositories.ParameterEventRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.plushy.factoryapi.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

@SpringBootTest
public class MachineServiceTests {
    @Autowired
    private MachineService machineService;

    @MockBean
    private ParameterEventRepository parameterEventRepository;

    @MockBean
    private ParameterRepository parameterRepository;

	@Test
	public void testGetAggregate() throws Exception {
        
    }

	@Test
	public void testGetLatest() throws Exception {
        String paramKey = "someParameter";
        String paramKey2 = "someParameter2";
        String machineKey = "someMachine";
        Parameter param = new Parameter(paramKey, machineKey, "Param", 
                "real", "%");
        Parameter param2 = new Parameter(paramKey2, machineKey, "Param", 
                "real", "%");
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(param);
        params.add(param2);

        List<ParameterEvent> paramEvent = new ArrayList<ParameterEvent>();
        ParameterEvent event = new ParameterEvent(LocalDateTime.now(), "99");
        event.setParameter(param);
        paramEvent.add(event);

		when(parameterRepository.findAll()).thenReturn(params);
		when(parameterEventRepository.getLatest(eq(machineKey), eq(paramKey), any(PageRequest.class))).thenReturn(paramEvent);

        List<ParameterEvent> latestEvents =  machineService.getLatestForEachParameter();

        Assert.isTrue(latestEvents.size() == 1);
        Assert.isTrue(latestEvents.get(0).getParameter().getParameterKey().equals(paramKey));
	}
}
