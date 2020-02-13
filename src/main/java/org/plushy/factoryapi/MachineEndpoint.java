package org.plushy.factoryapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachineEndpoint {

    @GetMapping("/employees")
    public List<String> all() {
        return new ArrayList<String>();
    }
}
