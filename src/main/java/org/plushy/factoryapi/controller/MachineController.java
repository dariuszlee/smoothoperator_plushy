package org.plushy.factoryapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.plushy.factoryapi.models.Machine;

@RestController
class MachineController {
    MachineController() {
    } 

    @GetMapping("/machines")
    List<Machine> all() {
        List<Machine> machines = new ArrayList<Machine>();
        machines.add(new Machine("Asdf"));
        return machines; 
    }
}
