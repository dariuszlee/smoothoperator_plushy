package org.plushy.factoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.plushy.factoryapi.models.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

}
