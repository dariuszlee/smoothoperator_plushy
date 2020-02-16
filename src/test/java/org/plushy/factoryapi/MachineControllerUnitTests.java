package org.plushy.factoryapi;

import static org.mockito.Mockito.when;
import org.plushy.factoryapi.controller.MachineController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.plushy.factoryapi.controller.MachineController;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {MachineController.class, MachineRepository.class})
@SpringBootTest
public class MachineControllerUnitTests {

	@Autowired
	private MachineController machineController;

	@Autowired
	private MachineRepository machineRepo;

	@Autowired
	private ParameterRepository parameterRepo;

	@Test
	public void testEmptyAll() throws Exception {
		// when(machineRepo.findAll()).thenReturn(new ArrayList<Machine>());
		List<Machine> res = this.machineController.all();
        System.out.println(res);
        Assert.isTrue(res.size() == 3);
	}

    @Test void testInvalidInsert() {
        // this.machineController.add();
    }
}
