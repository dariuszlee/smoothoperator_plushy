package org.plush.factoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.plushy.factoryapi.models.Machine;

interface EmployeeRepository extends JpaRepository<Machine, Long> {

}
