package org.plushy.factoryapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.plushy.factoryapi.controller.MachineController;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterAggregation;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.plushy.factoryapi.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MachineController.class)
public class MachineControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MachineService machineService;

	@Test
	public void testDuplicateParamKey() throws Exception {
        String machineKey = "machKey";
        String paramKey = "boolKey";
        Parameter boolParam = new Parameter(paramKey, machineKey, paramKey, "boolean", "");
        Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
        paramMap.put(paramKey, boolParam);

		when(machineService.findAllByMachineKeyAsMap(machineKey)).thenReturn(paramMap);

        String contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": true, \"%s\": false}}}", machineKey, paramKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andDo(print())
            .andExpect(status().isCreated());
    }

	@Test
	public void testValidPostEventBool() throws Exception {
        String machineKey = "machKey";
        String paramKey = "boolKey";
        Parameter boolParam = new Parameter(paramKey, machineKey, paramKey, "boolean", "");
        Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
        paramMap.put(paramKey, boolParam);

		when(machineService.findAllByMachineKeyAsMap(machineKey)).thenReturn(paramMap);

        String contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": true}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": \"true\"}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": \"true11\"}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());
	}

	@Test
	public void testIntegerParameterTypesTest() throws Exception {
        String machineKey = "machKey";
        String paramKey = "intKey";
        Parameter boolParam = new Parameter(paramKey, machineKey, paramKey, "int", "Quantity");
        Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
        paramMap.put(paramKey, boolParam);

		when(machineService.findAllByMachineKeyAsMap(machineKey)).thenReturn(paramMap);
        String contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": \"1\"}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": 1}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": 1.0}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isBadRequest());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": -1}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testDoubleParameterTypesTest() throws Exception {
        String machineKey = "machKey";
        String paramKey = "doubleKey";
        Parameter boolParam = new Parameter(paramKey, machineKey, paramKey, "real", "joule");
        Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
        paramMap.put(paramKey, boolParam);

		when(machineService.findAllByMachineKeyAsMap(machineKey)).thenReturn(paramMap);
        String contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": \"1\"}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": 2.0}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        boolParam = new Parameter(paramKey, machineKey, paramKey, "real", "%");
        paramMap = new HashMap<String, Parameter>();
        paramMap.put(paramKey, boolParam);
        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": -1}}", machineKey, paramKey);

		when(machineService.findAllByMachineKeyAsMap(machineKey)).thenReturn(paramMap);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isBadRequest());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": 1}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isCreated());

        contentAsJson = String.format("{\"machineKey\": \"%s\", \"parameters\": {\"%s\": \"asdf\"}}", machineKey, paramKey);
		this.mockMvc.perform(post("/parameters")
            .characterEncoding("utf-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentAsJson))
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testEmptyValidAggregate() throws Exception {
		this.mockMvc.perform(get("/parameters/aggregated")
            .characterEncoding("utf-8")
            .param("startDate", "2015-01-01T15:32:42")
            .param("endDate", "2015-01-01T19:32:42"))
            .andDo(print())
            .andExpect(status().isOk());
	}

	@Test
	public void testEmptyInvalidDateFormat() throws Exception {
		this.mockMvc.perform(get("/parameters/aggregated")
            .characterEncoding("utf-8")
            .param("startDate", "2015-01-01T")
            .param("endDate", "20150101T"))
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testEmptyInvalidTimePeriod() throws Exception {
		this.mockMvc.perform(get("/parameters/aggregated")
            .characterEncoding("utf-8")
            .param("startDate", "2015-01-01T15:32:42")
            .param("endDate", "2015-01-01T12:32:42"))
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testSimpleBefore() throws Exception {
		when(this.machineService.getAggregation(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(new ArrayList<ParameterAggregation>());
        long timeBefore = 5;
		this.mockMvc.perform(get("/parameters/aggregated/" + timeBefore))
            .andExpect(status().isOk())
		    .andExpect(content().string(equalToIgnoringWhiteSpace("[]")));
        verify(this.machineService, times(1)).getAggregation(any(LocalDateTime.class), any(LocalDateTime.class));
	}

	@Test
	public void testSimpleBeforeWithNegative() throws Exception {
        long timeBefore = -5;
		this.mockMvc.perform(get("/parameters/aggregated/" + timeBefore))
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testEmptyAllHttp() throws Exception {
		when(machineService.findAll()).thenReturn(new ArrayList<Parameter>());
		this.mockMvc.perform(get("/parameters"))
            .andExpect(status().isOk())
		    .andExpect(content().string(equalToIgnoringWhiteSpace("[]")));
	}
}
