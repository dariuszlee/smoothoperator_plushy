package org.plushy.factoryapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachineEndpoint {

    @GetMapping("/machines")
    public List<String> all() {
        return new ArrayList<String>();
    }
}
