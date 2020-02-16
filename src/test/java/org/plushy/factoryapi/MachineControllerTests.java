package org.plushy.factoryapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.plushy.factoryapi.controller.MachineController;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
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
	private MachineRepository machineRepo;

	@MockBean
	private ParameterRepository parameterRepo;

	@Test
	public void testEmptyAllHttp() throws Exception {
		when(machineRepo.findAll()).thenReturn(new ArrayList<Machine>());
		this.mockMvc.perform(get("/machines"))
            .andDo(print())
            .andExpect(status().isOk())
		    .andExpect(content().string(equalToIgnoringWhiteSpace("[]")));
	}

	@Test
	public void testPostEvent() throws Exception {
		// when(machineRepo.findAll()).thenReturn(new ArrayList<Machine>());
		this.mockMvc.perform(post("/machines")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"machineKey\": \"asdf\", \"parameters\": {\"asdf\": 1}}"))
            .andDo(print());
            // .andExpect(status().isOk())
		    // .andExpect(content().string(equalToIgnoringWhiteSpace("[]")));
	}
}
