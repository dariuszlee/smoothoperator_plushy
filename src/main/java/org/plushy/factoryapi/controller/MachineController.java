package org.plushy.factoryapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.plushy.factoryapi.exceptions.MachineNotFoundException;
import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;

@RestController
class MachineController {
    MachineRepository machineRepository;
    ParameterRepository parameterRepository;
    MachineController(MachineRepository machineRepository, ParameterRepository parameterRepository) {
        this.machineRepository = machineRepository; 
        this.parameterRepository = parameterRepository;
    } 

    @GetMapping("/machines")
    List<Machine> all() {
        List<Machine> machines = machineRepository.findAll();
        return machines; 
    }

    @GetMapping("/machines/{machineKey}")
    Machine getMachine(@PathVariable String machineKey) {
        Machine machine = machineRepository.findById(machineKey).orElse(new Machine());
        // .orElseThrow(() -> new Exception("Id not found"));
        return machine; 
    }

    @GetMapping("/machines/{machineKey}/parameter")
    Machine getParameter(@PathVariable String machineKey) {
        Machine machine = machineRepository.findById(machineKey).orElseThrow(() -> new MachineNotFoundException(machineKey));

        return machine; 
    }
}
