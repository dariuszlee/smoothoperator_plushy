package org.plushy.factoryapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.plushy.factoryapi.exceptions.MachineNotFoundException;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.models.MachineUpdateEvent;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;

@RestController
public class MachineController {
    @Autowired
    private MachineRepository machineRepository;
    private ParameterRepository parameterRepository;

    @Autowired
    MachineController(MachineRepository machineRepository, ParameterRepository parameterRepository) {
        this.machineRepository = machineRepository; 
        this.parameterRepository = parameterRepository;
    } 

    @GetMapping("/machines")
    public List<Machine> all() {
        List<Machine> machines = machineRepository.findAll();
        return machines; 
    }

    @PostMapping("/machines")
    public void add(@RequestBody MachineUpdateEvent e) {
        // List<Machine> machines = machineRepository.findAll();
        System.out.println(e);        
        // return machines; 
    }

    @GetMapping("/machinesAggregated")
    public List<Machine> allAggregated() {
        List<Machine> machines = machineRepository.findAll();
        return machines; 
    }


    @GetMapping("/machines/{machineKey}")
    public Machine getMachine(@PathVariable String machineKey) {
        Machine machine = machineRepository.findById(machineKey).orElseThrow(() -> new MachineNotFoundException(machineKey));

        return machine; 
    }
}
